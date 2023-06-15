package com.example.bookingservice.model;

import com.example.bookingservice.enums.VenueStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Venue{

    private Long venueId;
    private String venueName;
    private int capacity;
    private double price;
    private VenueStatus availability;

    private List<String> amenities;

    private List<String> events;

    private String createdBy;
    private String venueOwnerEmail;

    private Address address;

    private List<Contact> contacts;

    private List<VenueImage> venueImages;

}

