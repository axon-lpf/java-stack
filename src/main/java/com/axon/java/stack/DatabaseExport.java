package com.axon.java.stack;

import java.sql.*;
import java.io.*;

public class DatabaseExport {
    public static void main(String[] args) {
        String url = "jdbc:mysql://192.168.2.104:3306/pay_order_0??useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&verifyServerCertificate=false&useSSL=false&zeroDateTimeBehavior=convertToNull&autoReconnect=true";
        String user = "root";
        String password = "123456";

        String query = "SHOW TABLES";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             FileWriter fileWriter = new FileWriter("tables_structure.sql")) {

            while (rs.next()) {
                String tableName = rs.getString(1);
                Statement createStmt = conn.createStatement();
                ResultSet createRs = createStmt.executeQuery("SHOW CREATE TABLE " + tableName);

                if (createRs.next()) {
                    String createTableStatement = createRs.getString(2); // 获取CREATE TABLE语句
                    fileWriter.write(createTableStatement + ";\n\n"); // 写入文件
                }

                createRs.close();
                createStmt.close();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("执行完成");
    }
}
