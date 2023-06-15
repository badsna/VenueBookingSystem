package com.example.venueservice.repo;

import com.example.venueservice.model.VenueImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueImageRepo extends JpaRepository<VenueImage,Long> {
}
