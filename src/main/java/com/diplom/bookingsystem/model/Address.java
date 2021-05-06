package com.diplom.bookingsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.diplom.bookingsystem.model.Place.Place;
import com.diplom.bookingsystem.model.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long address_id;

  @NotNull
  private String country;

  @NotNull
  private String city;

  @NotNull
  private String street;

  @NotNull
  private String house_number;

  @OneToOne(mappedBy = "address")
  @JsonIgnore
  private User user;

  @OneToOne(mappedBy = "address")
  @JsonIgnore
  private Place place;
}
