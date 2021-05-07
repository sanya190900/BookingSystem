package com.diplom.bookingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MessageException extends RuntimeException {

    public MessageException(String message) {
        super(String.format("Error with sending message : %s", message));
    }
}
