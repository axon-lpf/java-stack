package com.axon.java.stack;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

public class test4 {
    public static void main(String[] args) {
        Map<LocalDate, WeekDateVO> result = new TreeMap<>();

        LocalDateTime now = LocalDateTime.now().withHour(8).withMinute(0).withSecond(0);
        LocalDateTime localDate = now.minusMonths(3).withHour(8).withMinute(0).withSecond(0);
        // 直接处理年月日
        for (LocalDateTime time = localDate; !time.isAfter(now); time = time.plusHours(24)) {
            LocalDateTime finalEndTime = time.plusHours(24);
            LocalDateTime finalBeginTime = time;
            int weekOfMonth = time.get(WeekFields.of(Locale.getDefault()).weekOfMonth());
            LocalDate localDate1 = finalBeginTime.toLocalDate();
            String format = finalBeginTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            System.out.println("finalBeginTime:" + finalBeginTime + ",weekOfMonth:" + weekOfMonth + ",localDate1" + localDate1 + ",format:" + format);

            LocalDate startOfWeek = time.toLocalDate().with(DayOfWeek.MONDAY);
            LocalDate endOfWeek = time.toLocalDate().with(DayOfWeek.SUNDAY);
            WeekDateVO weekDateVO = new WeekDateVO();
            weekDateVO.setStartDate(startOfWeek);
            weekDateVO.setEndDate(endOfWeek);
            if (result.containsKey(startOfWeek)) {
                WeekDateVO weekDateVO1 = result.get(startOfWeek);
                System.out.println("加一下数");
                //result.get(startOfWeek).setActualAmount(NumberUtil.add(weekDateVO1.getActualAmount(), actualAmount));
            } else {
                result.put(startOfWeek, weekDateVO);
            }
        }
        System.out.println("执行完毕");

    }
}
