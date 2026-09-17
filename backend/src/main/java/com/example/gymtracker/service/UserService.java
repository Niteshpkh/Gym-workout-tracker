package com.example.gymtracker.service;

import com.example.gymtracker.dto.SignupRequest;
import com.example.gymtracker.entity.UserEntity;
import com.example.gymtracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    // Explicit constructor injection
    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity registerUser(SignupRequest request) {
        if (userRepo.existsByUserName(request.getUserName())) {
            throw new IllegalArgumentException("Username '" + request.getUserName() + "' is already taken");
        }
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email '" + request.getEmail() + "' is already registered");
        }

        UserEntity user = new UserEntity();
        user.setUserName(request.getUserName().trim());
        user.setEmail(request.getEmail().trim());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        return userRepo.save(user);
    }

    public Optional<UserEntity> getUserById(String id) {
        return userRepo.findById(id);
    }

    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    public UserEntity updateUser(UserEntity updatedUser, String id) {
        return userRepo.findById(id)
                .map(existingUser -> {
                    // Update userName if provided
                    if (updatedUser.getUserName() != null && !updatedUser.getUserName().isBlank()) {
                        existingUser.setUserName(updatedUser.getUserName().trim());
                    }
                    // Update email if provided
                    if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank()) {
                        existingUser.setEmail(updatedUser.getEmail().trim());
                    }
                    // Hash new password if provided
                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }
                    // Save and return the updated entity
                    return userRepo.save(existingUser);
                })
                .orElse(null); // Return null so the controller can return 404
    }

    public boolean deleteUser(String id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }
    public Optional<UserEntity> findByUserName(SignupRequest user){
        return userRepo.findByUserName(String.valueOf(user));
    }
}