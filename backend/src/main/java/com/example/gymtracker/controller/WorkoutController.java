package com.example.gymtracker.controller;

import com.example.gymtracker.dto.WorkoutRequest;
import com.example.gymtracker.entity.WorkoutEntity;
import com.example.gymtracker.service.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<WorkoutEntity> createWorkout(@RequestBody WorkoutRequest workoutRequest, @AuthenticationPrincipal UserDetails userDetails){
        WorkoutEntity savedWorkout = workoutService.createWorkout(workoutRequest, userDetails.getUsername());
        {
            return new ResponseEntity<>(savedWorkout, HttpStatus.CREATED);
        }
    }

    // 2. GET CURRENT USER'S WORKOUTS
    @GetMapping("/my-workouts")
    public ResponseEntity<List<WorkoutEntity>> getMyWorkouts(
            @AuthenticationPrincipal UserDetails userDetails) {

        List<WorkoutEntity> workouts = workoutService.getWorkoutForCurrentUser(userDetails.getUsername());
        return ResponseEntity.ok(workouts);
    }

    // 3. GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutEntity> getWorkoutById(@PathVariable String id) {
        WorkoutEntity workout = workoutService.getWorkoutById(id);
        if (workout != null) {
            return ResponseEntity.ok(workout);
        }
        return ResponseEntity.notFound().build();
    }

    // 4. UPDATE WORKOUT
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutEntity> updateWorkout(
            @PathVariable String id,
            @RequestBody WorkoutEntity workout) {

        WorkoutEntity updated = workoutService.updateWorkout(workout, id);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // 5. DELETE WORKOUT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable String id) {
        boolean deleted = workoutService.deleteWorkout(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}