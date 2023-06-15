package com.example.bookingservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter

public class BookingRequestDto {
    @NotNull(message = "Event Starting Date Cannot Be Null")
    private LocalDate eventFrom;

    @NotNull(message = "Event Ending Date Cannot Be Null")
    private LocalDate eventTill;
    @NotNull(message = "Number Of Guest Participating Must Be Specified ")
    private Integer numberOfGuest;
    private List<String> specialRequests;
    private Long venueId;
    private String reservedBy;

    @NotBlank(message = "Payment Option Must Be Specified")
    private String paymentOption;

}
