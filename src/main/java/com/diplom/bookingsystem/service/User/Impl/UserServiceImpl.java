package com.diplom.bookingsystem.service.User.Impl;

import com.diplom.bookingsystem.authentication.JwtUtils;
import com.diplom.bookingsystem.dto.AuthRequestDto;
import com.diplom.bookingsystem.dto.JwtResponse;
import com.diplom.bookingsystem.dto.MessageResponse;
import com.diplom.bookingsystem.dto.UserDto;
import com.diplom.bookingsystem.model.*;
import com.diplom.bookingsystem.repository.JwtBlacklistRepository;
import com.diplom.bookingsystem.repository.RoleRepository;
import com.diplom.bookingsystem.repository.UserRepository;
import com.diplom.bookingsystem.service.RefreshToken.RefreshTokenService;
import com.diplom.bookingsystem.service.User.UserService;
import com.diplom.bookingsystem.service.UserDetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtBlacklistRepository jwtBlacklistRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Override
    public ResponseEntity<?> saveUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username is already taken!"));
        }

        userRepository.save(makeUser(userDto));

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> updateUser(UserDto userDto, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + auth.getName()));

        if (!user.getUser_id().equals(userDto.getUser_id()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Access denied!"));

        if (userRepository.existsByUsername(userDto.getUsername()) &&
                !userDto.getUsername().equals(user.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username is already taken!"));
        }

        if(!userDto.getUsername().equals(user.getUsername()) ||
                !passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
            unAuthUser(request);

        userRepository.save(makeUser(userDto));

        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }

    @Override
    public ResponseEntity<?> authUser(AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(),
                        authRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userPrincipal);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt,
                refreshToken.getToken(),
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Transactional
    @Override
    public void unAuthUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);

        JwtBlacklist jwtBlacklist = new JwtBlacklist();
        jwtBlacklist.setToken(token);
        jwtBlacklist.setExpiryDate(LocalDateTime.now().plusMinutes(10));
        jwtBlacklistRepository.save(jwtBlacklist);
    }

    private User makeUser(UserDto userDto) {
        User user = new User(userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getEmail(),
                userDto.getName(),
                userDto.getSurname(),
                userDto.getPhone(),
                userDto.getAddress());

        if (userDto.getUser_id() != null)
            user.setUser_id(userDto.getUser_id());

        Set<String> strRoles = userDto.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRole(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "manager":
                        Role modRole = roleRepository.findByRole(ERole.ROLE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);

        return user;
    }
}
