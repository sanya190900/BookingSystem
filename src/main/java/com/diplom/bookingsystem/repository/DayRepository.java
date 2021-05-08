package com.diplom.bookingsystem.repository;

import com.diplom.bookingsystem.model.Place.Day;
import com.diplom.bookingsystem.model.Place.EDay;
import com.diplom.bookingsystem.model.Place.Schedule;
import com.diplom.bookingsystem.model.User.ERole;
import com.diplom.bookingsystem.model.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RepositoryRestResource(exported = false)
@Repository
public interface DayRepository extends JpaRepository<Day, Long> {
    Optional<Day> findByDay(EDay day);
}
