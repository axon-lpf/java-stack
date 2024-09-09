package com.axon.java.stack.juc.reflect;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ObjectCompareDemo {

    /**
     * 比较两个实例对象的值, 有不想同的值，返回对应字段的名称
     *
     * @param obj1
     * @param obj2
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> List<String> compareDemo(T obj1, T obj2) {

        List<String> differentFields = new ArrayList<>();

        if (Objects.isNull(obj1) || Objects.isNull(obj2)) {
            return new ArrayList<>();
        }
        Class<?> aClass = obj1.getClass();
        //获取所有obj字段的名称
        Field[] objectFields = aClass.getFields();

        for (int i = 0; i < objectFields.length; i++) {

            Field objectField = objectFields[i];
            objectField.setAccessible(true);

            Object oldValue = objectField.get(obj1);
            Object newValue = objectField.get(obj2);

            if (oldValue == null && newValue == null) {
                continue;
            }
            if (oldValue == null || newValue == null || !oldValue.equals(newValue)) {
                differentFields.add(objectField.getName());
            }
        }
        return differentFields;
    }
}
