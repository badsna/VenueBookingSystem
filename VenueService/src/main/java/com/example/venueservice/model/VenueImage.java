package com.example.venueservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "venue_images")
@Getter
@Setter
public class VenueImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private String imagesUrl;

    @Column(name = "venue_id")
    private Long venueId;
}
