package com.tirestore.tireshop.service;

import com.tirestore.tireshop.repository.CartItemRepository;

public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }
}
