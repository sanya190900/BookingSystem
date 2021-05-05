package com.diplom.bookingsystem.controller;

import com.diplom.bookingsystem.dto.Place.PlaceDto;
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

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> updatePlace(@Valid @RequestBody PlaceDto placeDto) {
        return placeService.updatePlace(placeDto);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable Long id) {
        return placeService.deletePlace(id);
    }
}
