package com.axon.java.stack.algorithms.sort.shellsort;

import java.util.Arrays;

/**
 * /**
 * * 希尔排序算法
 * * 初始化数组: arr= [8, 9, 1, 7, 2, 3, 5, 4, 6, 0]
 * * 处理公式： gap=length/2
 * * 第一轮:
 * * 目前有10个元素， 则gap=10/2=5;  则步长为5， 按照插入比较的法则， 从插入的位置，向前进行比较
 *   1. arr[5]=3 往前数5个元素，则是arr[0],  则是arr[0]=3与 arr[5]=8 进行插入比较, 8>3 , 则位置互换  结果：[3, 9, 1, 7, 2, 8, 5, 4, 6, 0]
 *   2. arr[6]=5 往前数5个元素，则是arr[1],  则是arr[1]=9与 arr[6]=5 进行插入比较, 9>5 , 则位置互换  结果：[3, 5, 1, 7, 2, 8, 9, 4, 6, 0]
 *   3. arr[7]=4 往前数5个元素，则是arr[2],  则是arr[2]=1与 arr[7]=4 进行插入比较, 1<4 , 不进行互换  结果：[3, 5, 1, 7, 2, 8, 9, 4, 6, 0]
 *   4. arr[8]=6 往前数5个元素，则是arr[3],  则是arr[3]=7与 arr[8]=6 进行插入比较, 7>6 , 则位置互换  结果：[3, 5, 1, 6, 2, 8, 9, 4, 7, 0]
 *   5. arr[9]=0 往前数5个元素，则是arr[4],  则是arr[4]=2与 arr[9]=0 进行插入比较, 2>0 , 则位置互换  结果：[3, 5, 1, 6, 0, 8, 9, 4, 7, 2]
 *
 * * 第二轮： 则在上一轮的步长基础上再次除以2，  gap= 5/2, 则步长为2, [3, 5, 1, 6, 0, 8, 9, 4, 7, 2]
 *   1. arr[2]=1 往前数2个元素，则是arr[0],  则是arr[0]=3与 arr[2]=1 进行插入比较, 3>1 , 则位置互换  结果：[1, 5, 3, 6, 0, 8, 9, 4, 7, 2]   TODO  再次减去2就是-2， 不能比较了
 *
 *   2. arr[3]=6 往前数2个元素，则是arr[1],  则是arr[1]=5与 arr[3]=6 进行插入比较, 5<6 , 不进行交换  结果：[1, 5, 3, 6, 0, 8, 9, 4, 7, 2]   TODO  再次减去2就是-1 了不能比较了
 *
 *   3. arr[4]=0 往前数2个元素，则是arr[2],  则是arr[2]=3与 arr[4]=0 进行插入比较, 3>0 , 则位置互换  结果：[1, 5, 0, 6, 3, 8, 9, 4, 7, 2]
 *   4. arr[2]=0 往前数2个元素，则是arr[0],  则是arr[0]=1与 arr[2]=0 进行插入比较, 1>0 , 则位置互换  结果：[0, 5, 1, 6, 3, 8, 9, 4, 7, 2]   TODO  再次减去2就是-2  不能比较了
 *
 *   5. arr[5]=8 往前数2个元素，则是arr[3],  则是arr[3]=6与 arr[5]=8 进行插入比较, 6<8 , 不进行交换  结果：[0, 5, 1, 6, 3, 8, 9, 4, 7, 2]
 *   6. arr[3]=6 往前数2个元素，则是arr[1],  则是arr[3]=6与 arr[1]=5 进行插入比较, 5<6 , 不进行交换  结果：[0, 5, 1, 6, 3, 8, 9, 4, 7, 2]   TODO  再次减去2就是-1  不能比较了

 *
 *   7. arr[6]=9 往前数2个元素，则是arr[4],  则是arr[4]=3与 arr[6]=9 进行插入比较, 3<9 , 不进行交换  结果：[0, 5, 1, 6, 3, 8, 9, 4, 7, 2]
 *   8. arr[4]=3 往前数2个元素，则是arr[2],  则是arr[2]=0与 arr[4]=3 进行插入比较, 0<3 , 不进行交换  结果：[0, 5, 1, 6, 3, 8, 9, 4, 7, 2]
 *   9. arr[2]=1 往前数2个元素，则是arr[0],  则是arr[0]=0与 arr[1]=1 进行插入比较, 0<1 , 不进行交换  结果：[0, 5, 1, 6, 3, 8, 9, 4, 7, 2]   TODO  再次减去2就是-2  不能比较了
 *
 *   10. arr[7]=4 往前数2个元素，则是arr[5],  则是arr[5]=8与 arr[7]=4 进行插入比较, 8>4 , 不进行交换  结果：[0, 5, 1, 6, 3, 4, 9, 8, 7, 2]
 *   11. arr[5]=4 往前数2个元素，则是arr[3],  则是arr[3]=6与 arr[5]=4 进行插入比较, 0<3 , 不进行交换  结果：[0, 5, 1, 6, 3, 8, 9, 4, 7, 2]
 *   12. arr[2]=1 往前数2个元素，则是arr[0],  则是arr[0]=0与 arr[1]=1 进行插入比较, 0<1 , 不进行交换  结果：[0, 5, 1, 6, 3, 8, 9, 4, 7, 2]   TODO  再次减去2就是-2  不能比较了
 *
 *
 * 按照以上的规律，进行依次操作
 *
 *
 *
 *
 */

public class ShellSortDemo {

    public static void main(String[] args) {

        int[] arr = new int[]{8, 9, 1, 7, 2,
                3, 5, 4, 6, 0};

       // 进行拆解处理
        System.out.println("开始第一轮的处理");
        step1(arr);
        //[3, 5, 1, 6, 0, 8, 9, 7, 4, 2]
        System.out.println("开始第二轮的处理");
        step2(arr);
        System.out.println("开始第三轮的处理");
        step3(arr);
/*
        arr = new int[]{8, 9, 1, 7, 2,
                3, 5, 4, 6, 0};
        //交换赋值法
        shellSort(arr);*/
/*
        arr = new int[]{8, 9, 1, 7, 2,
                3, 5, 4, 6, 0};

        arr = new int[]{10, 9, 20, 30, 5, 1, -1};
        shellSort2(arr);*/
    }


    /**
     * 这里使用的交换赋值的方法
     *
     * @param arr
     */
    public static void shellSort(int[] arr) {

        for (int gap = arr.length / 2; gap > 0; gap = gap / 2) {
            for (int i = gap; i < arr.length; i++) {
                int temp = arr[i];
                for (int j = i - gap; j >= 0; j = j - gap) {
                    if (temp < arr[j]) {
                        //temp 要将大于的值进行后移
                        arr[j + gap] = arr[j];
                        //当前的最小值
                        arr[j] = temp;
                        //更新最小值
                        temp = arr[j];
                    }
                }
            }
            System.out.println(Arrays.toString(arr));
        }
    }

    /**
     * 位移法 ，效率比交换赋值法更高， 交换赋值不算希尔排序，主要是方便理解的处理。
     *
     * @param arr
     */
    public static void shellSort2(int[] arr) {
        int minIndex = 0;
        int temp = 0;
        for (int gap = arr.length / 2; gap > 0; gap = gap / 2) {
            for (int i = gap; i < arr.length; i++) {
                temp = arr[i]; //初始值 arr[5]=3;
                minIndex = i; //初始值是5，
                while (minIndex >= gap && temp < arr[minIndex - gap]) { //初始值  arr[5 - 5]=8    3<8
                    arr[minIndex] = arr[minIndex - gap];  //初始值 arr[5]= arr[5-5]    arr[5]=arr[5-5]   即 arr[5]=8  这里是先把大的值赋值
                    minIndex = minIndex - gap;  //记录最小值的位置   minIndex=5-5=0; 最小值的位置0，
                }
                if (minIndex != i) {
                    arr[minIndex] = temp; // 针对这一轮的比较，最小值进行赋值
                }
                System.out.println(Arrays.toString(arr));
            }
        }
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
                if (temp < arr[j]) {
                    //temp 要将大于的值进行后移
                    arr[j + 5] = arr[j];
                    //当前的最小值
                    arr[j] = temp;
                    //更新最小值
                    temp = arr[j];
                }
                System.out.println(Arrays.toString(arr));

            }
        }
    }


    public static void step2(int[] arr) {

        for (int i = 2; i < arr.length; i++) {
            int temp = arr[i];
            for (int j = i - 2; j >= 0; j = j - 2) {
                if (temp < arr[j]) {
                    arr[j + 2] = arr[j];
                    arr[j] = temp;
                    temp = arr[j];
                }
                System.out.println(Arrays.toString(arr));

            }
        }
    }


    public static void step3(int[] arr) {

        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            for (int j = i - 1; j >= 0; j = j - 1) {
                if (temp < arr[j]) {
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                    temp = arr[j];
                }
                System.out.println(Arrays.toString(arr));
            }
        }
    }

}
