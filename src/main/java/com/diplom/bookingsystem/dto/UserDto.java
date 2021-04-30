package com.diplom.bookingsystem.dto;

import com.diplom.bookingsystem.model.Address;
import com.diplom.bookingsystem.model.ERole;
import com.diplom.bookingsystem.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class UserDto {
  private Long user_id;

  private String username;

  private String password;

  private String email;

  private String name;

  private String surname;

  private String phone;

  private Address address;

  private Set<String> role;
}
