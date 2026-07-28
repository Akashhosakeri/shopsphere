package com.shopsphere.service;

import com.shopsphere.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import com.shopsphere.entity.Category;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
    return categoryRepository.save(category);
}
}
