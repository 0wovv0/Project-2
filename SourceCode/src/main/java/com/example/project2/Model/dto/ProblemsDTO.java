package com.example.project2.Model.dto;


import com.example.project2.Entity.ENUM.Accepted;
import com.example.project2.Entity.ProblemEntity;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemsDTO {
    private ProblemEntity problemEntity;
    private int achievedPoint;
    private Accepted accepted;

    @Override
    public String toString() {
        return "Thằng nào gọi đến cái này thì chắc chết rồi: ProblemsDTO";
    }
}
