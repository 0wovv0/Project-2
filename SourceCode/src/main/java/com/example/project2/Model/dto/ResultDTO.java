package com.example.project2.Model.dto;

import com.example.project2.Entity.ENUM.Accepted;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultDTO {
    private String input;
    private String expectedOutput;
    private String output;
    private Accepted accepted;
    private long runningTime;
}
