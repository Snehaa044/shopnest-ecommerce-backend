package com.sneha.shopnest.dto.response;

import com.sneha.shopnest.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long id;
    private String customerName;
    private String customerEmail;
    private List<OrderItemResponse> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private String shippingAddress;
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long v) { this.id = v; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String v) { this.customerName = v; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String v) { this.customerEmail = v; }
    public List<OrderItemResponse> getItems() { return items; }
    public void setItems(List<OrderItemResponse> v) { this.items = v; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal v) { this.totalAmount = v; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus v) { this.status = v; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String v) { this.shippingAddress = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }

    public static class OrderItemResponse {
        private Long id;
        private String productName;
        private Integer quantity;
        private BigDecimal priceAtPurchase;
        private BigDecimal subtotal;

        public Long getId() { return id; }
        public void setId(Long v) { this.id = v; }
        public String getProductName() { return productName; }
        public void setProductName(String v) { this.productName = v; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer v) { this.quantity = v; }
        public BigDecimal getPriceAtPurchase() { return priceAtPurchase; }
        public void setPriceAtPurchase(BigDecimal v) { this.priceAtPurchase = v; }
        public BigDecimal getSubtotal() { return subtotal; }
        public void setSubtotal(BigDecimal v) { this.subtotal = v; }
    }
}