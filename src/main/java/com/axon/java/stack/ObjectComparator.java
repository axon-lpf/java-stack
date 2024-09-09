package com.axon.java.stack;

import java.lang.reflect.Field;
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
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object oldValue = field.get(oldObject);
                Object newValue = field.get(newObject);

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
            targetObject = targetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create target object", e);
        }

        Class<?> sourceClass = source.getClass();

        for (String fieldName : fieldNames) {
            try {
                Field sourceField = sourceClass.getDeclaredField(fieldName);
                sourceField.setAccessible(true);
                Object value = sourceField.get(source);

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
