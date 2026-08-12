package com.shopsphere.service;

import org.springframework.stereotype.Service;

import java.util.List;
import com.shopsphere.entity.Cart;
import com.shopsphere.entity.CartItem;
import com.shopsphere.entity.Product;
import com.shopsphere.exception.ProductNotFoundException;
import com.shopsphere.repository.CartItemRepository;
import com.shopsphere.repository.CartRepository;
import com.shopsphere.repository.ProductRepository;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartItemService(
            CartItemRepository cartItemRepository,
            CartRepository cartRepository,
            ProductRepository productRepository) {

        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public CartItem addItemToCart(Long cartId, Long productId, Integer quantity) {

    Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));

    CartItem existingItem = cartItemRepository
            .findByCartAndProduct(cart, product)
            .orElse(null);

    if (existingItem != null) {
        existingItem.setQuantity(
                existingItem.getQuantity() + quantity
        );

        return cartItemRepository.save(existingItem);
    }

    CartItem cartItem = new CartItem(
            cart,
            product,
            quantity
    );

    return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItems(Long cartId) {

    Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

    return cartItemRepository.findByCart(cart);
    }

    public CartItem updateQuantity(Long cartItemId, Integer quantity) {

    CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("Cart item not found"));

    cartItem.setQuantity(quantity);

    return cartItemRepository.save(cartItem);
    }

    public void removeItem(Long cartItemId) {

    CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("Cart item not found"));

    cartItemRepository.delete(cartItem);
    }
}