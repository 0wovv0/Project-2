package com.example.project2.Entity;


import com.example.project2.Entity.ENUM.State;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "class")
public class ClassEntity {
    @Id
    private String id;
    @Column
    private String type;
    @Column
    private String name;
    @Column
    private State status;
    @Column
    private LocalDateTime createdAt;
    @Column
    private String createdBy;

    // Relational with UserEntity
    @ManyToMany(mappedBy = "classList", cascade ={CascadeType.PERSIST, CascadeType.MERGE})
    private Set<UserEntity> studentList = new HashSet<>();

    // Relational with TeacherEntity
    @ManyToMany(mappedBy = "classList", cascade ={CascadeType.PERSIST, CascadeType.MERGE})
    private Set<UserEntity> userList = new HashSet<>();

    @ManyToMany(mappedBy = "classList", cascade ={CascadeType.PERSIST, CascadeType.MERGE} )
    private Set<ProblemEntity> problemList = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_email")
    private UserEntity teacher;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassEntity that = (ClassEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ClassEntity{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", created_at=" + createdAt +
                ", createdBy='" + createdBy + '\'';
    }

    public void addStudent(UserEntity student) {
        this.studentList.add(student);
        student.getClassList().add(this);
    }

    public void removeStudent(UserEntity student) {
        this.studentList.remove(student);
        student.getClassList().remove(this);
    }

    public void addProblem(ProblemEntity problem) {
        this.problemList.add(problem);
        problem.getClassList().add(this);
    }

    public void removeProblem(ProblemEntity problemEntity) {
        this.problemList.remove(problemEntity);
        problemEntity.getClassList().remove(this);
    }

}
