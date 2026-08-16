package com.example.gymtracker.repository;

import com.example.gymtracker.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity ,String> {
}
