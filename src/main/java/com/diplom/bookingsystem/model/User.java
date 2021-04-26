package com.diplom.bookingsystem.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users",
  uniqueConstraints = {
          @UniqueConstraint(columnNames = "username")
  })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long user_id;

  @NotBlank
  @Column(nullable = false)
  private String username;

  @Size(min = 6)
  private String password;

  @NotBlank
  @Email
  @Column(nullable = false)
  private String email;

  @NotBlank
  @Column(nullable = false)
  private String name;

  @NotBlank
  @Column(nullable = false)
  private String surname;

  private String phone;

  private boolean activated = true;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id", referencedColumnName = "address_id")
  private Address address;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User(String username, String password, String email, String name, String surname,
      String phone, Address address) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.name = name;
    this.surname = surname;
    this.phone = phone;
    this.address = address;
  }
}
