package com.diplom.bookingsystem.repository;

import com.diplom.bookingsystem.model.Place.EService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import com.diplom.bookingsystem.model.Place.Service;

import java.util.Optional;

@RepositoryRestResource(exported = false)
@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByService(EService service);
}
