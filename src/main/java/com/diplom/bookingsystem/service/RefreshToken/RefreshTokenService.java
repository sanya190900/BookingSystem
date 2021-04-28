package com.diplom.bookingsystem.service.RefreshToken;

import com.diplom.bookingsystem.dto.TokenRefreshRequest;
import com.diplom.bookingsystem.model.RefreshToken;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface RefreshTokenService {
    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyExpiration(RefreshToken token);

    void purgeExpired();

    ResponseEntity<?> refreshAccessToken(TokenRefreshRequest request);
}
