package com.sneha.shopnest.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {
    private Long id;
    private List<CartItemResponse> items;
    private BigDecimal totalPrice;

    public Long getId() { return id; }
    public void setId(Long v) { this.id = v; }
    public List<CartItemResponse> getItems() { return items; }
    public void setItems(List<CartItemResponse> v) { this.items = v; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal v) { this.totalPrice = v; }

    public static class CartItemResponse {
        private Long id;
        private Long productId;
        private String productName;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subtotal;
        private String imageUrl;

        public Long getId() { return id; }
        public void setId(Long v) { this.id = v; }
        public Long getProductId() { return productId; }
        public void setProductId(Long v) { this.productId = v; }
        public String getProductName() { return productName; }
        public void setProductName(String v) { this.productName = v; }
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal v) { this.price = v; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer v) { this.quantity = v; }
        public BigDecimal getSubtotal() { return subtotal; }
        public void setSubtotal(BigDecimal v) { this.subtotal = v; }
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String v) { this.imageUrl = v; }
    }
}