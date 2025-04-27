package com.example.project2.Service;

import com.example.project2.Entity.ENUM.Accepted;
import com.example.project2.Entity.ResultEntity;
import com.example.project2.Entity.SubmitEntity;
import com.example.project2.Entity.TestcaseEntity;
import com.example.project2.Model.FileDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class ExeMultiPartFile {
    @Autowired
    private TestCaseService testCaseService;

    public final static String sourceDir = "C:\\Users\\hokta\\OneDrive - Hanoi University of Science and Technology\\2023.2\\Project 2\\SRC\\Project2\\";

    public SubmitEntity CompileAndRunCpp(FileDetail fileDetail) throws IOException {
        Long totalTime = 0L;
        Set<ResultEntity> resultList = new HashSet<>();
        byte[] fileContent = fileDetail.getFile().getBytes();
        String content = new String(fileContent, StandardCharsets.UTF_8);
        // Fetch testCaseIDs from database to avoid LazyInitializationException
        Set<TestcaseEntity> testCaseEntities = fileDetail.getTestcaseIDs();
        // Tao doi tuong SubmitEntity
        SubmitEntity submitEntity = SubmitEntity.builder()
                .source("a")
                .resultList(null)
                .id(fileDetail.getSubmittedId())
                .user(fileDetail.getUserEntity())
                .running_time(0L)
                .Accepted(Accepted.ACCEPTED)
                .submitted_at(LocalDateTime.now())
                .language(fileDetail.getLanguage())
                .classEntity(fileDetail.getClassEntity())
                .problem_id(fileDetail.getProblemId())
                .build();

        String sourcePath = sourceDir + "Submittion\\" + fileDetail.getSubmittedId();
        String exeFilePath = sourceDir + "test.exe";
        // Tạo thư mục nếu chưa tồn tại
        File folder = new File(sourcePath);
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                System.out.println("Không thể tạo thư mục: " + folder.getPath());
                submitEntity.setAccepted(Accepted.SYSTEM_ERROR);
                return submitEntity;
            }
        }

        int count = 0;
        try {
            FileRelation.saveSourcetFile(fileDetail.getFile(), sourcePath);
            // Biên dịch chương trình C++
            ProcessBuilder compileProcessBuilder = new ProcessBuilder("g++", sourcePath + "\\test.cpp", "-o", exeFilePath);
            compileProcessBuilder.directory(new File(sourceDir));
            Process compileProcess = compileProcessBuilder.start();

            int compileExitCode = compileProcess.waitFor();
            if (compileExitCode != 0) {
                submitEntity.setAccepted(Accepted.COMPILE_ERROR);
                System.out.println("Compile exit code: " + compileExitCode);
                return submitEntity;
            }

            ProcessBuilder runProcessBuilder = new ProcessBuilder(exeFilePath);
            for (TestcaseEntity testCase : testCaseEntities) {
                System.out.println("TestCaseID: " + testCase.getId());
                String testCaseID = testCase.getId();

                // Tạo một đối tượng ProcessBuilder mới cho mỗi test case
                runProcessBuilder.directory(new File(sourceDir));

                // Gán file input cho chương trình
                File inputFileForProcess = new File(sourceDir + "TestCase\\" + fileDetail.getProblemId() + "\\" + testCaseID + "\\input.txt");

                long startTime = System.currentTimeMillis();

                Process runProcess = runProcessBuilder.start();

                // Ghi dữ liệu đầu vào vào tiến trình
                Thread inputThread = new Thread(() -> {
                    try (BufferedReader reader = new BufferedReader(new FileReader(inputFileForProcess));
                         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()))) {
                        char[] buffer = new char[1024];
                        int bytesRead;
                        while ((bytesRead = reader.read(buffer)) != -1) {
                            writer.write(buffer, 0, bytesRead);
                            writer.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            runProcess.getOutputStream().close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                inputThread.start();

                // Đọc dữ liệu đầu ra của tiến trình
                StringBuilder processOutput = new StringBuilder();
                Thread outputThread = new Thread(() -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            processOutput.append(line).append(System.lineSeparator());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                outputThread.start();

                inputThread.join();
                int runExitCode = runProcess.waitFor();
                outputThread.join();

                long finishTime = System.currentTimeMillis();
                long runTime = finishTime - startTime;
                totalTime += runTime;

                System.out.println("Run exit code: " + runExitCode);
                System.out.println("Total time: " + runTime + "ms");

                // So sánh đầu ra của chương trình với file output
                try (BufferedReader outputReader = new BufferedReader(new FileReader(sourceDir + "TestCase\\" + fileDetail.getProblemId() + "\\" + testCaseID + "\\expectOutput.txt"))) {
                    boolean check = true;
                    // Tạo một đối tượng File đại diện cho tệp đầu ra
                    File processOutputFile = new File(folder, testCaseID + ".txt");

                    // Tạo một đối tượng FileWriter để ghi dữ liệu vào tệp
                    try (FileWriter fileWriter = new FileWriter(processOutputFile);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        BufferedReader runOutputReader = new BufferedReader(new StringReader(processOutput.toString()))) {
                        String outputLine;
                        String runOutputLine;
                        while ((runOutputLine = runOutputReader.readLine()) != null) {
                            if ((outputLine = outputReader.readLine()) != null && !outputLine.equals(runOutputLine)) {
                                check = false;
                            }
                            bufferedWriter.write(runOutputLine);
                            bufferedWriter.newLine();
                        }
                        if ((outputReader.readLine()) != null) {
                            check = false;
                        }
                    }

                    ResultEntity resultEntity = ResultEntity.builder()
                            .submition(submitEntity)
                            .testCase(testCase)
                            .accepted(Accepted.WRONG_ANSWER)
                            .runningTime((int) runTime)
                            .build();
                    if(check){
                        if(runTime <= testCase.getValidTimeRunning()){
                            resultEntity.setAccepted(Accepted.ACCEPTED);
                        } else{
                            resultEntity.setAccepted(Accepted.TIME_LIMIT_EXCEEDED);
                            check = false;
                        }
                    }
                    resultList.add(resultEntity);

                    System.out.println("Kết quả: " + check);
                    if(check){
                        count++;
                    } else{
                        submitEntity.setAccepted(Accepted.PARTIAL);
                    }
                }
                runProcess.destroy();
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(count < 0) submitEntity.setAccepted(Accepted.REJECTED);
        submitEntity.setResultList(resultList);
        submitEntity.setRunning_time(totalTime);
        return submitEntity;
    }

    public static String getFileContent(String path) {
        File file = new File(path);
        if(!file.exists()) {
            System.out.println("File không tồn tại: " + path);
            return null;
        }
        else{
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();
                return content.toString();
            } catch (IOException e) {
                System.out.println("Không thể đọc file: " + path);
                return null;
            }
        }

    }



}
