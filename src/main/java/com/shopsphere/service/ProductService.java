package com.shopsphere.service;

import org.springframework.stereotype.Service;

import com.shopsphere.repository.ProductRepository;
import com.shopsphere.entity.Product;
import com.shopsphere.entity.Category;
import com.shopsphere.repository.CategoryRepository;
import com.shopsphere.exception.CategoryNotFoundException;
import com.shopsphere.exception.ProductNotFoundException;
import java.util.List;

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
                .orElseThrow(()-> new CategoryNotFoundException("Category not found"));

        product.setCategory(existingCategory);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
    }

    public Product updateProduct(Long id, Product updatedProduct) {

    Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));

    Category category = updatedProduct.getCategory();

    Category existingCategory = categoryRepository.findById(category.getId())
            .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

    existingProduct.setName(updatedProduct.getName());
    existingProduct.setDescription(updatedProduct.getDescription());
    existingProduct.setPrice(updatedProduct.getPrice());
    existingProduct.setStock(updatedProduct.getStock());
    existingProduct.setCategory(existingCategory);

    return productRepository.save(existingProduct);
    }  
    
    public void deleteProduct(Long id){

        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));

        productRepository.delete(product);
    }
}
