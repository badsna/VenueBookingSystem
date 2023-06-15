package com.example.bookingservice.service;

import com.example.bookingservice.dto.BookingRequestDto;

public interface BookingService {
    void reserveVenue(BookingRequestDto bookingRequestDto);

}
