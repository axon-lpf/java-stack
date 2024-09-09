/*
 * Copyright (C) 2013-2019 Hangzhou Youyun Technology Co., Ltd. All rights reserved
 */
package com.axon.java.stack.juc.reflect;


import java.util.List;
import java.util.function.BiConsumer;

/**
 * @Author: cjb@aduer.com
 * @Date: 2019/11/28 11:45
 * @Desc: 对象复制工具类
 */
public class CollectionCopyUtils {

    /**
     * 单个对象复制
     * @param resource 原对象
     * @param target 目标对象
     */
    public static void copyProperties(final Object resource, final Object target){
        try {
           // BeanUtils.copyProperties(resource, target);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 集合UI对象复制
     * @param resources 原集合
     * @param targets 目标集合
     * @param targetsElementTpe 目标集合类性
     * @param <T1>
     * @param <T2>
     */
    public static <T1,T2> void copyProperties(final List<T1> resources, final List<T2> targets, Class<T2> targetsElementTpe){
        if(resources == null || targets == null){
            return;
        }
        if(targets.size()!=0){
            //防止目标对象被覆盖，要求必须长度为零
            throw new RuntimeException("this size of targets must be zero!");
        }
        try{
            for (T1 orig: resources) {
                T2 t = targetsElementTpe.newInstance();
                copyProperties(orig,t);
                targets.add(t);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    public static <T1,T2> void copyProperties(final List<T1> resources, final List<T2> targets, Class<T2> targetsElementTpe, BiConsumer<T1,T2> tuBiConsumer){
        if(resources == null || targets == null){
            return;
        }
        if(targets.size()!=0){
            //防止目标对象被覆盖，要求必须长度为零
            throw new RuntimeException("this size of targets must be zero!");
        }
        try{
            for (T1 orig: resources) {
                T2 t = targetsElementTpe.newInstance();
                copyProperties(orig,t);
                if (tuBiConsumer!=null){
                    tuBiConsumer.accept(orig,t);
                }
                targets.add(t);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
