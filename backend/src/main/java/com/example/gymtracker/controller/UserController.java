package com.example.gymtracker.controller;

import com.example.gymtracker.entity.UserEntity;
import com.example.gymtracker.repository.UserRepository;
import com.example.gymtracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")

public class UserController {
    private final UserRepository userRepository;
    private UserService userService;
    public UserController(UserService userService, UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id){
        Optional<UserEntity> user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody UserEntity user){
        UserEntity saveUser = userService.createUser(user);
        return new ResponseEntity<>(saveUser, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers(){
       List <UserEntity> users = userService.getAllUsers();
       return new ResponseEntity<>( users, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @RequestBody UserEntity user,
            @PathVariable String id) {

        UserEntity updatedUser = userService.updateUser(user, id);

        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }

        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}
