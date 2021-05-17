package com.diplom.bookingsystem.service.Booking;

import com.diplom.bookingsystem.dto.Booking.BookingDto;
import org.springframework.http.ResponseEntity;

public interface BookingService {
    ResponseEntity<?> reservePlace(BookingDto bookingDto);
}
