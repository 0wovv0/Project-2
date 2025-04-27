package com.example.project2.Entity;


import com.example.project2.Entity.ENUM.Accepted;
import com.example.project2.Entity.ENUM.Status;
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
@Table(name = "result")
public class ResultEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "submition_id")
    private SubmitEntity submition; // tương ứng submitID

    @Id
    @ManyToOne
    @JoinColumn(name = "testcase_id")
    private TestcaseEntity testCase;

    @Column(name = "output")
    private String output;

    @Column
    @Enumerated(EnumType.STRING)
    private Accepted accepted;

    @Column(name = "running_time")
    private long runningTime;

    @Override
    public int hashCode() {
        return Objects.hash(submition.getId(), testCase.getId());
    }
}
