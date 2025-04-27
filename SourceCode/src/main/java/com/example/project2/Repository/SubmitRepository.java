package com.example.project2.Repository;

import com.example.project2.Entity.SubmitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmitRepository extends JpaRepository<SubmitEntity, String> {

}
