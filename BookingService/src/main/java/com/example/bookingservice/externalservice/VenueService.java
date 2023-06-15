package com.example.bookingservice.externalservice;

import com.example.bookingservice.model.Venue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "VENUE-SERVICE")
public interface VenueService {
    @GetMapping("api/venue/get_venue_by_venueId/{venueId}")
    Venue getVenueByVenueId(@PathVariable Long venueId);

    @PutMapping("api/venue/update_venue_status/{venueId}")
    void updateVenueStatus(@PathVariable Long venueId);
}
