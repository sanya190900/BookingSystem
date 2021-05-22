package com.diplom.bookingsystem.controller;

import com.diplom.bookingsystem.dto.Place.GalleryDto;
import com.diplom.bookingsystem.dto.Place.PlaceDto;
import com.diplom.bookingsystem.model.Place.Gallery;
import com.diplom.bookingsystem.repository.PlaceRepository;
import com.diplom.bookingsystem.service.Place.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceRepository placeRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlace(@PathVariable Long id) {
        return placeService.getPlace(id);
    }

    @GetMapping
    public ResponseEntity<?> getPlaces(
            @RequestParam Integer page,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String creatorName,
            @RequestParam(required = false) String creatorSurname
            ) {
        return placeService.getPlaces(page, pageSize, name, country, city, street, creatorName, creatorSurname);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPlace(@Valid @RequestBody PlaceDto placeDto) {
        return placeService.createPlace(placeDto);
    }

    @PreAuthorize("@creatorCheck.getOwner_id(#placeDto.place_id, authentication.principal.id) or hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> updatePlace(@Valid @RequestBody PlaceDto placeDto) {
        return placeService.updatePlace(placeDto);
    }

    @PreAuthorize("@creatorCheck.getOwner_id(#placeDto.place_id, authentication.principal.id) or hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deletePlace(@Valid @RequestBody PlaceDto placeDto) {
        return placeService.deletePlace(placeDto.getPlace_id());
    }
}
