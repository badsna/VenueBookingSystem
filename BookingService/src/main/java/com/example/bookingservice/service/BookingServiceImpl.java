package com.example.bookingservice.service;

import com.example.bookingservice.customexception.DateMisMatchedException;
import com.example.bookingservice.customexception.VenueNotAvailableException;
import com.example.bookingservice.customexception.VenueNotFoundException;
import com.example.bookingservice.dto.BookingRequestDto;
import com.example.bookingservice.dto.PaymentRequestDto;
import com.example.bookingservice.dto.PaymentResponseDto;
import com.example.bookingservice.enums.BookingStatus;
import com.example.bookingservice.externalservice.EmailSenderService;
import com.example.bookingservice.externalservice.PaymentService;
import com.example.bookingservice.externalservice.VenueService;
import com.example.bookingservice.model.BookingDetail;
import com.example.bookingservice.model.EmailSender;
import com.example.bookingservice.model.Venue;
import com.example.bookingservice.repo.BookingRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepo bookingRepo;
    private final VenueService inventoryService;
    private final PaymentService paymentService;
    private final EmailSenderService emailSenderService;
    Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    public void sendMail(String userEmail){
        EmailSender emailSender=new EmailSender();
        emailSender.setToEmail(userEmail);
        emailSender.setBody("You can now access Venue Booking System");
        emailSender.setSubject("Welcome to Venue Booking System");
        emailSenderService.sendEmail(emailSender);
    }

    @Override
    public void reserveVenue(BookingRequestDto bookingRequestDto) {
        if(bookingRequestDto.getEventFrom().isBefore(java.time.LocalDate.now().plusDays(1)) || bookingRequestDto.getEventTill().isBefore(bookingRequestDto.getEventFrom().plusDays(1)) ){

            throw new DateMisMatchedException("Starting Date and Ending Date Of Event Cannot Be Before Or Today's Date");
        }

        BookingDetail bookingDetail = new BookingDetail();
        bookingDetail.setEventFrom(bookingRequestDto.getEventFrom());
        bookingDetail.setEventTill(bookingRequestDto.getEventTill());
        bookingDetail.setNumberOfGuest(bookingRequestDto.getNumberOfGuest());
        bookingDetail.setSpecialRequests(bookingRequestDto.getSpecialRequests());
        bookingDetail.setStatus(BookingStatus.PENDING);
        bookingDetail.setReservedBy(bookingRequestDto.getReservedBy());
        bookingDetail.setPaymentOption(bookingRequestDto.getPaymentOption());

        Venue venue = inventoryService.getVenueByVenueId(bookingRequestDto.getVenueId());
        String venueStatus = String.valueOf(venue.getAvailability());

        if (venue != null) {
            logger.info("Checking With venueStatus Instead Of venue.getAvailability");

            if (venueStatus.equals("AVAILABLE")) {
                bookingDetail.setVenueId(bookingRequestDto.getVenueId());

                PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
                paymentRequestDto.setAmount(venue.getPrice());

                if (bookingRequestDto.getPaymentOption().equals("eSewa")) {

                    paymentRequestDto.setPayment_option(bookingRequestDto.getPaymentOption());
                    PaymentResponseDto paymentResponseDto = paymentService.makePaymentUsingeSewa(paymentRequestDto);

                    logger.info(paymentResponseDto.getTransaction_code() + " " + "eSewa");

                } else if (bookingRequestDto.getPaymentOption().equals("fonePay")) {

                    paymentRequestDto.setPayment_option(bookingRequestDto.getPaymentOption());
                    PaymentResponseDto paymentResponseDto = paymentService.makePaymentUsingfonePay(paymentRequestDto);

                    logger.info(paymentResponseDto.getTransaction_code() + " " + "fonePay");
                } else {
                    paymentRequestDto.setPayment_option(bookingRequestDto.getPaymentOption());
                    PaymentResponseDto paymentResponseDto = paymentService.makePaymentUsingImePay(paymentRequestDto);

                    logger.info(paymentResponseDto.getTransaction_code() + " " + "ImePay");
                }
                bookingDetail.setStatus(BookingStatus.CONFORMED);

                bookingRepo.save(bookingDetail);

                inventoryService.updateVenueStatus(bookingDetail.getVenueId());
                logger.info("Status of Venue Has Been Changed To Booking");

                sendMail(bookingRequestDto.getReservedBy());

            } else {
                throw new VenueNotAvailableException("Selected venue Is Already Booked");
            }
        } else {
            throw new VenueNotFoundException("Venue Is Not Found");
        }
    }
}
