package com.axon.java.stack.data.structures.sort;

import java.util.Arrays;

/**
 * 希尔排序算法
 * 初始化数组:  [8, 9, 1, 7, 2, 3, 5, 4, 6, 0]
 * 处理公式： gap=length/2
 * 第一轮:
 * 目前有10个元素， 则gap=10/2=5;  则步长为5， 这样
 * 进行分组： 第一组： 则从索引为0开始，步长为5，  则是 [8,3]
 * 第二组： 则从索引为1开始，步长为5，   则是[9,5]
 * 第三组： 则从
 * <p>
 * <p>
 * 第二轮： 则在上一轮的步长基础上再次除以2，  gap= 5/2, 则步长为2
 * <p>
 * <p>
 * 第三轮：在在第二轮的步长基础之上再次除以2， gap=2/2 , 则步长为1
 */
public class ShellSortDemo {

    public static void main(String[] args) {

        int[] arr = new int[]{8, 9, 1, 7, 2,
                3, 5, 4, 6, 0};
        System.out.println("开始第一轮的处理");
        step1(arr);
        //[3, 5, 1, 6, 0, 8, 9, 7, 4, 2]
        System.out.println("开始第二轮的处理");
        step2(arr);
        System.out.println("开始第三轮的处理");
        step3(arr);
    }


    public static void shellSort(int[] arr) {

    }


    /**
     * 按照插入排序算法， 第一个元素，则从第5个元素开始
     *
     * @param arr
     */
    public static void step1(int[] arr) {

        for (int i = 5; i < arr.length; i++) {
            int temp = arr[i];
            for (int j = i - 5; j >= 0; j = j - 5) {
                if (arr[i] < arr[j]) {
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
            System.out.println(Arrays.toString(arr));
        }
    }


    public static void step2(int[] arr) {

        for (int i = 2; i < arr.length; i++) {
            int temp = arr[i];
            for (int j = i - 2; j >= 0; j = j - 2) {
                if (temp < arr[j]) {
                    //arr[i] = arr[j];
                    arr[j + 2] = temp;
                }
            }
            System.out.println(Arrays.toString(arr));
        }
    }


    public static void step3(int[] arr) {

        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            for (int j = i - 1; j >= 0; j = j - 1) {
                if (arr[i] < arr[j]) {
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
            System.out.println(Arrays.toString(arr));
        }
    }


}
