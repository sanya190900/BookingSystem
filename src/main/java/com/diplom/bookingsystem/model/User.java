package com.diplom.bookingsystem.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer user_id;

  @NotNull
  @Column(unique = true, nullable = false)
  private String username;

  @Size(min = 6)
  private String password;

  @NotNull
  @Email
  @Column(nullable = false)
  private String email;

  @NotNull
  @Column(nullable = false)
  private String name;

  @NotNull
  @Column(nullable = false)
  private String surname;

  private String phone;

  private boolean activated = true;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id", referencedColumnName = "address_id")
  private Address address;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Role role;

  public User(String username, String password, String email, String name, String surname,
      String phone, Address address, Role role) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.name = name;
    this.surname = surname;
    this.phone = phone;
    this.address = address;
    this.role = role;
  }
}
