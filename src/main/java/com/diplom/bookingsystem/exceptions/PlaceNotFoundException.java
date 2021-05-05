package com.diplom.bookingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PlaceNotFoundException extends RuntimeException {

    public PlaceNotFoundException(String message) {
        super(String.format("Failed : %s", message));
    }
}
