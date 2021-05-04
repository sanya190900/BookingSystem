package com.diplom.bookingsystem.service.User.Impl;

import com.diplom.bookingsystem.authentication.JwtUtils;
import com.diplom.bookingsystem.dto.*;
import com.diplom.bookingsystem.model.*;
import com.diplom.bookingsystem.repository.*;
import com.diplom.bookingsystem.service.RefreshToken.RefreshTokenService;
import com.diplom.bookingsystem.service.User.UserService;
import com.diplom.bookingsystem.service.UserDetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.util.*;
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

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    RecoveryTokenRepository recoveryTokenRepository;

    @Override
    public ResponseEntity<?> saveUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken.", HttpStatus.BAD_REQUEST);
        }

        userRepository.save(makeUser(userDto, null));

        return new ResponseEntity<>(userRepository.findByUsername(userDto.getUsername()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateUser(UserDto userDto, HttpServletRequest request) {
        User user = getAuthenticatedUser();

        if (userRepository.existsByUsername(userDto.getUsername()) &&
                !userDto.getUsername().equals(user.getUsername())) {
            return new ResponseEntity<>("Username is already taken: " + userDto.getUsername(), HttpStatus.BAD_REQUEST);
        }

        if(!userDto.getUsername().equals(user.getUsername()) ||
                !passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
            unAuthUser(request);

        Address address = userDto.getAddress();
        address.setAddress_id(user.getAddress().getAddress_id());
        userDto.setAddress(address);
        userRepository.save(makeUser(userDto, user.getCreation_date_time()));

        return new ResponseEntity<>(userRepository.findByUsername(userDto.getUsername()), HttpStatus.OK);
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
    public ResponseEntity<?> unAuthUser(HttpServletRequest request) {
        User user = getAuthenticatedUser();
        String token = request.getHeader("Authorization").substring(7);

        JwtBlacklist jwtBlacklist = new JwtBlacklist();
        jwtBlacklist.setToken(token);
        jwtBlacklist.setExpiryDate(LocalDateTime.now().plusMinutes(10));
        jwtBlacklistRepository.save(jwtBlacklist);

        if (refreshTokenRepository.existsByUser(user))
            refreshTokenRepository.deleteByUser(user);

        return ResponseEntity.ok(new MessageResponse("User unauthenticated successfully."));
    }

    @Override
    public ResponseEntity<?> getUser() {
        User user = getAuthenticatedUser();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> deleteUser(HttpServletRequest request) {
        User user = getAuthenticatedUser();

        unAuthUser(request);

        userRepository.delete(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> disableUser(HttpServletRequest request) {
        User user = getAuthenticatedUser();

        unAuthUser(request);

        user.setEnabled(false);
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> recoveryPassword(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));
        String token = UUID.randomUUID().toString();

        //try {
        RecoveryToken recoveryToken = new RecoveryToken(user, token, LocalDateTime.now().plusMinutes(60));
        recoveryTokenRepository.save(recoveryToken);
            //String recoverPasswordLink = "http://localhost:4200/password/change?token=" + token;

            //new Thread(() -> emailService.sendMessageWithAttachment(email, SUBJECT, recoverPasswordLink, PATH_TO_ATTACHMENT)).start();
        //} catch (UserNotFoundException e) {
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //}
        return new ResponseEntity<>(recoveryToken, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updatePassword(PasswordDto passwordDto) {
        RecoveryToken recoveryToken = recoveryTokenRepository
                .findRecoveryTokenByToken(passwordDto.getToken())
                .orElseThrow(() -> new UsernameNotFoundException("Token Not Found: " + passwordDto.getToken()));

        final LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.isAfter(recoveryToken.getExpiryDate()))
            return new ResponseEntity<>("Recovery token is expired.", HttpStatus.BAD_REQUEST);

        User user = recoveryToken.getUser();
        user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + auth.getName()));
    }

    private User makeUser(UserDto userDto, LocalDateTime creationDateTime) {
        User user = new User(userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getEmail(),
                userDto.getName(),
                userDto.getSurname(),
                userDto.getPhone(),
                userDto.getAddress(),
                creationDateTime
                );

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
