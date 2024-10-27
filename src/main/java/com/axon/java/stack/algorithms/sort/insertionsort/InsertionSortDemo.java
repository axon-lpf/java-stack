package com.axon.java.stack.algorithms.sort.insertionsort;

import java.util.Arrays;
import java.util.Random;

/**
 * 插入排序算法
 * 初始化，数组 [10,9,20,30,5,1,-1] ，针对这个数组进行插入排序， TODO 注意插入排序，是基于数组的的第二位开始，即下标为1的位置，value值为9的元素
 *
 * <p>排序逻辑 第一轮, 基于初始化数组  [10,9,20,30,5,1,-1]
 * 1. 9和10 比较， 9小于10 ， 则9和10 交换位置    结果： [9,10,20,30,5,1,-1]
 *
 * <p>第二轮
 * 1. 20和10 比较， 20大于10， 不进行交换位置      结果： [9,10,20,30,5,1,-1]
 *
 * <p>第三轮
 * 1. 30和20 比较， 30大于20，  不进行交换位置     结果： [9,10,20,30,5,1,-1]
 *
 * <p>第四轮
 * 1. 5和30比较   5小于30， 5和30交换位置，  结果：[9,10,20,5,30,1,-1]
 * <p>
 * 2. 5和20比较   5小于20， 5和20交换位置，  结果：[9,10,5,20,30,1,-1]
 * <p>
 * 3. 5和10比较   5小于10， 5和10交换位置，  结果：[9,5,10,20,30,1,-1]
 * <p>
 * 4. 5和9比较   5小于9， 5和9交换位置，  结果：[5,9,10,20,30,1,-1]
 *
 * <p>第五轮
 * <p>
 * 1. 1和30比较   1小于30， 1和30交换位置，  结果：[5,9,10,20,1,30,-1]
 * <p>
 * 2. 1和20比较   1小于20， 1和20交换位置，  结果：[5,9,10,1,20,30,-1]
 * <p>
 * 3. 1和10比较   1小于10， 1和10交换位置，  结果：[5,9,1,10,20,30,-1]
 * <p>
 * 4. 1和9比较   1小于9， 1和9交换位置，  结果：[5,1,9,10,20,30,-1]
 * <p>
 * 5. 1和5比较   1小于5， 1和5交换位置，  结果：[1,5,9,10,20,30,-1]
 * <p>
 * 总结规律，依次类推。
 *
 * [9, 10, 20, 30, 5, 1, -1]
 * [9, 10, 20, 30, 5, 1, -1]
 * [9, 10, 20, 30, 5, 1, -1]
 * [5, 9, 10, 20, 30, 1, -1]
 * [1, 5, 9, 10, 20, 30, -1]
 * [-1, 1, 5, 9, 10, 20, 30]
 *
 *
 */
public class InsertionSortDemo {

    public static void main(String[] args) {

        int[] arrs = new int[]{10, 9, 20, 30, 5, 1, -1};
        arrs = generateRandomArray(100000);

        long beginTime = System.currentTimeMillis();
        System.out.println("开始时间" + beginTime);
        // 选择排序
        insertionSort2(arrs);
        long endTime = System.currentTimeMillis();
        System.out.println("结束时间" + endTime);

        System.out.println(Arrays.toString(arrs));
        System.out.println("总共耗时" + (endTime - beginTime));
    }

    public static void insertionSort(int[] initArray) {
        //从第一位开始，循环遍历去比较
        for (int i = 1; i < initArray.length; i++) {
            //记录当前开始比较的位置, 需要与上一个元素去比较
            int preIndex = i - 1;
            //把第i位的元素保存到临时变量中，不会因为比较，元素后移导致丢失。
            int currentValue = initArray[i];
            //如果当前的值小于上一个元素的值，则交换位置
            while (preIndex >=0 && currentValue < initArray[preIndex]) {
                //将当前的元素赋值往后移动一位
                initArray[preIndex + 1] = initArray[preIndex];
                preIndex--;
            }
            //TODO 核心代码块。
            initArray[preIndex + 1] = currentValue;
            //System.out.println(Arrays.toString(initArray));

        }
    }

    /**
     * 第二种版本
     *
     * @param initArray
     */
    public static void insertionSort2(int[] initArray) {
        // 从第二个元素开始遍历（下标为1）
        for (int i = 1; i < initArray.length; i++) {
            int currentValue = initArray[i];
            int preIndex = i - 1;

            // 将大于 currentValue 的元素向右移动
            while (preIndex >= 0 && initArray[preIndex] > currentValue) {
                initArray[preIndex + 1] = initArray[preIndex]; // 右移
                preIndex--; // 移动到前一个元素
            }
            // 插入当前元素
            initArray[preIndex + 1] = currentValue;

            // 输出当前状态（可选）
           // System.out.println("当前状态: " + Arrays.toString(initArray));
        }
    }

    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            // 生成范围在 -100000 到 100000 之间的随机数
            arr[i] = random.nextInt(size) - 50000;
        }
        return arr;
    }
}
