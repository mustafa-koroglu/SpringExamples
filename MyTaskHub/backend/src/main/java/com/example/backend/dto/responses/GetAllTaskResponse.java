package com.example.backend.dto.responses;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetAllTaskResponse {
    private String title;
    private String description;

    private LocalDate dueDate;


}
