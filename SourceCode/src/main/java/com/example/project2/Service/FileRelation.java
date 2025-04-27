package com.example.project2.Service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
public class FileRelation{

    public final static String sourceDir = "C:\\Users\\hokta\\OneDrive - Hanoi University of Science and Technology\\2023.2\\Project 2\\SRC\\Project2\\";

    public static void writeStringToFile(String content, String filePath) {
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Couldn't writeStringToFile");
        }
    }

    public static String readStringFromFile(String filePath) {
        File file = new File(filePath);
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Couldn't readStringFromFile");
        }

        return content.toString().trim(); // Trim to remove the trailing new line
    }

    public static void saveSourcetFile(MultipartFile fileDetail, String sourcePath) {
        // Tạo một đối tượng File mới cho tệp sẽ được ghi
        File inputFile = new File(sourcePath + File.separator + "test.cpp");

        // Sử dụng try-with-resources để đảm bảo FileOutputStream được đóng đúng cách
        try (FileOutputStream fos = new FileOutputStream(inputFile)) {
            // Ghi mảng byte từ MultipartFile vào FileOutputStream
            fos.write(fileDetail.getBytes());
        } catch (IOException e) {
            // Ghi log lỗi nếu có ngoại lệ IOException xảy ra
            System.out.println("Không thể lưu file: " + e.getMessage());
        }
    }
}