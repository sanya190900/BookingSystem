package com.diplom.bookingsystem.dto.Place;

import com.diplom.bookingsystem.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PlaceDto {
    private Long place_id;

    private String name;

    private String description;

    private Address address;
}
