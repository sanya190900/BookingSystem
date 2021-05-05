package com.diplom.bookingsystem.repository;

import com.diplom.bookingsystem.model.User.RecoveryToken;
import com.diplom.bookingsystem.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@RepositoryRestResource(exported = false)
@Repository
public interface RecoveryTokenRepository extends JpaRepository<RecoveryToken, Long> {
    Optional<RecoveryToken> findRecoveryTokenByToken(String token);

    @Transactional
    void deleteByUser(User user);
}
