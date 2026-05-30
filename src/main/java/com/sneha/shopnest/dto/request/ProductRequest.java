package com.sneha.shopnest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class ProductRequest {
    @NotBlank private String name;
    private String description;
    @NotNull @Positive private BigDecimal price;
    @NotNull @PositiveOrZero private Integer stock;
    private String imageUrl;
    @NotNull private Long categoryId;

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
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long v) { this.categoryId = v; }
}