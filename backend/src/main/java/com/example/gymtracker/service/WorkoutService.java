package com.example.gymtracker.service;

import com.example.gymtracker.entity.WorkoutEntity;
import com.example.gymtracker.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;

    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public WorkoutEntity createWorkout(WorkoutEntity workout) {
        return workoutRepository.save(workout);
    }

    public List<WorkoutEntity> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public WorkoutEntity getWorkoutById(String id) {
        return workoutRepository.findById(id).orElse(null);
    }

    public WorkoutEntity updateWorkout(WorkoutEntity workout, String id) {

        WorkoutEntity existingWorkout =
                workoutRepository.findById(id).orElse(null);
        if (existingWorkout != null) {
            existingWorkout.setUserId(workout.getUserId());
            existingWorkout.setName(workout.getName());
            existingWorkout.setDate(workout.getDate());
            existingWorkout.setDuration(workout.getDuration());
            existingWorkout.setExercises(workout.getExercises());
            return workoutRepository.save(existingWorkout);
        }
        return null;
    }

    public boolean deleteWorkout(String id) {
        WorkoutEntity existingWorkout =
                workoutRepository.findById(id).orElse(null);
        if (existingWorkout != null) {
            workoutRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<WorkoutEntity> getWorkoutsByUser(String userId) {
        return workoutRepository.findByUserId(userId);
    }
}