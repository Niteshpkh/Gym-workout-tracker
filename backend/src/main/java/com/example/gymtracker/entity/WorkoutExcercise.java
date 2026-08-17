package com.example.gymtracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExcercise {
    private String exerciseId;
    private List<WorkoutSet> sets;
}
