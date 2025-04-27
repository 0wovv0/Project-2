package com.example.project2.Entity;

import com.example.project2.Entity.ENUM.Accepted;
import com.example.project2.Entity.ENUM.Language;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Submittion")
public class SubmitEntity {
    @Id
    private String id;

    @Column(name = "Accepted")
    @Enumerated(EnumType.STRING)
    private Accepted Accepted;

    @Column(name = "Language")
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "Source")
    private String source;

    @Column(name = "submitted_at")
    private LocalDateTime submitted_at;

    @Column(name = "running_time")
    private long running_time;

    @Column
    private String problem_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassEntity classEntity;

    @OneToMany(mappedBy = "submition", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ResultEntity> resultList = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}