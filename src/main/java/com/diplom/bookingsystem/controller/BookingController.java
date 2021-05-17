package com.diplom.bookingsystem.controller;

import com.diplom.bookingsystem.dto.Booking.BookingDto;
import com.diplom.bookingsystem.service.Booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/reserve")
    public ResponseEntity<?> reserve(@Valid @RequestBody BookingDto bookingDto) {
        return bookingService.reservePlace(bookingDto);
    }
}
