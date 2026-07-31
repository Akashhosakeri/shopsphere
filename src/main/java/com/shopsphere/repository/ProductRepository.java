package com.shopsphere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopsphere.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{
    
}
