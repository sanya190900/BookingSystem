package com.diplom.bookingsystem.service.User;

import com.diplom.bookingsystem.dto.AuthRequestDto;
import com.diplom.bookingsystem.dto.UserDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
  ResponseEntity<?> saveUser(UserDto userDto);

  ResponseEntity<?> updateUser(UserDto userDto, HttpServletRequest request);

  ResponseEntity<?> authUser(AuthRequestDto authRequestDto);

  void unAuthUser(HttpServletRequest request);
}
