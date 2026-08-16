package com.example.gymtracker.controller;

import com.example.gymtracker.entity.WorkoutEntity;
import com.example.gymtracker.service.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<WorkoutEntity> createWorkout(
            @RequestBody WorkoutEntity workout) {

        WorkoutEntity savedWorkout =
                workoutService.createWorkout(workout);

        return new ResponseEntity<>(savedWorkout, HttpStatus.CREATED);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<WorkoutEntity>> getAllWorkouts() {

        List<WorkoutEntity> workouts =
                workoutService.getAllWorkouts();

        return new ResponseEntity<>(workouts, HttpStatus.OK);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkoutById(
            @PathVariable String id) {

        WorkoutEntity workout =
                workoutService.getWorkoutById(id);

        if (workout != null) {
            return new ResponseEntity<>(workout, HttpStatus.OK);
        }

        return new ResponseEntity<>(
                "Workout not found",
                HttpStatus.NOT_FOUND
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkout(
            @RequestBody WorkoutEntity workout,
            @PathVariable String id) {

        WorkoutEntity updatedWorkout =
                workoutService.updateWorkout(workout, id);

        if (updatedWorkout != null) {
            return new ResponseEntity<>(
                    updatedWorkout,
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                "Workout not found",
                HttpStatus.NOT_FOUND
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkout(
            @PathVariable String id) {

        boolean deleted =
                workoutService.deleteWorkout(id);

        if (deleted) {
            return new ResponseEntity<>(
                    "Workout deleted successfully",
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                "Workout not found",
                HttpStatus.NOT_FOUND
        );
    }
}