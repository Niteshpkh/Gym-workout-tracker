package com.example.gymtracker.repository;

import com.example.gymtracker.entity.WorkoutEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WorkoutRepository extends MongoRepository<WorkoutEntity , String> {
    List<WorkoutEntity> findByUserId(String userId);
}
