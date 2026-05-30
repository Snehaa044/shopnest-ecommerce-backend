package com.sneha.shopnest.service;

import com.sneha.shopnest.dto.request.ProductRequest;
import com.sneha.shopnest.dto.response.ProductResponse;
import com.sneha.shopnest.entity.Category;
import com.sneha.shopnest.entity.Product;
import com.sneha.shopnest.exception.ResourceNotFoundException;
import com.sneha.shopnest.repository.CategoryRepository;
import com.sneha.shopnest.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    private ProductResponse toResponse(Product p) {
        ProductResponse r = new ProductResponse();
        r.setId(p.getId());
        r.setName(p.getName());
        r.setDescription(p.getDescription());
        r.setPrice(p.getPrice());
        r.setStock(p.getStock());
        r.setImageUrl(p.getImageUrl());
        r.setActive(p.isActive());
        if (p.getCategory() != null) {
            r.setCategoryId(p.getCategory().getId());
            r.setCategoryName(p.getCategory().getName());
        }
        return r;
    }

    public ProductResponse create(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Product p = new Product();
        p.setName(request.getName());
        p.setDescription(request.getDescription());
        p.setPrice(request.getPrice());
        p.setStock(request.getStock());
        p.setImageUrl(request.getImageUrl());
        p.setCategory(category);
        return toResponse(productRepository.save(p));
    }

    public List<ProductResponse> getAll() {
        return productRepository.findByActiveTrue().stream().map(this::toResponse).toList();
    }

    public List<ProductResponse> getByCategory(Long categoryId) {
        return productRepository.findByCategoryIdAndActiveTrue(categoryId)
                .stream().map(this::toResponse).toList();
    }

    public List<ProductResponse> search(String keyword) {
        return productRepository.searchProducts(keyword)
                .stream().map(this::toResponse).toList();
    }

    public ProductResponse getById(Long id) {
        return toResponse(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found")));
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        p.setName(request.getName());
        p.setDescription(request.getDescription());
        p.setPrice(request.getPrice());
        p.setStock(request.getStock());
        p.setImageUrl(request.getImageUrl());
        p.setCategory(category);
        return toResponse(productRepository.save(p));
    }

    public void delete(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        p.setActive(false);
        productRepository.save(p);
    }
}