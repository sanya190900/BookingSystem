package com.diplom.bookingsystem.model.Place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long schedule_id;

    @NotBlank
    private LocalTime start;

    @NotBlank
    private LocalTime stop;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false, updatable = false)
    @JsonIgnore
    private Place place;

    @ManyToOne
    @JoinColumn(name = "day_id", nullable = false, updatable = false)
    private Day day;

    @NotBlank
    private Double price;

    public Schedule(LocalTime start, LocalTime stop, Double price, Place place) {
        this.start = start;
        this.stop = stop;
        this.price = price;
        this.place = place;
    }
}
