package com.diplom.bookingsystem.service.Place;

import com.diplom.bookingsystem.exceptions.PlaceNotFoundException;
import com.diplom.bookingsystem.model.Place.Place;
import com.diplom.bookingsystem.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatorCheck {
    @Autowired
    PlaceRepository placeRepository;

    public Boolean getOwner_id(Long place_id, Long user_id) {
        Place place = placeRepository.findById(place_id)
                .orElseThrow(() -> new PlaceNotFoundException("Place not found, id: " + place_id));
        return place.getUser().getUser_id().equals(user_id);
    }
}
