package com.axon.java.stack;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Map;

public class Time2Test {

    public static void main(String[] args) {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 获取当前年月
        YearMonth currentYearMonth = YearMonth.from(currentDate);

        // 计算近三个月前的年月
        YearMonth threeMonthsAgo = currentYearMonth.minusMonths(3);

        // 获取近三个月的第一天和最后一天
        LocalDate firstDayOfThreeMonthsAgo = threeMonthsAgo.atDay(1);
        LocalDate lastDayOfCurrentMonth = currentYearMonth.atEndOfMonth();

        // 格式化器，用于格式化输出
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 迭代每一天，并获取每周的时间段
        LocalDate currentDay = firstDayOfThreeMonthsAgo;
        while (!currentDay.isAfter(lastDayOfCurrentMonth)) {
            int weekOfMonth = currentDay.get(WeekFields.of(java.util.Locale.getDefault()).weekOfMonth());

            // 获取当前周的开始和结束日期
            LocalDate startOfWeek = currentDay.with(DayOfWeek.MONDAY);
            LocalDate endOfWeek = currentDay.with(DayOfWeek.SUNDAY);

            System.out.println("第 " + weekOfMonth + " 周的时间段：从 " + startOfWeek.format(formatter) + " 到 " + endOfWeek.format(formatter));

            // 移动到下一周的第一天
            currentDay = currentDay.plusDays(7 - currentDay.getDayOfWeek().getValue() + 1);
        }


        Map<String,String> map = new HashMap<>();
    }
}
