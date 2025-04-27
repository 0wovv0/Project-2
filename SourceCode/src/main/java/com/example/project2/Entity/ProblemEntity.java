package com.example.project2.Entity;

import com.example.project2.Entity.ENUM.Level;
import com.example.project2.Entity.ENUM.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "problem")
public class ProblemEntity {
    @Id
    private String id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "InputDescription", columnDefinition = "TEXT")
    private String input;
    @Column(name = "OutputDescription", columnDefinition = "TEXT")
    private String output;
    @Column(name = "Constraints", columnDefinition = "TEXT")
    private String constraints;

    @Enumerated(EnumType.STRING) // Đánh dấu trường enum
    @Column(name = "Level")
    private Level level;

    @Enumerated(EnumType.STRING) // Đánh dấu trường enum
    @Column(name = "Tag")
    private Tag tags;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Column(name = "Example", columnDefinition = "TEXT")
    Set<ExampleEntity> examples;

    @ManyToMany
    @JoinTable(
        name = "problem_class",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    private Set<ClassEntity> classList = new HashSet<>();

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<TestcaseEntity> testcaseList = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id); // Assuming 'id' is the primary key or unique identifier field
    }

    public void addClass(ClassEntity classEntity) {
        this.classList.add(classEntity);
        classEntity.getProblemList().add(this);
    }

    public void removeClass(ClassEntity classEntity) {
        this.classList.remove(classEntity);
        classEntity.getProblemList().remove(this);
    }

    public void addTestcase(TestcaseEntity testcaseEntity) {
        this.testcaseList.add(testcaseEntity);
        testcaseEntity.setProblem(this);
    }

    public void removeTestcase(TestcaseEntity testcaseEntity) {
        this.testcaseList.remove(testcaseEntity);
        testcaseEntity.setProblem(null);
    }

    @Override
    public String toString() {
        return "ProblemEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", constraints='" + constraints + '\'' +
                ", level=" + level +
                ", tags=" + tags +
                ", examples=" + examples +
                ", classList=" + classList +
                ", testcaseList=" + testcaseList +
                '}';
    }

}


