package com.shopsphere.service;

import org.springframework.stereotype.Service;

import com.shopsphere.entity.Cart;
import com.shopsphere.entity.User;
import com.shopsphere.exception.UserNotFoundException;
import com.shopsphere.repository.CartRepository;
import com.shopsphere.repository.UserRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartService(
            CartRepository cartRepository,
            UserRepository userRepository) {

        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public Cart createCart(Long userId) {

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

    Cart cart = new Cart(user);

    return cartRepository.save(cart);

    }

    public Cart getCartByUser(Long userId) {

    User user = userRepository.findById(userId)
            .orElseThrow(() ->
                    new UserNotFoundException("User not found"));

    return cartRepository.findByUser(user)
            .orElseThrow(() ->
                    new RuntimeException("Cart not found"));
    }
}