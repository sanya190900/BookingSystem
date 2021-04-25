package com.diplom.bookingsystem.controller;

import com.diplom.bookingsystem.model.User;
import com.diplom.bookingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.OK)
    public void registerUser(@Valid @RequestBody User newUser) {
        System.out.println(newUser.toString());
        userRepository.save(newUser);
    }
}
