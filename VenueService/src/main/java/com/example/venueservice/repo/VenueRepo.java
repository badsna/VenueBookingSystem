package com.example.venueservice.repo;

import com.example.venueservice.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface VenueRepo extends JpaRepository<Venue,Long>, JpaSpecificationExecutor<Venue> {
    Venue findByContacts_ContactNumber(String contactNumber);


    List<Venue> findByAddress_AreaAndCapacityGreaterThanEqual(String area,int capacity);

/*
   Venue findByVenueOwnerEmail(String venueOwnerEmail);
*/

   Optional<Venue> findByVenueOwnerEmail(String venueOwnerEmail);

}
