package com.example.gymtracker.repository;

import com.example.gymtracker.entity.WorkoutEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkoutRepository extends MongoRepository<WorkoutEntity , String> {
}
