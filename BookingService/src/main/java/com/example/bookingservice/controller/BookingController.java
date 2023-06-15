package com.example.bookingservice.controller;

import com.example.bookingservice.config.JwtService;
import com.example.bookingservice.dto.BookingRequestDto;
import com.example.bookingservice.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking/")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final JwtService jwtService;

    @PostMapping("/reserve_venue")
    public ResponseEntity<String> reserveVenue(@RequestBody @Valid BookingRequestDto bookingRequestDto,
                                                @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String userName = jwtService.extractUsername(token);
        bookingRequestDto.setReservedBy(userName);

        bookingService.reserveVenue(bookingRequestDto);

        return ResponseEntity.ok("Venue Has Been Booked For " + bookingRequestDto.getEventFrom());
    }

}
