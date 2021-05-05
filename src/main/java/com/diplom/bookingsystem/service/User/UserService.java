package com.diplom.bookingsystem.service.User;

import com.diplom.bookingsystem.dto.User.AuthRequestDto;
import com.diplom.bookingsystem.dto.User.PasswordDto;
import com.diplom.bookingsystem.dto.User.UserDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
  ResponseEntity<?> saveUser(UserDto userDto);

  ResponseEntity<?> updateUser(UserDto userDto, HttpServletRequest request);

  ResponseEntity<?> authUser(AuthRequestDto authRequestDto);

  ResponseEntity<?> unAuthUser(HttpServletRequest request);

  ResponseEntity<?> getUser();

  ResponseEntity<?> deleteUser(HttpServletRequest request);

  ResponseEntity<?> disableUser(HttpServletRequest request);

  ResponseEntity<?> recoveryPassword(String username);

  ResponseEntity<?> updatePassword(PasswordDto passwordDto);
}
