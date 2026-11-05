package com.tirestore.tireshop.service;

import com.tirestore.tireshop.dto.request.OrderItemRequest;
import com.tirestore.tireshop.dto.response.OrderItemResponse;
import com.tirestore.tireshop.dto.response.OrderResponse;
import com.tirestore.tireshop.entity.Account;
import com.tirestore.tireshop.entity.Item;
import com.tirestore.tireshop.entity.Order;
import com.tirestore.tireshop.entity.OrderItem;
import com.tirestore.tireshop.enums.OrderStatus;
import com.tirestore.tireshop.exception.ItemOutOfStockException;
import com.tirestore.tireshop.exception.ResourceNotFoundException;
import com.tirestore.tireshop.repository.AccountRepository;
import com.tirestore.tireshop.repository.ItemRepository;
import com.tirestore.tireshop.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final ItemRepository itemRepository;

    public OrderService(OrderRepository orderRepository, AccountRepository accountRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public OrderResponse makeOrder(List<OrderItemRequest> request) {
        if(request == null || request.isEmpty()) {
            throw new IllegalArgumentException("Order cannot be null or empty");
        }

        String userEmail = authUserEmail();

        Account userAccount = accountRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        Order order = new Order();

        List<OrderItem> orderItems = new ArrayList<>();

        //List of orderItems objects
        for(OrderItemRequest orderItemRequest : request) {
            orderItems.add(new OrderItem(
                    order,
                    itemRepository.getReferenceById(orderItemRequest.itemId()),
                    itemRepository.findById(orderItemRequest.itemId()).orElseThrow(() -> new ResourceNotFoundException("item with id: "+ orderItemRequest.itemId() + " doest exist")).getPrice(),
                    orderItemRequest.quantity()));
        }

        for(OrderItem orderItem : orderItems) {
            Item item = itemRepository.findById(orderItem.getItem().getItemId()).orElseThrow(() -> new ResourceNotFoundException("Item with id: " + orderItem.getItem().getItemId() + " not found"));
            if(item.getStock() < orderItem.getQuantity()) {
                throw new ItemOutOfStockException("Item:" + item.getName() + " is out of stock");
            }else{
                // -! check logic
                item.setStock(item.getStock() - orderItem.getQuantity());
                itemRepository.save(item);
            }
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        // get total price for order
        for(OrderItem orderItem : orderItems) {
            for(int i = 0; i < orderItem.getQuantity(); i++) {
                totalPrice = totalPrice.add(orderItem.getPricePurchase());
            }
        }

        order.setAccount(userAccount);
        order.setStatus(OrderStatus.NEW);
        order.setTotal(totalPrice);
        order.setOrderItems(orderItems);

        orderRepository.save(order);

        return new OrderResponse(
                order.getOrderId(),
                OrderStatus.NEW,
                order.getCreatedAt(),
                totalPrice,
                mapToOrderItemResponse(orderItems)

        );
    }


    @Transactional
    public OrderResponse changeOrderStatus(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order did not exist"));

        if(order.getStatus().equals(OrderStatus.CANCELLED)) {
            throw new IllegalStateException("Order status cannot be updated");
        }

        order.setStatus(order.getStatus().nextStatus());

        orderRepository.save(order);

        return new OrderResponse(
                order.getOrderId(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getTotal(),
                mapToOrderItemResponse(order.getOrderItems())
        );
    }

    @Transactional
    public OrderResponse cancelOrder(Integer orderId) {

        String userEmail = authUserEmail();

        Account account = accountRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order did not exist"));

        if(account.getUserId() != order.getAccount().getUserId()) {
            throw new ResourceNotFoundException("Order did not exist");
        }

        if(order.getStatus().equals(OrderStatus.CANCELLED)) {
            throw new IllegalArgumentException("Order status cannot be CANCELLED here");
        }

        if(order.getStatus().equals(OrderStatus.COMPLETED)) {
            throw new IllegalStateException("Completed order cannot be CANCELLED");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        List<OrderItem> orderItems = order.getOrderItems();

        for(OrderItem orderItem : orderItems) {
            Item item = orderItem.getItem();

            item.setStock(item.getStock() + orderItem.getQuantity());
        }

        return new OrderResponse(
                orderId,
                OrderStatus.CANCELLED,
                order.getCreatedAt(),
                order.getTotal(),
                mapToOrderItemResponse(order.getOrderItems())
        );
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(Integer orderId) {

        String userEmail = authUserEmail();

        Account userAccount = accountRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order did not exist"));


        if(order.getAccount().getUserId() != userAccount.getUserId()) {
            throw new ResourceNotFoundException("Order did not exist");
        }

        return new OrderResponse(
                order.getOrderId(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getTotal(),
                mapToOrderItemResponse(order.getOrderItems())
        );
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrders() {

        String userEmail = authUserEmail();

        Account userAccount = accountRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        List<Order> orders = orderRepository.findAllByUserId(userAccount.getUserId());

        List<OrderResponse> orderResponseList = new ArrayList<>();
        for(Order order : orders) {
            OrderResponse orderResponse = new OrderResponse(
                    order.getOrderId(),
                    order.getStatus(),
                    order.getCreatedAt(),
                    order.getTotal(),
                    mapToOrderItemResponse(order.getOrderItems())
            );
            orderResponseList.add(orderResponse);
        }
        return orderResponseList;
    }

    private List<OrderItemResponse> mapToOrderItemResponse(List<OrderItem> orderItem) {
        List<OrderItemResponse> orderItemResponseList = new ArrayList<>();
        for(OrderItem itemObj : orderItem) {
                Item item = itemRepository.findById(itemObj.getItem().getItemId()).orElseThrow(() -> new ResourceNotFoundException("Item: " + itemObj.getItem().getItemId() + " not found"));
                OrderItemResponse orderItemResponse = new OrderItemResponse(
                        item.getItemId(),
                        itemObj.getPricePurchase(),
                        itemObj.getQuantity(),
                        item.getName(),
                        item.getProductType(),
                        item.getPublicId()
                );
                orderItemResponseList.add(orderItemResponse);
        }
        return orderItemResponseList;
    }

    private String authUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if(authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("Authentication required");
        }

        return authentication.getName();
    }


}
