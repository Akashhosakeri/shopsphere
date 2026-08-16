package com.shopsphere.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import jakarta.validation.Valid;
import com.shopsphere.dto.CartItemRequest;

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
        @Valid @RequestBody CartItemRequest request) {

    return cartItemService.addItemToCart(
            cartId,
            productId,
            request.getQuantity()
    );
    }

    @GetMapping("/{cartId}")
    public List<CartItem> getCartItems(@PathVariable Long cartId) {

    return cartItemService.getCartItems(cartId);
    }

    @PutMapping("/{cartItemId}")
    public CartItem updateQuantity(
        @PathVariable Long cartItemId,
        @Valid @RequestBody CartItemRequest request) {

    return cartItemService.updateQuantity(
            cartItemId,
            request.getQuantity()
    );
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeItem(
        @PathVariable Long cartItemId) {

    cartItemService.removeItem(cartItemId);

    return ResponseEntity.noContent().build();
    }
}