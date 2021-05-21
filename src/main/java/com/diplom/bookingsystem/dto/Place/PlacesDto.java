package com.diplom.bookingsystem.dto.Place;

import com.diplom.bookingsystem.model.Place.Place;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PlacesDto {
    List<Place> content;
    Long totalElements;
}
