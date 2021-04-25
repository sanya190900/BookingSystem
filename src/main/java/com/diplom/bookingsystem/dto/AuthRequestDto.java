package com.diplom.bookingsystem.dto;

import lombok.Data;

@Data
public class AuthRequestDto {
  String username;
  String password;
  String token;
}
