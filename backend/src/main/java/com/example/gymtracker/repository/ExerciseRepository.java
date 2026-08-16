package com.example.gymtracker.repository;

import com.example.gymtracker.entity.ExerciseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExerciseRepository extends MongoRepository <ExerciseEntity, String>{

}
