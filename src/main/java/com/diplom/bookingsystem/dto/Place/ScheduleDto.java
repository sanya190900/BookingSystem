package com.diplom.bookingsystem.dto.Place;

import com.diplom.bookingsystem.model.Place.Day;
import com.diplom.bookingsystem.model.Place.Place;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@AllArgsConstructor
@Data
public class ScheduleDto {
    Long schedule_id;

    private LocalTime start;

    private LocalTime stop;

    private Place place;

    private String day;

    private Double price;
}
