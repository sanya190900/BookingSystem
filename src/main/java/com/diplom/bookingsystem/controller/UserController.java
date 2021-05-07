package com.diplom.bookingsystem.controller;

import com.diplom.bookingsystem.dto.User.AuthRequestDto;
import com.diplom.bookingsystem.dto.User.PasswordDto;
import com.diplom.bookingsystem.dto.User.TokenRefreshRequest;
import com.diplom.bookingsystem.dto.User.UserDto;
import com.diplom.bookingsystem.service.RefreshToken.RefreshTokenService;
import com.diplom.bookingsystem.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping
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

    @PostMapping("/disable")
    public ResponseEntity<?> disableUser(HttpServletRequest request) {
        return userService.disableUser(request);
    }

    @PostMapping("/password/recovery")
    public ResponseEntity<?> recoveryPassword(@RequestBody String username) {
        return userService.recoveryPassword(username);
    }

    @PostMapping("/password/update")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordDto passwordDto) {
        return userService.updatePassword(passwordDto);
    }
}
