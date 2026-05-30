package com.sneha.shopnest.service;

import com.sneha.shopnest.dto.request.OrderRequest;
import com.sneha.shopnest.dto.response.OrderResponse;
import com.sneha.shopnest.entity.*;
import com.sneha.shopnest.enums.OrderStatus;
import com.sneha.shopnest.exception.ResourceNotFoundException;
import com.sneha.shopnest.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository,
                        CartService cartService, UserRepository userRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private OrderResponse toResponse(Order order) {
        List<OrderResponse.OrderItemResponse> items = order.getItems().stream().map(item -> {
            OrderResponse.OrderItemResponse r = new OrderResponse.OrderItemResponse();
            r.setId(item.getId());
            r.setProductName(item.getProduct().getName());
            r.setQuantity(item.getQuantity());
            r.setPriceAtPurchase(item.getPriceAtPurchase());
            r.setSubtotal(item.getPriceAtPurchase()
                    .multiply(BigDecimal.valueOf(item.getQuantity())));
            return r;
        }).toList();

        OrderResponse r = new OrderResponse();
        r.setId(order.getId());
        r.setCustomerName(order.getUser().getFullName());
        r.setCustomerEmail(order.getUser().getEmail());
        r.setItems(items);
        r.setTotalAmount(order.getTotalAmount());
        r.setStatus(order.getStatus());
        r.setShippingAddress(order.getShippingAddress());
        r.setCreatedAt(order.getCreatedAt());
        return r;
    }

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        User user = getCurrentUser();
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(request.getShippingAddress());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for: " + product.getName());
            }
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice());
            orderItems.add(orderItem);

            total = total.add(product.getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);
        Order saved = orderRepository.save(order);

        cartService.clearCart(cart);

        return toResponse(saved);
    }

    public List<OrderResponse> getMyOrders() {
        User user = getCurrentUser();
        return orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream().map(this::toResponse).toList();
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toResponse).toList();
    }

    public OrderResponse updateStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(OrderStatus.valueOf(status));
        return toResponse(orderRepository.save(order));
    }
}