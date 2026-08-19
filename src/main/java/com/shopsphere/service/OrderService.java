package com.shopsphere.service;

import java.math.BigDecimal;
import java.util.List;

import com.shopsphere.entity.Cart;
import com.shopsphere.entity.CartItem;
import com.shopsphere.entity.Order;
import com.shopsphere.entity.OrderItem;
import com.shopsphere.entity.OrderStatus;
import com.shopsphere.entity.Product;
import com.shopsphere.entity.User;
import com.shopsphere.exception.InsufficientStockException;
import com.shopsphere.exception.UserNotFoundException;

import org.springframework.stereotype.Service;

import com.shopsphere.repository.CartItemRepository;
import com.shopsphere.repository.CartRepository;
import com.shopsphere.repository.OrderItemRepository;
import com.shopsphere.repository.OrderRepository;
import com.shopsphere.repository.ProductRepository;
import com.shopsphere.repository.UserRepository;
import com.shopsphere.dto.OrderResponse;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderResponse placeOrder(Long userId) {

    User user = userRepository.findById(userId)
            .orElseThrow(() ->
                    new UserNotFoundException("User not found"));

    Cart cart = cartRepository.findByUser(user)
            .orElseThrow(() ->
                    new RuntimeException("Cart not found"));

    List<CartItem> cartItems = cartItemRepository.findByCart(cart);

    if (cartItems.isEmpty()) {
        throw new RuntimeException("Cart is empty");
    }

    BigDecimal totalAmount = BigDecimal.ZERO;

    for (CartItem cartItem : cartItems) {

        Product product = cartItem.getProduct();

        BigDecimal itemTotal = product.getPrice()
                .multiply(
                        BigDecimal.valueOf(cartItem.getQuantity())
                );

        totalAmount = totalAmount.add(itemTotal);
    }

    for (CartItem cartItem : cartItems) {

    Product product = cartItem.getProduct();

    if (cartItem.getQuantity() > product.getStock()) {
        throw new InsufficientStockException(
                "Insufficient stock for product: "
                        + product.getName()
                        + ". Available stock: "
                        + product.getStock()
        );
        }
        }

    Order order = new Order(
            user,
            totalAmount,
            OrderStatus.PENDING
    );

    order = orderRepository.save(order);

    for (CartItem cartItem : cartItems) {

        Product product = cartItem.getProduct();

        OrderItem orderItem = new OrderItem(
                order,
                product,
                cartItem.getQuantity(),
                product.getPrice()
        );

        orderItemRepository.save(orderItem);

        product.setStock(
                product.getStock() - cartItem.getQuantity()
        );

        productRepository.save(product);
    }

    cartItemRepository.deleteAll(cartItems);

    return toOrderResponse(order);
    }


    public List<OrderResponse> getOrdersByUser(Long userId) {

    User user = userRepository.findById(userId)
            .orElseThrow(() ->
                    new UserNotFoundException("User not found"));

    return orderRepository.findByUser(user)
        .stream()
        .map(this::toOrderResponse)
        .toList();
    }

    public OrderResponse getOrderById(Long orderId) {

    Order order = orderRepository.findById(orderId)
        .orElseThrow(() ->
                new RuntimeException("Order not found"));

        return toOrderResponse(order);
    }
    
    private OrderResponse toOrderResponse(Order order) {

    return new OrderResponse(
            order.getId(),
            order.getTotalAmount(),
            order.getStatus()
    );
        }

}