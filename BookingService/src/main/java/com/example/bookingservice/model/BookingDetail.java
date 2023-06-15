package com.example.bookingservice.model;

import com.example.bookingservice.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "booking_details")
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    private LocalDate eventFrom;
    private LocalDate eventTill ;
    private int numberOfGuest;
    @ElementCollection
    private List<String> specialRequests;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @CreationTimestamp
    private LocalDate BookingTimeStamp;

    private Long venueId;
    private String reservedBy;
    private String paymentOption;


}
