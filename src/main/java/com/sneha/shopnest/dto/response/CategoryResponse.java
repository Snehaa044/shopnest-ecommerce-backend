package com.sneha.shopnest.dto.response;

public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private int productCount;

    public Long getId() { return id; }
    public void setId(Long v) { this.id = v; }
    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
    public int getProductCount() { return productCount; }
    public void setProductCount(int v) { this.productCount = v; }
}