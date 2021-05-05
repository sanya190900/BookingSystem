package com.diplom.bookingsystem.repository;

import com.diplom.bookingsystem.model.User.ERole;
import com.diplom.bookingsystem.model.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RepositoryRestResource(exported = false)
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole role);
}
