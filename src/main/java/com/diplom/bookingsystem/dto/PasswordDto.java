package com.diplom.bookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class PasswordDto {
    @NotNull
    String token;

    @NotNull
    @Size(min = 6)
    String password;
}
