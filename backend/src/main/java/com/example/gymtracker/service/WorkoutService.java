package com.example.gymtracker.service;

import com.example.gymtracker.dto.WorkoutRequest;
import com.example.gymtracker.entity.UserEntity;
import com.example.gymtracker.entity.WorkoutEntity;
import com.example.gymtracker.repository.UserRepository;
import com.example.gymtracker.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;

    public WorkoutService(WorkoutRepository workoutRepository, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    public WorkoutEntity createWorkout(WorkoutRequest request , String userName) {
        UserEntity user = userRepository.findByUserName(userName).orElseThrow(() -> new IllegalArgumentException("User not found" + userName));

        WorkoutEntity workout = new WorkoutEntity();
        workout.setUserId(user.getId());
        workout.setName(request.getName());
        workout.setDate(request.getDate());
        workout.setDuration(request.getDuration());
        workout.setExercises(request.getExercises());

        return workoutRepository.save(workout);

    }

    public List <WorkoutEntity> getWorkoutForCurrentUser(String userName){
        UserEntity user = userRepository.findByUserName(userName).orElseThrow(()-> new IllegalArgumentException("User not found" + userName));
        return workoutRepository.findByUserId(user.getId());
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