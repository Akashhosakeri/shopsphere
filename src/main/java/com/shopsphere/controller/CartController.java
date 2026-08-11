package com.shopsphere.controller;

import org.springframework.web.bind.annotation.*;
import com.shopsphere.entity.Cart;
import com.shopsphere.service.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{userId}")
    public Cart createCart(@PathVariable Long userId) {

        return cartService.createCart(userId);
    }

    @GetMapping("/{userId}")
    public Cart getCartByUser(@PathVariable Long userId) {

        return cartService.getCartByUser(userId);
}
}