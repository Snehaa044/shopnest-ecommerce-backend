package com.sneha.shopnest.dto.response;

import java.math.BigDecimal;

public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
    private boolean active;
    private Long categoryId;
    private String categoryName;

    public Long getId() { return id; }
    public void setId(Long v) { this.id = v; }
    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal v) { this.price = v; }
    public Integer getStock() { return stock; }
    public void setStock(Integer v) { this.stock = v; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String v) { this.imageUrl = v; }
    public boolean isActive() { return active; }
    public void setActive(boolean v) { this.active = v; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long v) { this.categoryId = v; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String v) { this.categoryName = v; }
}