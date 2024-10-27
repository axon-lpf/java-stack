package com.axon.java.stack.algorithms.sort.selectionsort;

import java.util.Arrays;
import java.util.Random;

/**
 * 选择排序算法
 * <p>
 * 初始化，数组 [10,9,20,30,5,1,-1] ，针对这个数组进行选择排序
 * <p>排序逻辑 第一轮, 基于初始化数组  [10,9,20,30,5,1,-1]  TODO  选择排序，找到最小值的索引去替换，
 * <p>
 * 1. 10和9 比较， 10大于9 ， 则9和10 交换位置    结果： [9,10,20,30,5,1,-1]  最小值索引值是0
 * <p>
 * 2. 9和20 比较， 9小于20， 不进行交换位置      结果： [9,10,20,30,5,1,-1]    最值小索引值是0
 * <p>
 * 3. 9和30 比较， 9小于30， 不进行交换位置      结果： [9,10,20,30,5,1,-1]    最值小索引值是0
 * <p>
 * 4. 9和5 比较，  9大于5，  则9和5交换位置     结果： [5,10,20,30,9,1,-1]     最小值索引值是4
 * <p>
 * 5.5和1 比较   5大于1，   则5和1交换位置     结果：[1,10,20,30,9,5,-1]       最小值的索引值是5
 * <p>
 * 6.1和-1比较   1大于-1，  则1和-1交换位置，  结果：[-1,10,20,30,9,5,1]       最小值的索引值是6
 * <p>
 * 最后是将6位置和0位置的value进行交换
 *
 * <p>
 * <p>
 * 排序逻辑 第二轮， 第二轮基于第一轮的结果去处理 第一轮结果：[-1,10,20,30,9,5,1]     TODO 注意，第一轮比较，已经将最小的元素放到首位，那后面则依次递增， 则本次则从下标为1的元素开始，则是10的位置
 * <p>
 * 1.10和20比较，  10小于20， 不进行交换位置， 结果： [-1,10,20,30,9,5,1]
 * <p>
 * 2.10和30比较， 10小于30， 不进行交换位置， 结果： [-1,10,20,30,9,5,1]
 * <p>
 * 3.10和9比较， 10大于9，  则10和9交换位置， 结果： [-1,9,20,30,10,5,1]
 * <p>
 * 4.9和5比较， 9大于5，  则9和5交换位置， 结果： [-1,5,20,30,10,9,1]
 * <p>
 * 5.5和1比较， 5大于1，  则5和1交换位置， 结果： [-1,1,20,30,10,9,5]
 * <p>
 * <p>
 * 排序逻辑 第三轮， 第三轮基于第二轮的结果去处理  第二轮结果： [-1,1,20,30,10,9,5]   TODO 则本次则从下标为2的元素开始，则是20的位置
 * <p>
 * 1.20和30比较，  20小于30， 不进行交换位置， 结果： [-1,1,20,30,10,9,5]
 * <p>
 * 2.20和10比较， 20大于10， 则20和10交换位置， 结果： [-1,1,10,30,20,9,5]
 * <p>
 * 3.10和9比较， 10大于9，  则10和9交换位置， 结果： [-1,1,9,30,20,10,5]
 * <p>
 * 4.9和5比较， 9大于5，  则9和5交换位置， 结果： [-1,1,5,30,20,10,9]
 * <p>
 * <p>
 * 总结规律，依次类推。
 * <p>
 * [-1, 10, 20, 30, 9, 5, 1]
 * [-1, 1, 20, 30, 10, 9, 5]
 * [-1, 1, 5, 30, 20, 10, 9]
 * [-1, 1, 5, 9, 30, 20, 10]
 * [-1, 1, 5, 9, 10, 30, 20]
 * [-1, 1, 5, 9, 10, 20, 30]
 *
 * selectionSortToArray 错误版本和正确版本出性能差很多
 */
public class SelectionSortDemo {

    public static void main(String[] args) {

        int[] arrs = new int[]{10, 9, 20, 30, 5, 1, -1};
        arrs = generateRandomArray(100000);

        long beginTime = System.currentTimeMillis();
        System.out.println("开始时间" + beginTime);
        // 选择排序
        selectionSortToArray2(arrs);
        long endTime = System.currentTimeMillis();
        System.out.println("结束时间" + endTime);

        System.out.println(Arrays.toString(arrs));
        System.out.println("总共耗时" + (endTime - beginTime));

    }

    /**
     * 选择排序算法--- 这是错误版本的， 这里每次循环都交换位置
     *
     * @param initArray
     */
    public static void selectionSortToArray(int[] initArray) {

        for (int i = 0; i < initArray.length - 1; i++) {

            for (int j = i + 1; j < initArray.length; j++) {
                //如果i的位置大于j的位置的元素，则两个需要交换位置
                if (initArray[i] > initArray[j]) {
                    //临时j位置的元素值
                    int temp = initArray[j];
                    //将i位置的元素的值赋值给j
                    initArray[j] = initArray[i];
                    initArray[i] = temp;
                }
            }
            //System.out.println(Arrays.toString(initArray));
        }

    }


    /**
     * 选择排序算法- 正确版本的。
     *
     * @param initArray
     */
    public static void selectionSortToArray2(int[] initArray) {
        for (int i = 0; i < initArray.length - 1; i++) {
            // 假设第一个元素为最小值
            int minIndex = i;
            for (int j = i + 1; j < initArray.length; j++) {
                // 找到最小值的索引
                if (initArray[j] < initArray[minIndex]) {
                    minIndex = j;
                }
            }
            // 如果找到的最小值的索引不是当前索引，则进行交换
            if (minIndex != i) {
                int temp = initArray[i];
                initArray[i] = initArray[minIndex];
                initArray[minIndex] = temp;
            }

            // 输出当前状态
            //System.out.println("第 " + (i + 1) + " 轮结果: " + Arrays.toString(initArray));
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
