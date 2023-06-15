package com.example.venueservice.dto;

import com.example.venueservice.enums.VenueStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VenueResponseDto {
    private String venueName;
    private int capacity;
    private double price;
    private VenueStatus availability;
    private List<String> amenities;
    private List<String> events;

    private List<ImageDto> venueImages;

    private AddressDto address;
    private String venueOwnerEmail;

    private List<ContactDto> contacts;
}
