package com.diplom.bookingsystem.service.User.Impl;

import com.diplom.bookingsystem.model.User;
import com.diplom.bookingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
  @Autowired
  private UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = repository.findByUsername(username);

    if(user == null) {
      throw new UsernameNotFoundException("user not found");
    }

    List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));

    return new org.springframework.security.core.userdetails.User
        (user.getUsername(), user.getPassword(), authorities);
  }
}
