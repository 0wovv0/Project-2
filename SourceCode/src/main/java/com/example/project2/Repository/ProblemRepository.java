package com.example.project2.Repository;

import com.example.project2.Entity.ProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository  extends JpaRepository<ProblemEntity, String>{}