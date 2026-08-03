package com.shopsphere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopsphere.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
    
}
