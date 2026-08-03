package com.shopsphere.service;

import org.springframework.stereotype.Service;

import com.shopsphere.entity.User;
import com.shopsphere.exception.UserNotFoundException;
import com.shopsphere.repository.UserRepository;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found"));
    }

    public User updateUser(Long id,User updatedUser){

        User existingUser = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found"));
        
        existingUser.setName(updatedUser.getName());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setEnabled(updatedUser.getEnabled());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id){
         User user = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }
}