package com.diplom.bookingsystem.dto;

import com.diplom.bookingsystem.model.Address;
import com.diplom.bookingsystem.model.Role;
import lombok.Data;

@Data
public class UserDto {

  private Integer user_id;

  private String username;

  private String password;

  private String email;

  private String name;

  private String surname;

  private String phone;

  private Address address;

  private Role role;
}
