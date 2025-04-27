package com.example.project2.Entity;

import com.example.project2.Entity.ENUM.Role;
import com.example.project2.Entity.ENUM.State;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "Email")
    private String Email;
    @Column(name = "FullName")
    private String Name;
    @Column(name = "Hashed_password")
    private String hashed_password;
    @Column(name = "State")
    private State state;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
    private Role role;

    @Column(name = "NumberOfAccept")
    private int numberOfAccept;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_class",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    private Set<ClassEntity> classList = new HashSet<>();

    @OneToMany(mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<ClassEntity> classTeachingList = new HashSet<>();

    public void addClass(ClassEntity classEntity) {
        classList.add(classEntity);
        classEntity.getStudentList().add(this);
    }

    public void removeClass(ClassEntity classEntity) {
        classList.remove(classEntity);
        classEntity.getStudentList().remove(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Email);
    }
}