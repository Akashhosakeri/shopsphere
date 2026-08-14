package com.shopsphere.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopsphere.entity.Address;
import com.shopsphere.entity.User;
import com.shopsphere.repository.AddressRepository;
import com.shopsphere.repository.UserRepository;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(
            AddressRepository addressRepository,
            UserRepository userRepository) {

        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public Address createAddress(
        Long userId,
        Address address) {

    User user = userRepository.findById(userId)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    address.setUser(user);

    return addressRepository.save(address);
    }

    public List<Address> getUserAddresses(Long userId) {

    User user = userRepository.findById(userId)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    return addressRepository.findByUser(user);
    }
}