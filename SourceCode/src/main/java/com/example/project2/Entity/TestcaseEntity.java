package com.example.project2.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "testcase")
public class TestcaseEntity {
    @Id
    private String id;
    @Column(name = "Input", columnDefinition = "LONGTEXT")
    private String input;
    @Column(name = "Output", columnDefinition = "LONGTEXT")
    private String output;
    @Column(name = "valid_TimeRunning")
    private int validTimeRunning;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "problem_id")
    private ProblemEntity problem;

    @Override
    public int hashCode() {
        return Objects.hash(id); // Assuming 'id' is the primary key or unique identifier field
    }

}