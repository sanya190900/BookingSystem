package com.diplom.bookingsystem.service.Place;

import com.diplom.bookingsystem.dto.Place.GalleryDto;
import com.diplom.bookingsystem.dto.Place.PlaceDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;

public interface PlaceService {
    ResponseEntity<?> getPlace(Long id);

    ResponseEntity<?> createPlace(PlaceDto placeDto);

    ResponseEntity<?> updatePlace(PlaceDto placeDto);

    ResponseEntity<?> deletePlace(Long id);

    ResponseEntity<?> getPlaces(Integer page, Integer pageSize, String name, String country,
                                String city, String street, String creatorName, String creatorSurname);
}
