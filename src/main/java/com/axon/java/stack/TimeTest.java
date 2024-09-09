package com.axon.java.stack;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeTest {
    public static void main(String[] args) {
        LocalDateTime firstTime = LocalDateTime.parse("2023-01-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime lastTime = LocalDateTime.parse("2024-01-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        for (LocalDateTime time = firstTime; time.isBefore(lastTime); time = time.plusDays(1)) {
            LocalDateTime beginTime = time;
            LocalDateTime endTime = time.plusDays(1);
            System.out.println("beginTime" + beginTime);
            System.out.println("endTime" + endTime);
            System.out.println(time);

        }
    }
}
