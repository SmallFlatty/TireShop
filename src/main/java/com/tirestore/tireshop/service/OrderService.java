package com.tirestore.tireshop.service;

import com.tirestore.tireshop.repository.OrderRepository;

public class OrderService {

    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
