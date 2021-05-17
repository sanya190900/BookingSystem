package com.diplom.bookingsystem.dto.Booking;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookingDto {
    String messageToManager;
    String messageToCustomer;
    String emailManager;
    String emailCustomer;
}
