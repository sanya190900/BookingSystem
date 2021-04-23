package com.diplom.bookingsystem.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "users")
public class User{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="userId")
  private Long userId;

  @Size(min=2)
  private String username;

  @Email
  @Column(name="email")
  private String email;

  @Size(min=2)
  @Column(name="firstName")
  private String firstName;

  @Size(min=2)
  @Column(name="surname")
  private String surname;

  @Column(name="phone")
  private String phone;

  @Column(name="activated")
  private boolean activated = true;

  @Size(min=2)
  private String password;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "addressId", nullable = false)
  private Address address;

  @Transient
  private String passwordConfirm;

}
