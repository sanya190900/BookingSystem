package com.diplom.bookingsystem.service.Place;

import com.diplom.bookingsystem.dto.Place.GalleryDto;
import com.diplom.bookingsystem.dto.Place.PlaceDto;
import org.springframework.http.ResponseEntity;

public interface PlaceService {
    ResponseEntity<?> getPlace(Long id);

    ResponseEntity<?> createPlace(PlaceDto placeDto);

    ResponseEntity<?> updatePlace(PlaceDto placeDto);

    ResponseEntity<?> deletePlace(Long id);
}
