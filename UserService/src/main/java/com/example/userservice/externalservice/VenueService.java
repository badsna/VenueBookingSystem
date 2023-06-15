package com.example.userservice.externalservice;

import com.example.userservice.model.Venue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name="VENUE-SERVICE")
public interface VenueService {


  /*  @GetMapping("api/venue/get_venue_by_venueOwnerEmail/{venueOwnerEmail}")
  Venue getVenueByVenueOwnerEmail(@PathVariable String venueOwnerEmail);*/

    @GetMapping("api/venue/get_venue_by_venueOwnerEmail/{venueOwnerEmail}")
    String getVenueByVenueOwnerEmail(@PathVariable String venueOwnerEmail);
}
