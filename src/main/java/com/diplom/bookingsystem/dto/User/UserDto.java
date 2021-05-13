package com.diplom.bookingsystem.dto.User;

import com.diplom.bookingsystem.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
  private Long user_id;

  private String username;

  private String password;

  private String email;

  private String name;

  private String surname;

  private String phone;

  private String pathToAvatar;

  private Address address;

  private Set<String> role;
}
