package com.example.project2.Security;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
@Primary
public class SHA1Hashing implements Hashing {

    @Override
    public String hashPasword(String password) {
        try {
            // Tạo đối tượng MessageDigest với thuật toán SHA-1
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            // Mã hóa mật khẩu thành byte array
            byte[] hashedBytes = messageDigest.digest(password.getBytes());

            // Chuyển byte array thành chuỗi hex
            StringBuilder stringBuilder = new StringBuilder();
            for (byte hashedByte : hashedBytes) {
                stringBuilder.append(Integer.toString((hashedByte & 0xff) + 0x100, 16).substring(1));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            // Xử lý nếu thuật toán không được hỗ trợ
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean validatePasword(String originalPassword, String storedPassword) {
        // Mã hóa mật khẩu nhập vào và so sánh với mật khẩu đã lưu
        return hashPasword(originalPassword).equals(storedPassword);
    }
}
