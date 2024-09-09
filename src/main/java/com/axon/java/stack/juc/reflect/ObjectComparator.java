package com.axon.java.stack.juc.reflect;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ObjectComparator {


    /**
     * 比较两个对象的所有字段，找出不同的字段
     *
     * @param oldObject 旧对象
     * @param newObject 新对象
     * @param <T>       对象类型
     * @return 字段名列表，表示两个对象之间不同的字段
     */
    public static <T> List<String> findDifferences(T oldObject, T newObject) {
        List<String> differentFields = new ArrayList<>();

        if (oldObject == null || newObject == null) {
            throw new IllegalArgumentException("Objects to compare cannot be null");
        }

        Class<?> clazz = oldObject.getClass();
        // 获取类对象的所有字段
        Field[] fields = clazz.getDeclaredFields();

        // 循环所有的字段
        for (Field field : fields) {
            // 设置有权限访问
            field.setAccessible(true);
            try {
                // 获取老对象字段导致hi
                Object oldValue = field.get(oldObject);
                //获取新对象的字段的值
                Object newValue = field.get(newObject);
                if (oldValue == null && newValue == null) {
                    continue;
                }
                // 比较逻辑
                boolean isDifferent = false;
                if (oldValue == null || newValue == null) {
                    isDifferent = true;  // 其中一个为 null，认为不同
                } else if (field.getType().equals(String.class)) {
                    isDifferent = !((String) oldValue).equals((String) newValue);
                } else if (field.getType().equals(Integer.class)) {
                    isDifferent = !((Integer) oldValue).equals((Integer) newValue);
                } else if (field.getType().equals(BigDecimal.class)) {
                    // 使用 compareTo 来比较 BigDecimal
                    isDifferent = ((BigDecimal) oldValue).compareTo((BigDecimal) newValue) != 0;
                } else {
                    // 对于其他类型，使用默认的 equals 方法
                    isDifferent = !oldValue.equals(newValue);
                }
                if (isDifferent) {
                    differentFields.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        return differentFields;
    }


    /**
     * 根据字段名称集合，从对象中获取对应字段的值，并返回包含这些值的新对象
     *
     * @param source      原对象实例
     * @param fieldNames  字段名称集合
     * @param targetClass 目标对象类型
     * @param <T>         目标对象类型
     * @return 包含指定字段值的目标对象
     */
    public static <T> T getObjectWithSpecifiedFields(Object source, List<String> fieldNames, Class<T> targetClass) {
        if (source == null || fieldNames == null || targetClass == null) {
            throw new IllegalArgumentException("Source object, fieldNames, and targetClass cannot be null");
        }

        T targetObject;
        try {
            //创建一个新的实例
            targetObject = targetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create target object", e);
        }
        //获取原始对象的类型
        Class<?> sourceClass = source.getClass();

        for (String fieldName : fieldNames) {
            try {
                //获取原始对象字段的值
                Field sourceField = sourceClass.getDeclaredField(fieldName);
                sourceField.setAccessible(true);
                Object value = sourceField.get(source);

                //为新对象某个字段设置值
                Field targetField = targetClass.getDeclaredField(fieldName);
                targetField.setAccessible(true);
                targetField.set(targetObject, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                // Optional: Set default values or handle the case where fields are not found
            }
        }

        return targetObject;
    }
}
