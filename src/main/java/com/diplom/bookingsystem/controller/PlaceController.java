package com.diplom.bookingsystem.controller;

import com.diplom.bookingsystem.dto.Place.GalleryDto;
import com.diplom.bookingsystem.dto.Place.PlaceDto;
import com.diplom.bookingsystem.model.Place.Gallery;
import com.diplom.bookingsystem.service.Place.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    PlaceService placeService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlace(@PathVariable Long id) {
        return placeService.getPlace(id);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPlace(@Valid @RequestBody PlaceDto placeDto) {
        return placeService.createPlace(placeDto);
    }

    @PreAuthorize("#placeDto.user.user_id == authentication.principal.id or hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> updatePlace(@Valid @RequestBody PlaceDto placeDto) {
        return placeService.updatePlace(placeDto);
    }

    @PreAuthorize("#placeDto.user.user_id == authentication.principal.id or hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deletePlace(@Valid @RequestBody PlaceDto placeDto) {
        return placeService.deletePlace(placeDto.getPlace_id());
    }
}
