package com.sneha.shopnest.dto.request;

import jakarta.validation.constraints.NotBlank;

public class OrderRequest {
    @NotBlank private String shippingAddress;

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String v) { this.shippingAddress = v; }
}