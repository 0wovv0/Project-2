package com.example.project2.Service;

import com.example.project2.Entity.ClassEntity;
import com.example.project2.Entity.ProblemEntity;
import com.example.project2.Repository.ClassRepository;
import com.example.project2.Exception.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    public void addClass(ClassEntity classEntity) {
        try{
            if(!classRepository.existsById(classEntity.getId())) {
                classRepository.save(classEntity);
            }
            else{
                throw new ResponseException("Class is already exist");
            }
        } catch (Exception e) {
            throw new ResponseException("Some thing wrong!");
        }
    }

    public Optional<ClassEntity> getClass(String classId) {
        return classRepository.findById(classId);
    }

    public void updateClass(ClassEntity classEntity) {
        classRepository.save(classEntity);
    }

    public void deleteClass(String classId) {
        classRepository.deleteById(classId);
    }

    public Set<ProblemEntity> getAllProblems(String classId) {
        Set<ProblemEntity> problemsList = null;
        Optional<ClassEntity> classEntity = classRepository.findById(classId); //.orElseThrow(() -> new ResponseException("Class not found"));
        if(classEntity.isPresent()) {
            problemsList = classEntity.get().getProblemList();
        }
        return problemsList;
    }

    public Set<ProblemEntity> getProblemsByClassID(String classID) {
        Optional<ClassEntity> classEntity = classRepository.findById(classID);
        if(classEntity.isPresent()) {
            return classEntity.get().getProblemList();
        }
        return null;
    }

    public Set<String> getAllProblemsID(String classId) {
        Set<String> problemsList = null;
        Optional<ClassEntity> classEntity = classRepository.findById(classId); //.orElseThrow(() -> new ResponseException("Class not found"));
        if(classEntity.isPresent()) {
            for(ProblemEntity problemEntity : classEntity.get().getProblemList()) {
                problemsList.add(problemEntity.getId());
            }
        }
        return problemsList;
    }


}