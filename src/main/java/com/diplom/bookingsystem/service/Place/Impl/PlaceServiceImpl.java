package com.diplom.bookingsystem.service.Place.Impl;

import com.diplom.bookingsystem.dto.Place.PlaceDto;
import com.diplom.bookingsystem.exceptions.PlaceNotFoundException;
import com.diplom.bookingsystem.model.Place.Place;
import com.diplom.bookingsystem.model.Address;
import com.diplom.bookingsystem.model.User.User;
import com.diplom.bookingsystem.repository.PlaceRepository;
import com.diplom.bookingsystem.repository.UserRepository;
import com.diplom.bookingsystem.service.Place.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<?> getPlace(Long id) {
        Place place = placeRepository
                .findById(id)
                .orElseThrow(() -> new PlaceNotFoundException("Place not found, id: " + id));

        return new ResponseEntity<>(place, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createPlace(PlaceDto placeDto) {
        if (placeRepository.existsByName(placeDto.getName())) {
            return new ResponseEntity<>("Name is already exist.", HttpStatus.BAD_REQUEST);
        }

        placeRepository.save(makePlace(placeDto));

        return new ResponseEntity<>(placeRepository.findByName(placeDto.getName()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updatePlace(PlaceDto placeDto) {
        if (placeRepository.existsByName(placeDto.getName())) {
            return new ResponseEntity<>("Name is already exist.", HttpStatus.BAD_REQUEST);
        }

        Place place = placeRepository
                .findById(placeDto.getPlace_id())
                .orElseThrow(() -> new PlaceNotFoundException("Place not found, id: " + placeDto.getPlace_id()));

        Address address = placeDto.getAddress();
        address.setAddress_id(place.getAddress().getAddress_id());
        placeDto.setAddress(address);
        placeRepository.save(makePlace(placeDto));

        return new ResponseEntity<>(placeRepository.findByName(placeDto.getName()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deletePlace(Long id) {
        placeRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Place makePlace(PlaceDto placeDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + auth.getName()));

        Place place = new Place(
                placeDto.getAddress(),
                placeDto.getName(),
                placeDto.getDescription()
        );

        if (placeDto.getPlace_id() != null)
            place.setPlace_id(placeDto.getPlace_id());
        else
            place.setUser(user);

        return place;
    }
}
