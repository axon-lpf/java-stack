package com.axon.java.stack;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;

public class Time3Test {

    public static void main(String[] args) {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 获取当前年月
        YearMonth currentYearMonth = YearMonth.from(currentDate);

        // 计算近三个月前的年月
        YearMonth threeMonthsAgo = currentYearMonth.minusMonths(3);

        // 格式化器，用于格式化输出
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 迭代每一月，确保每月的第一周从该月的第一天开始
        for (int i = 0; i < 3; i++) {
            // 获取当前月的第一天和最后一天
            LocalDate firstDayOfMonth = threeMonthsAgo.plusMonths(i).atDay(1);
            LocalDate lastDayOfMonth = threeMonthsAgo.plusMonths(i).atEndOfMonth();

            // 输出月份信息
            System.out.println("月份：" + firstDayOfMonth.getMonth() + " " + firstDayOfMonth.getYear());

            // 迭代每一周，并获取每周的时间段
            LocalDate currentDay = firstDayOfMonth;
            while (!currentDay.isAfter(lastDayOfMonth)) {
                int weekOfMonth = currentDay.get(WeekFields.of(java.util.Locale.getDefault()).weekOfMonth());

                // 获取当前周的开始和结束日期
                LocalDate startOfWeek = currentDay.with(DayOfWeek.MONDAY);
                LocalDate endOfWeek = currentDay.with(DayOfWeek.SUNDAY);

                System.out.println("    第 " + weekOfMonth + " 周的时间段：从 " + startOfWeek.format(formatter) + " 到 " + endOfWeek.format(formatter));

                // 移动到下一周的第一天
                currentDay = currentDay.plusDays(7 - currentDay.getDayOfWeek().getValue() + 1);
            }
            System.out.println();  // 输出一个空行，以区分不同月份
        }
    }
}
