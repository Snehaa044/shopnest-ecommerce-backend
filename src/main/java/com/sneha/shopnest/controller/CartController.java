package com.sneha.shopnest.controller;

import com.sneha.shopnest.dto.request.CartItemRequest;
import com.sneha.shopnest.dto.response.CartResponse;
import com.sneha.shopnest.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addItem(@Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addItem(request));
    }

    @PutMapping("/update/{itemId}")
    public ResponseEntity<CartResponse> updateItem(@PathVariable Long itemId,
                                                   @Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.updateItem(itemId, request));
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<CartResponse> removeItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeItem(itemId));
    }
}