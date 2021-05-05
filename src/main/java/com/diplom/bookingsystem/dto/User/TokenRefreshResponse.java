package com.diplom.bookingsystem.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenRefreshResponse {
    private String token;
    private String refreshToken;
    private static final String tokenType = "Bearer";
}
