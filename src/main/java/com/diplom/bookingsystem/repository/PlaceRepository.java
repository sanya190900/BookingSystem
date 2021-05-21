package com.diplom.bookingsystem.repository;

import com.diplom.bookingsystem.model.Place.Place;
import com.diplom.bookingsystem.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Boolean existsByName(String name);

    Optional<Place> findByName(String name);
}
