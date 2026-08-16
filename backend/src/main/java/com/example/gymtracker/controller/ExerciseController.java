package com.example.gymtracker.controller;

import com.example.gymtracker.entity.ExerciseEntity;
import com.example.gymtracker.service.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ExerciseEntity> createExercise(
            @RequestBody ExerciseEntity exercise) {

        ExerciseEntity savedExercise =
                exerciseService.createExercise(exercise);

        return new ResponseEntity<>(savedExercise, HttpStatus.CREATED);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<ExerciseEntity>> getAllExercises() {

        List<ExerciseEntity> exercises =
                exerciseService.getAllExercises();

        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getExerciseById(@PathVariable String id) {

        ExerciseEntity exercise =
                exerciseService.getExerciseById(id);

        if (exercise != null) {
            return new ResponseEntity<>(exercise, HttpStatus.OK);
        }

        return new ResponseEntity<>("Exercise not found", HttpStatus.NOT_FOUND);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateExercise(
            @RequestBody ExerciseEntity exercise,
            @PathVariable String id) {

        ExerciseEntity updatedExercise =
                exerciseService.updateExercise(exercise, id);

        if (updatedExercise != null) {
            return new ResponseEntity<>(updatedExercise, HttpStatus.OK);
        }

        return new ResponseEntity<>("Exercise not found", HttpStatus.NOT_FOUND);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExercise(@PathVariable String id) {

        boolean deleted = exerciseService.deleteExercise(id);

        if (deleted) {
            return new ResponseEntity<>("Exercise deleted successfully",
                    HttpStatus.OK);
        }

        return new ResponseEntity<>("Exercise not found",
                HttpStatus.NOT_FOUND);
    }
}