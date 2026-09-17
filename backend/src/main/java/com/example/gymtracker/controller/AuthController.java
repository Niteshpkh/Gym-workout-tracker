package com.example.gymtracker.controller;

import com.example.gymtracker.dto.LoginRequest;
import com.example.gymtracker.dto.LoginResponse;
import com.example.gymtracker.dto.SignupRequest;
import com.example.gymtracker.entity.UserEntity;
import com.example.gymtracker.security.JwtService;
import com.example.gymtracker.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUserName(),
                                request.getPassword()
                        )
                );

        String token = jwtService.generateToken(
                (org.springframework.security.core.userdetails.UserDetails)
                        authentication.getPrincipal()
        );

        return ResponseEntity.ok(new LoginResponse(token));
    }
    @PostMapping ("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest){
       userService.registerUser(signupRequest);
       return new ResponseEntity<>(HttpStatus.CREATED);
    }
}