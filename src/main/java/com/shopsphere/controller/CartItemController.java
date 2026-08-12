package com.shopsphere.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.shopsphere.entity.CartItem;
import com.shopsphere.service.CartItemService;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/{cartId}/{productId}")
    public CartItem addItemToCart(
        @PathVariable Long cartId,
        @PathVariable Long productId,
        @RequestParam Integer quantity) {

    return cartItemService.addItemToCart(
            cartId,
            productId,
            quantity
    );
    }

    @GetMapping("/{cartId}")
    public List<CartItem> getCartItems(@PathVariable Long cartId) {

    return cartItemService.getCartItems(cartId);
    }

    @PutMapping("/{cartItemId}")
    public CartItem updateQuantity(
        @PathVariable Long cartItemId,
        @RequestParam Integer quantity) {

    return cartItemService.updateQuantity(
            cartItemId,
            quantity
    );
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeItem(
        @PathVariable Long cartItemId) {

    cartItemService.removeItem(cartItemId);

    return ResponseEntity.noContent().build();
    }
}