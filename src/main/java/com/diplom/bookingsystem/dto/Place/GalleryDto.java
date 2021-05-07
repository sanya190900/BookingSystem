package com.diplom.bookingsystem.dto.Place;

import com.diplom.bookingsystem.model.Place.Gallery;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class GalleryDto {
    Long gallery_id;

    Set<String> pathsToPhotos;
}
