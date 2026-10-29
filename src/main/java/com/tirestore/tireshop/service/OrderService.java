package com.tirestore.tireshop.service;

import com.tirestore.tireshop.dto.request.OrderItemRequest;
import com.tirestore.tireshop.dto.response.OrderResponse;
import com.tirestore.tireshop.entity.Account;
import com.tirestore.tireshop.entity.Item;
import com.tirestore.tireshop.entity.Order;
import com.tirestore.tireshop.entity.OrderItem;
import com.tirestore.tireshop.enums.OrderStatus;
import com.tirestore.tireshop.exception.ItemOutOfStockException;
import com.tirestore.tireshop.repository.AccountRepository;
import com.tirestore.tireshop.repository.ItemRepository;
import com.tirestore.tireshop.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;
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
    //TODO Stock in OrderResponse, - items bought. Remove duplicates, create method Map To OrderResponse. Check all methods logic

    @Transactional
    public OrderResponse makeOrder(List<OrderItemRequest> request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Authentication required");
        }

        String userEmail = authentication.getName();

        Account userAccount = accountRepository.findByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("Account not found"));

        Order order = new Order();

        // Get References for orderItem, order objects
        Order orderRef = orderRepository.getReferenceById(order.getOrderId());
        Account accountRef = accountRepository.getReferenceById(userAccount.getUserId());

        List<OrderItem> orderItems = new ArrayList<>();

        //List of orderItems objects
        for(OrderItemRequest orderItemRequest : request) {
            orderItems.add(new OrderItem(
                    orderRef,
                    itemRepository.getReferenceById(orderItemRequest.itemId()),
                    orderItemRequest.price(),
                    orderItemRequest.quantity()));
        }

        for(OrderItem orderItem : orderItems) {
            Item item = itemRepository.findById(orderItem.getItem().getItemId()).orElseThrow(() -> new EntityNotFoundException("Item with id: " + orderItem.getItem().getItemId() + " not found"));
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
            totalPrice = totalPrice.add(orderItem.getPricePurchase());
        }

        order.setAccount(accountRef);
        order.setStatus(OrderStatus.NEW);
        order.setTotal(totalPrice);
        order.setOrderItems(orderItems);

        orderRepository.save(order);

        return new OrderResponse(
                order.getOrderId(),
                OrderStatus.NEW,
                order.getCreatedAt(),
                totalPrice,
                orderItems

        );
    }


    @Transactional
    public OrderResponse changeOrderStatus(Integer orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " not found"));

        if(orderStatus.equals(OrderStatus.CANCELLED)) {
            throw new IllegalArgumentException("Order status cannot be CANCELLED here");
        }

        order.setStatus(orderStatus);
        orderRepository.save(order);

        return new OrderResponse(
                order.getOrderId(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getTotal(),
                order.getOrderItems()
        );
    }

    @Transactional
    public OrderResponse cancelOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " not found"));
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
                order.getOrderItems()
        );
    }

    public OrderResponse getOrder(Integer orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Authentication required");
        }

        String userEmail = authentication.getName();

        Account userAccount = accountRepository.findByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("Account not found"));

        Order order = orderRepository.findByUserId(userAccount.getUserId());

        if(order == null) {
            throw new EntityNotFoundException("Order with id: " + orderId + " not found");
        }

        return new OrderResponse(
                order.getOrderId(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getTotal(),
                order.getOrderItems()
        );
    }

    public List<OrderResponse> getOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Authentication required");
        }

        String userEmail = authentication.getName();

        Account userAccount = accountRepository.findByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("Account not found"));

        List<Order> orders = orderRepository.findAllByUserId(userAccount.getUserId());

        List<OrderResponse> orderResponseList = new ArrayList<>();
        for(Order order : orders) {
            OrderResponse orderResponse = new OrderResponse(
                    order.getOrderId(),
                    order.getStatus(),
                    order.getCreatedAt(),
                    order.getTotal(),
                    order.getOrderItems()
            );
            orderResponseList.add(orderResponse);
        }
        return orderResponseList;
    }


}
