package com.sneha.shopnest.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {
    @NotBlank private String name;
    private String description;

    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
}