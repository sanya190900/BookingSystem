package com.diplom.bookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String refreshToken;
    private final static String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
