package com.diplom.bookingsystem.model.Place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "days")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long day_id;

    @Enumerated(EnumType.STRING)
    private EDay day;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Schedule> schedules;

    public Day(EDay day) {
        this.day = day;
    }
}
