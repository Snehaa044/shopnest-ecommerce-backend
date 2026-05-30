package com.sneha.shopnest.service;

import com.sneha.shopnest.dto.request.CategoryRequest;
import com.sneha.shopnest.dto.response.CategoryResponse;
import com.sneha.shopnest.entity.Category;
import com.sneha.shopnest.exception.ResourceNotFoundException;
import com.sneha.shopnest.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private CategoryResponse toResponse(Category c) {
        CategoryResponse r = new CategoryResponse();
        r.setId(c.getId());
        r.setName(c.getName());
        r.setDescription(c.getDescription());
        r.setProductCount(c.getProducts().size());
        return r;
    }

    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category already exists");
        }
        Category c = new Category();
        c.setName(request.getName());
        c.setDescription(request.getDescription());
        return toResponse(categoryRepository.save(c));
    }

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream().map(this::toResponse).toList();
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        c.setName(request.getName());
        c.setDescription(request.getDescription());
        return toResponse(categoryRepository.save(c));
    }

    public void delete(Long id) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(c);
    }
}