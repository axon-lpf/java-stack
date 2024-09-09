package com.axon.java.stack;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashExample {

    public static void main(String[] args) {
        String originalString = "pos"; // 你要生成哈希值的字符串
        String sha256Hash = generateHash(originalString, "SHA-256");
        System.out.println("原始字符串: " + originalString);
        System.out.println("SHA-256 哈希值: " + sha256Hash);
    }

    public static String generateHash(String input, String algorithm) {
        try {
            // 创建 MessageDigest 实例
            MessageDigest md = MessageDigest.getInstance(algorithm);
            // 执行哈希计算
            byte[] hashedBytes = md.digest(input.getBytes());

            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: Hashing algorithm not found - " + algorithm, e);
        }
    }
}
