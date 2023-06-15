package com.example.venueservice.model;

import com.example.venueservice.enums.VenueStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

@Entity
@Table(name = "venues")
@Getter
@Setter
public class Venue{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long venueId;
    private String venueName;
    private int capacity;
    private double price;
    @Enumerated(EnumType.STRING)
    private VenueStatus availability;

    @ElementCollection
    private List<String> amenities;

    @ElementCollection
    private List<String> events;
    @Column(updatable = false)
    private String venueOwnerEmail;

    @Column(updatable = false)
    private String createdBy;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id")
    private List<Contact> contacts;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "venue_id")
    private List<VenueImage> venueImages;


}

