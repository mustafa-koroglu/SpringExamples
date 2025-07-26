package com.example.backend.dto.requests;


import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTaskRequest {

    private int id;

    private String title;

    private String description;

    private LocalDate dueDate;

    private boolean status;
}
