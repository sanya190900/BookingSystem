package com.diplom.bookingsystem.controller;

import com.diplom.bookingsystem.dto.AuthRequestDto;
import com.diplom.bookingsystem.dto.UserDto;
import com.diplom.bookingsystem.service.User.UserService;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.OK)
    public void registerUser(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userDto);
    }

//    @PostMapping("/login")
//    @ResponseStatus(value = HttpStatus.OK)
//    public AuthRequestDto login(@Valid @RequestBody AuthRequestDto authRequestDto) {
//        String token = userService.getJwtToken(authRequestDto.getUsername());
//        authRequestDto.setToken(token);
//        return authRequestDto;
//    }
}
