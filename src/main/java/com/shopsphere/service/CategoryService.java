package com.shopsphere.service;

import com.shopsphere.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import com.shopsphere.entity.Category;
import java.util.List;
import com.shopsphere.exception.CategoryNotFoundException;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
    return categoryRepository.save(category);
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("Category not found"));
    }
}
