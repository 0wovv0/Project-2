package com.example.project2.Service;


import com.example.project2.Entity.ExampleEntity;
import com.example.project2.Entity.ProblemEntity;
import com.example.project2.Entity.TestcaseEntity;
import com.example.project2.Exception.ResponseException;
import com.example.project2.Model.dto.ProblemsDTO;
import com.example.project2.Repository.ProblemRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProblemService {
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private FileRelation fileRelation;

    public final static String sourceDir = "C:\\Users\\hokta\\OneDrive - Hanoi University of Science and Technology\\2023.2\\Project 2\\SRC\\Project2\\";


    public void addProblem(ProblemEntity problemEntity) {
        try{
            if(!problemRepository.existsById(problemEntity.getId())) {
                problemRepository.save(problemEntity);
            }
            else{
                throw new ResponseException("Problem is already exist");
            }
        } catch (Exception e) {
            throw new ResponseException("Some thing wrong!");
        }
    }

    public List<ProblemEntity> getAllProblems() {
        return problemRepository.findAll();
    }

    public  Optional<ProblemEntity> getProblemByID(String id) {
        if(!problemRepository.existsById(id)){
            throw new ResponseException("Problem not found");
        }
        Optional<ProblemEntity>  problemEntity = problemRepository.findById(id);
        problemEntity.get().setConstraints(fileRelation.readStringFromFile(sourceDir  +"Problems\\" + id + "\\Constraint.txt"));
        problemEntity.get().setInput(fileRelation.readStringFromFile(sourceDir  +"Problems\\" + id + "\\inputDescription.txt"));
        problemEntity.get().setOutput(fileRelation.readStringFromFile(sourceDir  +"Problems\\" + id + "\\outputDescription.txt"));
        problemEntity.get().setDescription(fileRelation.readStringFromFile(sourceDir  +"Problems\\" + id + "\\Description.txt"));
        for(ExampleEntity example : problemEntity.get().getExamples()){
            example.setExplaination(fileRelation.readStringFromFile(sourceDir  +"Problems\\" + id + "\\Examples\\" + example.getId() + "\\Explaination.txt"));
            example.setInput(fileRelation.readStringFromFile(sourceDir  +"Problems\\" + id + "\\Examples\\" + example.getId() + "\\Input.txt"));
            example.setOutput(fileRelation.readStringFromFile(sourceDir  +"Problems\\" + id + "\\Examples\\" + example.getId() + "\\Output.txt"));
        }

        return problemEntity;
    }


    public Set<TestcaseEntity> getTestCasesByProblemID(String problemID) {
        Optional<ProblemEntity> problemEntity = problemRepository.findById(problemID);
        return problemEntity.map(ProblemEntity::getTestcaseList).orElse(null);
    }



    public void addProblemEntity(ProblemEntity problemEntity) {
        Faker faker = new Faker();
        problemEntity.setId(faker.idNumber().valid());

        fileRelation.writeStringToFile(sourceDir  +"Problems\\" + problemEntity.getId() + "\\Description.txt", problemEntity.getDescription());
        fileRelation.writeStringToFile(sourceDir  +"Problems\\" + problemEntity.getId() + "\\inputDescription.txt", problemEntity.getInput());
        fileRelation.writeStringToFile(sourceDir  +"Problems\\" + problemEntity.getId() + "\\outputDescription.txt", problemEntity.getOutput());
        fileRelation.writeStringToFile(sourceDir  +"Problems\\" + problemEntity.getId() + "\\Constraint.txt", problemEntity.getConstraints());
        problemEntity.setDescription("");
        problemEntity.setInput("");
        problemEntity.setOutput("");
        problemEntity.setConstraints("");

        if(problemEntity.getExamples() != null){
            for(ExampleEntity example : problemEntity.getExamples()){
                example.setId(faker.idNumber().valid());
                fileRelation.writeStringToFile(sourceDir  +"Problems\\" + problemEntity.getId() + "\\Examples\\" + example.getId() + "\\Input.txt", example.getInput());
                fileRelation.writeStringToFile(sourceDir  +"Problems\\" + problemEntity.getId() + "\\Examples\\" + example.getId() + "\\Output.txt", example.getOutput());
                example.setInput("");
                example.setOutput("");
            }
        }

        if(problemEntity.getTestcaseList() != null){
            for(TestcaseEntity testcase : problemEntity.getTestcaseList()){
                testcase.setId(faker.idNumber().valid());
                fileRelation.writeStringToFile(sourceDir  +"TestCase\\" + problemEntity.getId() + "\\Testcases\\" + testcase.getId() + "\\Input.txt", testcase.getInput());
                fileRelation.writeStringToFile(sourceDir  +"TestCase\\" + problemEntity.getId() + "\\Testcases\\" + testcase.getId() + "\\expectOutput.txt", testcase.getOutput());
                testcase.setInput("");
                testcase.setOutput("");
            }
        }

        problemRepository.save(problemEntity);
    }

    public void updateProblem(ProblemEntity problemEntity) {
        problemRepository.save(problemEntity);
    }

}