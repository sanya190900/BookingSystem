package com.diplom.bookingsystem.repository;

import com.diplom.bookingsystem.model.User.RefreshToken;
import com.diplom.bookingsystem.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@RepositoryRestResource(exported = false)
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Transactional
    void deleteByExpiryDateLessThan(LocalDateTime now);

    Boolean existsByUser(User user);

    @Transactional
    void deleteByUser(User user);
}
