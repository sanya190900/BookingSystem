package com.diplom.bookingsystem.controller;

import com.diplom.bookingsystem.dto.AuthRequestDto;
import com.diplom.bookingsystem.dto.TokenRefreshRequest;
import com.diplom.bookingsystem.dto.UserDto;
import com.diplom.bookingsystem.service.RefreshToken.RefreshTokenService;
import com.diplom.bookingsystem.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping()
    public ResponseEntity<?> getUser() {
        return userService.getUser();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequestDto authRequestDto) {
        return userService.authUser(authRequestDto);
    }

    @PutMapping()
    @PreAuthorize("#userDto.user_id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto, HttpServletRequest request) {
        return userService.updateUser(userDto, request);
    }

    @GetMapping("/revoke")
    public ResponseEntity<?> unAuthorizeUser(HttpServletRequest request) {
        return userService.unAuthUser(request);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        return refreshTokenService.refreshAccessToken(request);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser(HttpServletRequest request) {
        return userService.deleteUser(request);
    }
}
