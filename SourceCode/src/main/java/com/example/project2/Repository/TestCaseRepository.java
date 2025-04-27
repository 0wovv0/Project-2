package com.example.project2.Repository;


import com.example.project2.Entity.TestcaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseRepository extends JpaRepository<TestcaseEntity, String>{ }
