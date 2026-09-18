package com.example.gymtracker.dto;

import com.example.gymtracker.entity.WorkoutExcercise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRequest {
    private String name;
    private LocalDate date;
    private Integer duration;
    private List <WorkoutExcercise> exercises;
}
