package com.diplom.bookingsystem.repository;

import com.diplom.bookingsystem.model.ERole;
import com.diplom.bookingsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole role);
}
