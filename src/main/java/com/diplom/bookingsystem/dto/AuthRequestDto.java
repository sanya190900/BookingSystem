package com.diplom.bookingsystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthRequestDto {
  @NotBlank
  String username;

  @NotBlank
  String password;
}
