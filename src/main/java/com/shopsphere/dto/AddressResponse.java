package com.shopsphere.dto;

public class AddressResponse {

    private Long id;
    private String street;
    private String city;
    private String state;
    private String pincode;
    private String country;

    public AddressResponse(
            Long id,
            String street,
            String city,
            String state,
            String pincode,
            String country) {

        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPincode() {
        return pincode;
    }

    public String getCountry() {
        return country;
    }
}