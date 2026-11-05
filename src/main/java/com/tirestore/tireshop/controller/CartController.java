package com.tirestore.tireshop.controller;


import com.tirestore.tireshop.dto.request.AddToCartRequest;
import com.tirestore.tireshop.service.CartService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public void addToCart(@Valid @RequestBody AddToCartRequest addToCartRequest) {
        cartService.addToCart(addToCartRequest);
    }

    @DeleteMapping("/remove")
    public void removeFromCart(@RequestParam Integer itemId) {
        cartService.removeFromCart(itemId);
    }

    @PutMapping("/update-quantity")
    public void changeQuantity(@RequestParam Integer itemId, @RequestParam Integer quantity) {
        cartService.changeQuantity(itemId, quantity);
    }
}
