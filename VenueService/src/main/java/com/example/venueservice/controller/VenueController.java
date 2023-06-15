package com.example.venueservice.controller;

import com.example.venueservice.config.JwtService;
import com.example.venueservice.dto.VenueRequestDto;
import com.example.venueservice.dto.VenueResponseDto;
import com.example.venueservice.model.Venue;
import com.example.venueservice.service.VenueServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/venue")
@RequiredArgsConstructor
public class VenueController {
    private final VenueServiceImpl venueService;
    private final JwtService jwtService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/add_new_venue")
    public ResponseEntity<String> addNewVenue(@RequestPart VenueRequestDto venueRequestDto,
                                              @RequestPart List<MultipartFile> venueImages,
                                              @RequestHeader("Authorization") String authorizationHeader) throws IOException {

        String token = authorizationHeader.substring(7); // Remove the "Bearer " prefix
        String username = jwtService.extractUsername(token);
        venueRequestDto.setCreatedBy(username);

        venueService.addNewVenue(venueRequestDto, venueImages, path);
        return ResponseEntity.ok("Venue Added Successfully");
    }


    @GetMapping("search")
    public ResponseEntity<List<VenueResponseDto>> getVenue(@RequestParam(required = false) String area,
                                                           @RequestParam(required = false) Integer minimumCapacity,
                                                           @RequestParam(required = false) Double maximumPrice,
                                                           @RequestParam(required = false) String events,
                                                           @RequestParam(required = false) String venueName,
                                                           Pageable pageable) {

        List<VenueResponseDto> venueResponseDto = venueService.getVenue(area, minimumCapacity, maximumPrice, events, venueName, pageable);
        return ResponseEntity.ok(venueResponseDto);

    }


    @GetMapping("/get_venue_by_venueId/{venueId}")
    public ResponseEntity<VenueResponseDto> getVenueByVenueId(@PathVariable Long venueId) {
        VenueResponseDto venueResponseDto = venueService.getVenueByVenueId(venueId);
        return ResponseEntity.ok(venueResponseDto);
    }


    @GetMapping("/get_venue_by_venueOwnerEmail/{venueOwnerEmail}")
    public ResponseEntity<String> getVenueByVenueOwnerEmail(@PathVariable String venueOwnerEmail) {
        String data = venueService.getVenueByVenueOwnerEmail(venueOwnerEmail);
        return ResponseEntity.ok(data);
    }

    @PutMapping("/update_venue_information/{venueId}")
    public ResponseEntity<String> updateVenueInformation(@PathVariable Long venueId,
                                                         @RequestPart VenueRequestDto venueRequestDto,
                                                         @RequestPart("venueImages") List<MultipartFile> venueImages,
                                                         @RequestHeader("Authorization") String authorizationHeader) throws IOException {
        String token = authorizationHeader.substring(7); // Remove the "Bearer " prefix
        String username = jwtService.extractUsername(token);
        venueService.updateVenueInformation(venueId, venueRequestDto, venueImages, path,username);
        return ResponseEntity.ok("Updated successfully");
    }

    @PutMapping("/update_venue_status/{venueId}")
    public ResponseEntity<String> updateVenueStatus(@PathVariable Long venueId) {
        venueService.updateVenueStatus(venueId);
        return ResponseEntity.ok("Updated successfully");
    }

    @PutMapping("/update_venue_image/{venueId}")
    public ResponseEntity<String> updateVenueImage(@PathVariable Long venueId,
                                              @RequestPart MultipartFile venueImage
                                             ) throws IOException {


        venueService.updateVenueImage(venueId, venueImage, path);
        return ResponseEntity.ok("Venue Image Updated Successfully");
    }
}
