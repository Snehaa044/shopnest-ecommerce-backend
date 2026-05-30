package com.sneha.shopnest.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CartItemRequest {
    @NotNull private Long productId;
    @NotNull @Positive private Integer quantity;

    public Long getProductId() { return productId; }
    public void setProductId(Long v) { this.productId = v; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer v) { this.quantity = v; }
}