package com.diplom.bookingsystem.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="addressId")
  private Long addressId;

  @Column(name="country")
  private String country;

  @Column(name="city")
  private String city;

  @Column(name="street")
  private String street;

  @Column(name="houseNumber")
  private String houseNumber;

  @OneToMany(mappedBy = "address", fetch = FetchType.LAZY,
      cascade = CascadeType.ALL)
  private Set<User> users;


}
