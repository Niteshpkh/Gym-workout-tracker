package com.example.gymtracker.service;

import com.example.gymtracker.entity.UserEntity;
import com.example.gymtracker.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUserName(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}