package com.diplom.bookingsystem.repository;

import com.diplom.bookingsystem.model.JwtBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@RepositoryRestResource(exported = false)
@Repository
public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklist, Long> {
    Boolean existsByToken(String username);

    void deleteByExpiryDateLessThan(LocalDateTime now);
}
