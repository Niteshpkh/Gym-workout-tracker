package com.example.gymtracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
@Data
public class UserEntity {
    @Id
    private String id;
    @NonNull
    @Indexed(unique = true)
    private String userName;
    @NonNull
    @Indexed(unique = true)
    private String email;
    @NonNull
    private String password;
    @NonNull
    private  LocalDateTime createdAt;
}
