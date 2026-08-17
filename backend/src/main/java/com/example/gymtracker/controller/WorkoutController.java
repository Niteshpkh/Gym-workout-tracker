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

    @PostMapping
    public ResponseEntity<WorkoutEntity> createWorkout(
            @RequestBody WorkoutEntity workout) {

        WorkoutEntity savedWorkout =
                workoutService.createWorkout(workout);

        return new ResponseEntity<>(savedWorkout, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WorkoutEntity>> getAllWorkouts() {

        List<WorkoutEntity> workouts =
                workoutService.getAllWorkouts();

        return new ResponseEntity<>(workouts, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkoutById(
            @PathVariable("id") String id) {

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


    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkout(
            @RequestBody WorkoutEntity workout,
            @PathVariable("id") String id) {

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


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkout(
            @PathVariable("id")String id) {

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
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkoutEntity>> getWorkoutsByUser(
            @PathVariable("userId") String userId) {

        List<WorkoutEntity> workouts =
                workoutService.getWorkoutsByUser(userId);

        return new ResponseEntity<>(workouts, HttpStatus.OK);
    }
}