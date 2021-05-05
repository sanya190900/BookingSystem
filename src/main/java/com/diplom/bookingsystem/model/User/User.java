package com.diplom.bookingsystem.model.User;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.diplom.bookingsystem.model.Address;
import com.diplom.bookingsystem.model.Place.Place;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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

  @JsonIgnore
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

  private String pathToAvatar;

  private boolean enabled = true;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id", referencedColumnName = "address_id")
  private Address address;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime creation_date_time;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<Place> places;

  public User(String username, String password, String email, String name, String surname,
      String phone, String pathToAvatar, Address address, LocalDateTime creation_date_time) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.name = name;
    this.surname = surname;
    this.phone = phone;
    this.pathToAvatar = pathToAvatar;
    this.address = address;
    this.creation_date_time = creation_date_time;
  }
}
