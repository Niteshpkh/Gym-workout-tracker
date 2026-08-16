package com.example.gymtracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "workouts")
public class WorkoutEntity {

    @Id
    private String id;

    private String userId;

    private String name;

    private LocalDate date;

    private Integer duration;
}