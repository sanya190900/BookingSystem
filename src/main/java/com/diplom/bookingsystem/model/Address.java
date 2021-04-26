package com.diplom.bookingsystem.model;

import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;

@Data
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
  private User user;
}
