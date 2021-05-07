package com.diplom.bookingsystem.model.Place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "gallery")
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long gallery_id;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false, updatable = false)
    @JsonIgnore
    private Place place;

    private String pathToPhoto;

    public Gallery(Place place, String pathToPhoto) {
        this.place = place;
        this.pathToPhoto = pathToPhoto;
    }
}
