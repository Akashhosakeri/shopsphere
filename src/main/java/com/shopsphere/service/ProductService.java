package com.shopsphere.service;

import org.springframework.stereotype.Service;

import com.shopsphere.repository.ProductRepository;
import com.shopsphere.entity.Product;
import com.shopsphere.entity.Category;
import com.shopsphere.repository.CategoryRepository;
import com.shopsphere.exception.CategoryNotFoundException;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product createProduct(Product product){
        Category category = product.getCategory();

        Category existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(()-> new CategoryNotFoundException("Cetgory not found"));

        product.setCategory(existingCategory);
        return productRepository.save(product);
    }
}
