package com.sneha.shopnest.service;

import com.sneha.shopnest.dto.request.CartItemRequest;
import com.sneha.shopnest.dto.response.CartResponse;
import com.sneha.shopnest.entity.*;
import com.sneha.shopnest.exception.ResourceNotFoundException;
import com.sneha.shopnest.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
                       ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private CartResponse toResponse(Cart cart) {
        List<CartResponse.CartItemResponse> items = cart.getItems().stream().map(item -> {
            CartResponse.CartItemResponse r = new CartResponse.CartItemResponse();
            r.setId(item.getId());
            r.setProductId(item.getProduct().getId());
            r.setProductName(item.getProduct().getName());
            r.setPrice(item.getProduct().getPrice());
            r.setQuantity(item.getQuantity());
            r.setSubtotal(item.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())));
            r.setImageUrl(item.getProduct().getImageUrl());
            return r;
        }).toList();

        BigDecimal total = items.stream()
                .map(CartResponse.CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CartResponse response = new CartResponse();
        response.setId(cart.getId());
        response.setItems(items);
        response.setTotalPrice(total);
        return response;
    }

    public CartResponse getCart() {
        User user = getCurrentUser();
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return toResponse(cart);
    }

    public CartResponse addItem(CartItemRequest request) {
        User user = getCurrentUser();
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        // If item already in cart, update quantity
        cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .ifPresentOrElse(
                        existing -> {
                            existing.setQuantity(existing.getQuantity() + request.getQuantity());
                            cartItemRepository.save(existing);
                        },
                        () -> {
                            CartItem item = new CartItem();
                            item.setCart(cart);
                            item.setProduct(product);
                            item.setQuantity(request.getQuantity());
                            cartItemRepository.save(item);
                        }
                );

        Cart updatedCart = cartRepository.findByUserId(user.getId()).get();
        return toResponse(updatedCart);
    }

    public CartResponse updateItem(Long itemId, CartItemRequest request) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        item.setQuantity(request.getQuantity());
        cartItemRepository.save(item);
        User user = getCurrentUser();
        Cart cart = cartRepository.findByUserId(user.getId()).get();
        return toResponse(cart);
    }

    public CartResponse removeItem(Long itemId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        cartItemRepository.delete(item);
        User user = getCurrentUser();
        Cart cart = cartRepository.findByUserId(user.getId()).get();
        return toResponse(cart);
    }

    public void clearCart(Cart cart) {
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}