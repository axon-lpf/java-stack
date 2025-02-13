package com.axon.java.stack.algorithms.sort.dubblesort;


import java.util.Arrays;
import java.util.Random;

/**
 * 初始化，数组 [10,9,20,30,5,1,-1] ，针对这个数组进行冒泡排序
 * 冒泡排序，每次比较一轮，将每一轮最大的排序到最后，
 * <p>排序逻辑 第一轮
 * <p>
 * 1. 10和9 比较， 10大于9 ， 则9和10 交换位置    结果： [9,10,20,30,5,1,-1]
 * <p>
 * 2.10和20 比较， 10小于20， 不进行交换位置      结果： [9,10,20,30,5,1,-1]
 * <p>
 * 3.20和30 比较， 20小于30， 不进行交换位置      结果： [9,10,20,30,5,1,-1]
 * <p>
 * 4.30和5 比较，  30大于5，  则30和5交换位置     结果： [9,10,20,5,30,1,-1]
 * <p>
 * 5.30和1 比较   30大于1，   则30和1交换位置     结果：[9,10,20,5,1,30,-1]
 * <p>
 * 6.30和-1比较   30大于-1，  则30和-1交换位置，  结果：[9,10,20,5,1,-1,30]
 * <p>
 * <p>
 * 排序逻辑 第二轮， 第二轮基于第一轮的结果去处理 [9,10,20,5,1,-1,30]
 * <p>
 * 1.9和10比较，  9小于10， 不进行交换位置， 结果： [9,10,20,5,1,-1,30]
 * <p>
 * 2.10和20比较， 10小于20， 不进行交换位置， 结果： [9,10,20,5,1,-1,30]
 * <p>
 * 3.20和5比较， 20大于5，  则20和5交换位置， 结果： [9,10,5,20,1,-1,30]
 * <p>
 * 4.20和1比较， 20大于1，  则20和1交换位置， 结果： [9,10,5,1,20,-1,30]
 * <p>
 * 5.20和-1比较， 20大于-1，  则20和-11交换位置， 结果： [9,10,5,1,-1,20,30]   ，TODO  注意；20和30不用比较了， 因为总共7个元素， 第一轮已经将最大元素放到最后一位了，则没有后续步骤了
 * <p>
 * <p>
 * 排序逻辑 第三轮， 第三轮基于第二轮的结果去处理  [9,10,5,1,-1,20,30]
 * <p>
 * 1.9和10比较，  9小于10， 不进行交换位置， 结果： [9,10,5,1,-1,20,30]
 * <p>
 * 2.10和5比较， 10大于5， 则10和5交换位置， 结果：  [9,5,10,1,-1,20,30]
 * <p>
 * 3.10和1比较， 10大于1，  则10和1交换位置， 结果：[9,5,1,10,-1,20,30]
 * <p>
 * 4.10和-1比较， 10大于-1，  则10和-1交换位置， 结果： [9,5,1,-1,10,20,30]  TODO  注意；10和20不用比较了， 因为总共7个元素， 第二轮结束，最后两个元素都是有序的，则没有后续步骤了
 * <p>
 * 总结规律，依次类推。
 * <p>
 * [9, 10, 20, 5, 1, -1, 30]
 * [9, 10, 5, 1, -1, 20, 30]
 * [9, 5, 1, -1, 10, 20, 30]
 * [5, 1, -1, 9, 10, 20, 30]
 * [1, -1, 5, 9, 10, 20, 30]
 * [-1, 1, 5, 9, 10, 20, 30]
 */
public class BubbleSortDemo {

    public static void main(String[] args) {
        int[] arrs = new int[]{20, -1, 30, 8, 0, 13, 55, 3};
        arrs = generateRandomArray(100000);

        long beginTime = System.currentTimeMillis();
        System.out.println("开始时间" + beginTime);
        // 冒泡排序
        bubbleSortToArray(arrs);
        System.out.println(Arrays.toString(arrs));
        long endTime = System.currentTimeMillis();
        System.out.println("结束时间" + endTime);
        System.out.println("总共耗时" + (endTime - beginTime));
    }


    /**
     * 冒泡排序
     *
     * @param initArray
     */
    public static void bubbleSortToArray(int[] initArray) {
        //结束标志
        boolean flag = false;
        for (int i = 0; i < initArray.length - 1; i++) {

            for (int j = 0; j < initArray.length - 1 - i; j++) {

                if (initArray[j] > initArray[j + 1]) {
                    int temp = initArray[j];
                    initArray[j] = initArray[j + 1];
                    initArray[j + 1] = temp;
                    flag = true;
                }
            }
            if (flag) {
                //如果为true，说明在本轮中交换过位置，需要继续比较处理
                flag = false;
            } else {
                // 否则，没有交换过位置，说明都是有序的，则结束。
                break;
            }
           // System.out.println(Arrays.toString(initArray));
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
