package com.shopsphere.service;

import org.springframework.stereotype.Service;

import com.shopsphere.entity.User;
import com.shopsphere.exception.UserNotFoundException;
import com.shopsphere.repository.UserRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.shopsphere.dto.UserResponse;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UserResponse toUserResponse(User user) {

    return new UserResponse(
            user.getId(),
            user.getName(),
            user.getPhoneNumber(),
            user.getEmail(),
            user.getRole().name(),
            user.getEnabled()
            );
    }

    public UserResponse createUser(User user) {

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User savedUser = userRepository.save(user);

    return toUserResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {

    return userRepository.findAll()
            .stream()
            .map(this::toUserResponse)
            .toList();
    }

    public UserResponse getUserById(Long id) {

    User user = userRepository.findById(id)
            .orElseThrow(() ->
                    new UserNotFoundException("User not found"));

    return toUserResponse(user);
    }

    public UserResponse updateUser(Long id, User updatedUser){

        User existingUser = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found"));
        
        existingUser.setName(updatedUser.getName());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        existingUser.setRole(updatedUser.getRole());
        existingUser.setEnabled(updatedUser.getEnabled());

        User savedUser = userRepository.save(existingUser);

        return toUserResponse(savedUser);
    }

    public void deleteUser(Long id){
         User user = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }
}