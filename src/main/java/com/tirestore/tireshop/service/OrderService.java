package com.tirestore.tireshop.service;

import com.tirestore.tireshop.repository.OrderRepository;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
