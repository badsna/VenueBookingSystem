package com.example.venueservice.dto;

import com.example.venueservice.enums.VenueStatus;
import com.example.venueservice.model.Address;
import com.example.venueservice.model.Contact;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class VenueRequestDto {
    private String venueName;
    private int capacity;

    private double price;
    private Address address;
    private List<String> amenities;

    private List<Contact> contacts;
    private String createdBy;
    private List<String> events;
    private String venueOwnerEmail;



}
