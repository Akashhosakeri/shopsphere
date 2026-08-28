package com.shopsphere.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.shopsphere.dto.AddressResponse;
import com.shopsphere.entity.Address;
import com.shopsphere.service.AddressService;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public AddressResponse createAddress(
            Authentication authentication,
            @RequestBody Address address) {

        return addressService.createAddress(
                authentication.getName(),
                address
        );
    }

    @GetMapping
    public List<AddressResponse> getUserAddresses(
            Authentication authentication) {

        return addressService.getUserAddresses(
                authentication.getName()
        );
    }
}