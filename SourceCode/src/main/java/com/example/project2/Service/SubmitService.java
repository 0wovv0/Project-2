package com.example.project2.Service;


import com.example.project2.Entity.ENUM.Accepted;
import com.example.project2.Entity.ENUM.Language;
import com.example.project2.Entity.ResultEntity;
import com.example.project2.Entity.SubmitEntity;
import com.example.project2.Entity.UserEntity;
import com.example.project2.Model.dto.ResultDTO;
import com.example.project2.Model.dto.SubmitDTO;
import com.example.project2.Repository.SubmitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.tuple.Pair;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SubmitService {
    @Autowired
    private SubmitRepository submitRepository;
    @Autowired
    private FileRelation fileRelation;

    public final static String sourceDir = "C:\\Users\\hokta\\OneDrive - Hanoi University of Science and Technology\\2023.2\\Project 2\\SRC\\Project2\\";

    public void saveSubmitEntity(SubmitEntity submitEntity) {
        submitRepository.save(submitEntity);
    }

    public Set<SubmitEntity> getSubmitOfEachUser(String userID, String classID, String problemID) {
        Set<SubmitEntity> submitEntities = new HashSet<>();
        String query = "SELECT * FROM submittion WHERE user_id = ? AND class_id = ? AND problem_id = ?";

            try (Connection connection = JDBCConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, userID);
                preparedStatement.setString(2, classID);
                preparedStatement.setString(3, problemID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        SubmitEntity submitEntity = new SubmitEntity();
                        submitEntity.setId(resultSet.getString("id"));
                        submitEntity.setRunning_time(resultSet.getLong("running_time"));
                        submitEntity.setAccepted(Accepted.valueOf(resultSet.getString("accepted")));
                        submitEntity.setLanguage(Language.valueOf(resultSet.getString("language")));
                        submitEntity.setSubmitted_at(resultSet.getTimestamp("submitted_at").toLocalDateTime());
                        // Set other fields as needed

                        submitEntities.add(submitEntity);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return submitEntities;
    }

    public Set<SubmitEntity>getStSubmitOfClass(String classID){
        Set<SubmitEntity> submitEntities = new HashSet<>();
        String query = "SELECT * FROM submittion WHERE class_id = ?";

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, classID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    SubmitEntity submitEntity = new SubmitEntity();
                    submitEntity.setId(resultSet.getString("id"));
                    submitEntity.setRunning_time(resultSet.getLong("running_time"));
                    submitEntity.setAccepted(Accepted.valueOf(resultSet.getString("accepted")));
                    submitEntity.setLanguage(Language.valueOf(resultSet.getString("language")));
                    submitEntity.setSubmitted_at(resultSet.getTimestamp("submitted_at").toLocalDateTime());
                    submitEntity.setUser(new UserEntity());
                    submitEntity.getUser().setEmail(resultSet.getString("user_id"));
                    submitEntity.setProblem_id(resultSet.getString("problem_id"));
                    // Set other fields as needed

                    submitEntities.add(submitEntity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return submitEntities;
    }

    public Set<SubmitEntity>getStSubmitOfProblem(String classID){
        Set<SubmitEntity> submitEntities = new HashSet<>();
        String query = "SELECT * FROM submittion WHERE problem_id = ?";

        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, classID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    SubmitEntity submitEntity = new SubmitEntity();
                    submitEntity.setId(resultSet.getString("id"));
                    submitEntity.setRunning_time(resultSet.getLong("running_time"));
                    submitEntity.setAccepted(Accepted.valueOf(resultSet.getString("accepted")));
                    submitEntity.setLanguage(Language.valueOf(resultSet.getString("language")));
                    submitEntity.setSubmitted_at(resultSet.getTimestamp("submitted_at").toLocalDateTime());
                    submitEntity.setUser(new UserEntity());
                    submitEntity.getUser().setEmail(resultSet.getString("user_id"));
                    submitEntity.setProblem_id(resultSet.getString("problem_id"));
                    // Set other fields as needed

                    submitEntities.add(submitEntity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return submitEntities;
    }

    public Optional<SubmitDTO> getSubmitDTO(String id){
        Optional<SubmitEntity> submitEntity = submitRepository.findById(id);
        if(submitEntity.isPresent()){
            SubmitDTO submitDTO = new SubmitDTO();
            submitDTO.setResults(new HashSet<>());
            submitDTO.setAccepted(submitEntity.get().getAccepted());
            submitDTO.setSource(fileRelation.readStringFromFile(FileRelation.sourceDir + "Submittion\\" + id + "\\test.cpp"));
            Set<ResultEntity> resultSet = submitEntity.get().getResultList();
            for(ResultEntity result : resultSet){
                String realOutput = fileRelation.readStringFromFile(FileRelation.sourceDir + "Submittion\\" + id + "\\" + result.getTestCase().getId() + ".txt");
                String input = fileRelation.readStringFromFile(FileRelation.sourceDir + "Testcase\\" + submitEntity.get().getProblem_id()+ "\\"+result.getTestCase().getId() + "\\input.txt");
                String expectedOutput = fileRelation.readStringFromFile(FileRelation.sourceDir + "Testcase\\" + submitEntity.get().getProblem_id()+"\\"+ result.getTestCase().getId() + "\\expectOutput.txt");
                ResultDTO resultDTO = new ResultDTO(input, expectedOutput, realOutput, result.getAccepted(), result.getRunningTime());
                submitDTO.getResults().add(resultDTO);
            }
            return Optional.of(submitDTO);
        }
        return Optional.empty();
    }

    public Optional<SubmitEntity> getFullSubmitByID(String id) {
        Optional<SubmitEntity> submitEntity =  submitRepository.findById(id);
        if(submitEntity.isPresent()) {
            submitEntity.get().setSource(fileRelation.readStringFromFile(FileRelation.sourceDir + "Submittion\\" + id + "\\test.cpp"));

            if(submitEntity.get().getAccepted() == Accepted.COMPILE_ERROR){
                return submitEntity;
            }
            Set<ResultEntity> resultSet = submitEntity.get().getResultList();

            // Duyệt qua resultSet bằng for-each loop
            for (ResultEntity result : resultSet) {
                result.setOutput(fileRelation.readStringFromFile(FileRelation.sourceDir + "Submittion\\" + id + "\\" + result.getTestCase().getId() + ".txt"));
            }
        }
        return submitEntity;
    }

}