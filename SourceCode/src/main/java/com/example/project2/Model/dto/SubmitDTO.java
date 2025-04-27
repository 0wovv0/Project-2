package com.example.project2.Model.dto;

import com.example.project2.Entity.ENUM.Accepted;
import com.example.project2.Entity.ResultEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.tuple.Pair;


import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitDTO {
    Accepted Accepted;
    private String source;
    Set<ResultDTO> results;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SubmitDTO{");
        sb.append("accepted=").append(Accepted).append(", ");
        sb.append("source='").append(source).append('\'').append(", ");
        sb.append("results=");


        sb.append('}');
        return sb.toString();
    }

}