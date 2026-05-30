package com.sneha.shopnest.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class DashboardResponse {
    private long totalOrders;
    private long pendingOrders;
    private long totalProducts;
    private long totalCustomers;
    private BigDecimal totalRevenue;
    private List<TopProduct> topProducts;

    public long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(long v) { this.totalOrders = v; }
    public long getPendingOrders() { return pendingOrders; }
    public void setPendingOrders(long v) { this.pendingOrders = v; }
    public long getTotalProducts() { return totalProducts; }
    public void setTotalProducts(long v) { this.totalProducts = v; }
    public long getTotalCustomers() { return totalCustomers; }
    public void setTotalCustomers(long v) { this.totalCustomers = v; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal v) { this.totalRevenue = v; }
    public List<TopProduct> getTopProducts() { return topProducts; }
    public void setTopProducts(List<TopProduct> v) { this.topProducts = v; }

    public static class TopProduct {
        private String productName;
        private Long totalSold;

        public String getProductName() { return productName; }
        public void setProductName(String v) { this.productName = v; }
        public Long getTotalSold() { return totalSold; }
        public void setTotalSold(Long v) { this.totalSold = v; }
    }
}