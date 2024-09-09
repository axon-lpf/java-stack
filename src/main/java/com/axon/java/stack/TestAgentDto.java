package com.axon.java.stack;

import cn.hutool.json.JSON;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Data
public class TestAgentDto {

    // 假设有一些字段
    private String name;
    private int age;
    private String department;

    private String company;

    public  TestAgentDto(){

    }

    // 其他字段和getter/setter方法
    public TestAgentDto(String name, int age, String department) {
        this.name = name;
        this.age = age;
        this.department = department;
    }
/*
    // Getters and setters...
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }*/

    // Method to find different fields
    public static List<String> findDifferences(TestAgentDto oldAgentDto, TestAgentDto newAgentDto) {
        List<String> differentFields = new ArrayList<>();

        Field[] fields = TestAgentDto.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object oldValue = field.get(oldAgentDto);
                Object newValue = field.get(newAgentDto);

                if (oldValue == null && newValue == null) {
                    continue;
                }
                if (oldValue == null || newValue == null || !oldValue.equals(newValue)) {
                    differentFields.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return differentFields;
    }

    public static void main(String[] args) {
        TestAgentDto oldAgent = new TestAgentDto("John", 30, "Sales");
        TestAgentDto newAgent = new TestAgentDto("John", 31, "Marketing");
        List<String> differences = ObjectComparator.findDifferences(oldAgent, newAgent);
        System.out.println("Different fields: " + differences);

        TestAgentDto objectWithSpecifiedFields = ObjectComparator.getObjectWithSpecifiedFields(newAgent, differences, TestAgentDto.class);
        System.out.println(objectWithSpecifiedFields);
    }
}
