package com.diplom.bookingsystem.controller;

import com.diplom.bookingsystem.authentication.JwtUtils;
import com.diplom.bookingsystem.dto.AuthRequestDto;
import com.diplom.bookingsystem.dto.JwtResponse;
import com.diplom.bookingsystem.dto.MessageResponse;
import com.diplom.bookingsystem.dto.UserDto;
import com.diplom.bookingsystem.model.ERole;
import com.diplom.bookingsystem.model.Role;
import com.diplom.bookingsystem.model.User;
import com.diplom.bookingsystem.repository.RoleRepository;
import com.diplom.bookingsystem.repository.UserRepository;
import com.diplom.bookingsystem.service.User.Impl.UserDetailsImpl;
import com.diplom.bookingsystem.service.User.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

//    @PostMapping("/signup")
//    @ResponseStatus(value = HttpStatus.OK)
//    public void registerUser(@Valid @RequestBody UserDto userDto) {
//        userService.saveUser(userDto);
//    }

//    @PostMapping("/login")
//    @ResponseStatus(value = HttpStatus.OK)
//    public AuthRequestDto login(@Valid @RequestBody AuthRequestDto authRequestDto) {
//        String token = userService.getJwtToken(authRequestDto.getUsername());
//        authRequestDto.setToken(token);
//        return authRequestDto;
//    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(userDto.getUsername(),
                encoder.encode(userDto.getPassword()),
                userDto.getEmail(),
                userDto.getName(),
                userDto.getSurname(),
                userDto.getPhone(),
                userDto.getAddress());

        Set<String> strRoles = userDto.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRole(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "manager":
                        Role modRole = roleRepository.findByRole(ERole.ROLE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequestDto authRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }
}
