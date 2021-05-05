package com.diplom.bookingsystem.service.RefreshToken;

import com.diplom.bookingsystem.dto.User.TokenRefreshRequest;
import com.diplom.bookingsystem.model.User.RefreshToken;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

public interface RefreshTokenService {
    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyExpiration(RefreshToken token);

    void purgeExpired();

    ResponseEntity<?> refreshAccessToken(TokenRefreshRequest request);
}
