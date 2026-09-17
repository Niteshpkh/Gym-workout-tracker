package com.example.gymtracker.repository;

import com.example.gymtracker.entity.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity ,String> {
    Optional<UserEntity> findByUserName(String userName);
}
