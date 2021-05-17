package com.diplom.bookingsystem.service.Booking.Impl;

import com.diplom.bookingsystem.dto.Booking.BookingDto;
import com.diplom.bookingsystem.service.Booking.BookingService;
import com.diplom.bookingsystem.service.Mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    EmailService emailService;

    private String PATH_TO_ATTACHMENT = "src/main/resources/email/reserve.html";

    @Override
    public ResponseEntity<?> reservePlace(BookingDto bookingDto) {
        new Thread(() -> emailService.sendMessageWithAttachment(
                bookingDto.getEmailCustomer(),
                "Reservation of place",
                bookingDto.getMessageToCustomer(),
                PATH_TO_ATTACHMENT)
        ).start();

        new Thread(() -> emailService.sendMessageWithAttachment(
                bookingDto.getEmailManager(),
                "Reservation of place",
                bookingDto.getMessageToManager(),
                PATH_TO_ATTACHMENT)
        ).start();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
