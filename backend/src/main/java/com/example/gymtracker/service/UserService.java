package com.example.gymtracker.service;

import com.example.gymtracker.entity.UserEntity;
import com.example.gymtracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepo;

    public UserService(UserRepository userRepository){
        this.userRepo = userRepository;
    }
    public UserEntity createUser(UserEntity user){
         return  userRepo.save(user);
    }

    public Optional<UserEntity> getUserById(String  id ){
        return userRepo.findById(id);
    }

    public List<UserEntity> getAllUsers(){
        return userRepo.findAll();
    }

    public UserEntity updateUser(UserEntity user, String id) {

        Optional<UserEntity> existingUserOptional = userRepo.findById(id);

        if (existingUserOptional.isPresent()) {

            UserEntity existingUser = existingUserOptional.get();

            existingUser.setUserName(user.getUserName());
            existingUser.setPassword(user.getPassword());
            existingUser.setEmail(user.getEmail());

            userRepo.save(existingUser);
        }
        return user;
    }
}
