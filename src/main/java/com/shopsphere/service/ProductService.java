package com.shopsphere.service;

import org.springframework.stereotype.Service;

import com.shopsphere.repository.ProductRepository;
import com.shopsphere.entity.Product;
import com.shopsphere.entity.Category;
import com.shopsphere.repository.CategoryRepository;
import com.shopsphere.exception.CategoryNotFoundException;
import com.shopsphere.exception.ProductNotFoundException;
import java.util.List;
import com.shopsphere.dto.ProductRequest;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product createProduct(ProductRequest productRequest) {

    Category category = categoryRepository.findById(
            productRequest.getCategoryId()
    ).orElseThrow(() ->
            new CategoryNotFoundException("Category not found"));

    Product product = new Product();

    product.setName(productRequest.getName());
    product.setDescription(productRequest.getDescription());
    product.setPrice(productRequest.getPrice());
    product.setStock(productRequest.getStock());
    product.setCategory(category);

    return productRepository.save(product);
}

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
    }

    public Product updateProduct(Long id, ProductRequest productRequest) {

    Product existingProduct = productRepository.findById(id)
            .orElseThrow(() ->
                    new ProductNotFoundException("Product not found"));

    Category existingCategory = categoryRepository.findById(
            productRequest.getCategoryId()
    ).orElseThrow(() ->
            new CategoryNotFoundException("Category not found"));

    existingProduct.setName(productRequest.getName());
    existingProduct.setDescription(productRequest.getDescription());
    existingProduct.setPrice(productRequest.getPrice());
    existingProduct.setStock(productRequest.getStock());
    existingProduct.setCategory(existingCategory);

    return productRepository.save(existingProduct);
    }
    
    public void deleteProduct(Long id){

        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));

        productRepository.delete(product);
    }
}
