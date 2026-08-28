package com.shopsphere.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.shopsphere.dto.AddressResponse;

import com.shopsphere.entity.Address;
import com.shopsphere.entity.User;
import com.shopsphere.exception.UserNotFoundException;
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

    public AddressResponse createAddress(
        String email,
        Address address) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new UserNotFoundException("User not found"));

    address.setUser(user);

    Address savedAddress = addressRepository.save(address);

    return toAddressResponse(savedAddress);
}

    public List<AddressResponse> getUserAddresses(String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new UserNotFoundException("User not found"));

    return addressRepository.findByUser(user)
            .stream()
            .map(this::toAddressResponse)
            .toList();
    }

    private AddressResponse toAddressResponse(Address address) {

    return new AddressResponse(
            address.getId(),
            address.getStreet(),
            address.getCity(),
            address.getState(),
            address.getPincode(),
            address.getCountry()
    );
    }
}