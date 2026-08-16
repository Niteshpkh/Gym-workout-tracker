package com.example.gymtracker.service;

import com.example.gymtracker.entity.ExerciseEntity;
import com.example.gymtracker.repository.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    // CREATE
    public ExerciseEntity createExercise(ExerciseEntity exercise) {
        return exerciseRepository.save(exercise);
    }

    // READ ALL
    public List<ExerciseEntity> getAllExercises() {
        return exerciseRepository.findAll();
    }

    // READ BY ID
    public ExerciseEntity getExerciseById(String id) {
        return exerciseRepository.findById(id).orElse(null);
    }

    // UPDATE
    public ExerciseEntity updateExercise(ExerciseEntity exercise, String id) {

        ExerciseEntity existingExercise =
                exerciseRepository.findById(id).orElse(null);

        if (existingExercise != null) {
            existingExercise.setName(exercise.getName());
            existingExercise.setMuscleGroup(exercise.getMuscleGroup());
            existingExercise.setDescription(exercise.getDescription());

            return exerciseRepository.save(existingExercise);
        }

        return null;
    }

    // DELETE
    public boolean deleteExercise(String id) {

        ExerciseEntity existingExercise =
                exerciseRepository.findById(id).orElse(null);

        if (existingExercise != null) {
            exerciseRepository.deleteById(id);
            return true;
        }

        return false;
    }
}