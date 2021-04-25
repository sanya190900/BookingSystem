package com.diplom.bookingsystem.service.User.Impl;

import com.diplom.bookingsystem.dto.UserDto;
import com.diplom.bookingsystem.model.User;
import com.diplom.bookingsystem.repository.UserRepository;
import com.diplom.bookingsystem.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()),
            userDto.getEmail(), userDto.getName(), userDto.getSurname(), userDto.getPhone(),
            userDto.getAddress(), userDto.getRole());
        userRepository.save(user);
    }
}
