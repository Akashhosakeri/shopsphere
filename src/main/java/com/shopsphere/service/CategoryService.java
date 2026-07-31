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

    public Category updateCategory(Long id,Category updatedCategory){

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("Category not found"));
        existingCategory.setName(updatedCategory.getName());
        existingCategory.setDescription(updatedCategory.getDescription());

        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id){

        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("Category not found"));

        categoryRepository.delete(category);
    }
}
