package com.example.gymtracker.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Document(collection = "users")
@Data
public class UserEntity {
    @Id
    private String id;
    @NonNull
    private String userName;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private  LocalDateTime createdAt;
}
