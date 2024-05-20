package com.engelhard.todoapp.models.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTaskDTO {
    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private UUID userId;
}
