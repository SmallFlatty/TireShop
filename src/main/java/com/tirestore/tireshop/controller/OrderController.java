package com.tirestore.tireshop.controller;

import com.tirestore.tireshop.dto.request.OrderItemRequest;
import com.tirestore.tireshop.dto.response.OrderResponse;
import com.tirestore.tireshop.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/new")
    public OrderResponse makeOrder(@RequestBody List<OrderItemRequest> request) {
        return orderService.makeOrder(request);
    }

    @PutMapping("/cancel/{id}")
    public OrderResponse cancelOrder(@PathVariable Integer id) {
        return orderService.cancelOrder(id);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Integer id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/all")
    public List<OrderResponse> getAllOrders() {
        return orderService.getOrders();
    }
}
