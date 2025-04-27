package com.example.project2.Service;

import com.example.project2.Entity.TestcaseEntity;
import com.example.project2.Repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TestCaseService {
    @Autowired
    private TestCaseRepository testCaseRepository;

    public Optional<TestcaseEntity> getTestCase(String id) {
        return testCaseRepository.findById(id);
    }

    public Set<TestcaseEntity> getTestCasesByIds(Set<TestcaseEntity> testcaseEntities) {
        Set<TestcaseEntity> fetchedTestCases = new HashSet<>();
        for (TestcaseEntity testcase : testcaseEntities) {
            fetchedTestCases.add(testCaseRepository.findById(testcase.getId()).orElse(null));
        }
        return fetchedTestCases;
    }
}
