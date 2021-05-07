package com.diplom.bookingsystem.repository;

import com.diplom.bookingsystem.model.Place.Gallery;
import com.diplom.bookingsystem.model.Place.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(exported = false)
@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    @Transactional
    void deleteByPlace(Place place);
}
