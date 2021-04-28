package com.diplom.bookingsystem.controller;

import com.diplom.bookingsystem.dto.AuthRequestDto;
import com.diplom.bookingsystem.dto.TokenRefreshRequest;
import com.diplom.bookingsystem.dto.UserDto;
import com.diplom.bookingsystem.service.RefreshToken.RefreshTokenService;
import com.diplom.bookingsystem.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequestDto authRequestDto) {
        return userService.authUser(authRequestDto);
    }

    @RequestMapping("/revoke")
    public void revokeToken(HttpServletRequest request) {
        userService.unAuthUser(request);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        return refreshTokenService.refreshAccessToken(request);
    }
}
