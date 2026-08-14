package com.shopsphere.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.shopsphere.entity.Address;
import com.shopsphere.service.AddressService;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/{userId}")
    public Address createAddress(
        @PathVariable Long userId,
        @RequestBody Address address) {

    return addressService.createAddress(userId, address);
    }

    @GetMapping("/{userId}")
    public List<Address> getUserAddresses(@PathVariable Long userId) {

    return addressService.getUserAddresses(userId);
    }
}