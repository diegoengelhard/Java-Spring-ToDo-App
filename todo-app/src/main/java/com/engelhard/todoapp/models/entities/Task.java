package com.engelhard.todoapp.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tasks", schema = "public")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;
    
    @Column(name = "description", unique = true, nullable = false)
    private String description;

    public Task(String title, String description) {
        super();
        this.title = title;
        this.description = description;
    }
}
