package com.diplom.bookingsystem.dto.Place;

import com.diplom.bookingsystem.model.Address;
import com.diplom.bookingsystem.model.Place.Service;
import com.diplom.bookingsystem.model.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class PlaceDto {
    private Long place_id;

    private String name;

    private String description;

    private Address address;

    private Set<String> service;

    private Set<String> pathsToPhotos;

    private User user;
}
