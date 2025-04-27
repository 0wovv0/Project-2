package com.example.project2.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "example")
@NoArgsConstructor
@AllArgsConstructor
public class ExampleEntity {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "input")
    private String input;
    @Column(name = "output")
    private String output;
    @Column(name = "explaination")
    private String explaination;

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private ProblemEntity problem;

}