package com.axon.java.stack.algorithms.sort.radixsort;

import java.util.Arrays;

/**
 * 基数排序算法
 * <p>
 * 假设 arr[i] = 532，当 digit 分别为 0、1 和 2 时：
 * <p>
 * •	digit = 0：divisor = 1，arr[i] / 1 % 10 = 532 % 10 = 2，提取的是个位。
 * •	digit = 1：divisor = 10，arr[i] / 10 % 10 = 53 % 10 = 3，提取的是十位。
 * •	digit = 2：divisor = 100，arr[i] / 100 % 10 = 5 % 10 = 5，提取的是百位。
 * <p>
 * <p>
 * 对于数组 {53, 3, 542, 748, 14, 214}，使用基数排序的步骤将从个位开始排序，接着是十位、百位，逐步排列出最终的顺序。基数排序中，数据按每个位数（个位、十位、百位等）分别排序，直至最高位。我们将假设数据的最高位不超过百位来简化步骤。
 * <p>
 * 原始数组
 * <p>
 * {53, 3, 542, 748, 14, 214}
 * <p>
 * 步骤 1：个位排序
 * <p>
 * 首先，我们按照个位数将元素放入对应的桶中。
 * <p>
 * 1.	3：个位是 3 → 放入桶 3
 * 2.	14：个位是 4 → 放入桶 4
 * 3.	53：个位是 3 → 放入桶 3
 * 4.	214：个位是 4 → 放入桶 4
 * 5.	542：个位是 2 → 放入桶 2
 * 6.	748：个位是 8 → 放入桶 8
 * <p>
 * 将各个桶中的数据按顺序收集回数组，得到：
 * <p>
 * {542, 3, 53, 14, 214, 748}
 * <p>
 * 步骤 2：十位排序
 * <p>
 * 接下来，我们对十位上的数字进行排序。
 * <p>
 * 1.	542：十位是 4 → 放入桶 4
 * 2.	3：十位是 0 → 放入桶 0
 * 3.	53：十位是 5 → 放入桶 5
 * 4.	14：十位是 1 → 放入桶 1
 * 5.	214：十位是 1 → 放入桶 1
 * 6.	748：十位是 4 → 放入桶 4
 * <p>
 * 将各个桶中的数据按顺序收集回数组，得到：
 * <p>
 * {3, 14, 214, 542, 748, 53}
 * <p>
 * 步骤 3：百位排序
 * <p>
 * 现在我们对百位上的数字进行排序（注意 3、14、53 这些数字的百位视为 0）。
 * <p>
 * 1.	3：百位是 0 → 放入桶 0
 * 2.	14：百位是 0 → 放入桶 0
 * 3.	214：百位是 2 → 放入桶 2
 * 4.	542：百位是 5 → 放入桶 5
 * 5.	748：百位是 7 → 放入桶 7
 * 6.	53：百位是 0 → 放入桶 0
 * <p>
 * 将各个桶中的数据按顺序收集回数组，得到：
 * <p>
 * {3, 14, 53, 214, 542, 748}
 * <p>
 * 最终结果
 * <p>
 * 经过个位、十位和百位排序后，数组最终有序：{3, 14, 53, 214, 542, 748}。
 */
public class RadixSortDemo {


    public static void main(String[] args) {

        int[] arr = {53, 3, 542, 748, 14, 214};
/*
        step1(arr);
       System.out.println(Arrays.toString(arr));
        step2(arr);
        System.out.println(Arrays.toString(arr));

        step3(arr);
        System.out.println(Arrays.toString(arr));
*/

        radixSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 基数排序， 这里的基数排序法，没有对负数进行处理
     *
     * @param arr
     */
    public static void radixSort(int[] arr) {

        //总结三步规律， 产生以下代码
        //1.先求出最大的数
        int maxValue = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (maxValue < arr[i]) {
                maxValue = arr[i];
            }
        }
        // 获取最大的长度
        String maxLength = maxValue + "";

        for (int k = 0, n = 1; k < maxLength.length(); k++, n *= 10) {

            // 记录一个桶
            int[][] bucket = new int[10][arr.length];

            // 用于记录桶中存放的数量
            int[] bucketIndexCount = new int[10];

            //循环遍历
            for (int i = 0; i < arr.length; i++) {
                int bucketIndex = arr[i] / n % 10;

                if (bucketIndex >= 0) {
                    //取出当前桶中存的数量  bucketIndexCount[bucketIndex]++;
                    bucket[bucketIndex][bucketIndexCount[bucketIndex]++] = arr[i];
                }
            }

            int index = 0;
            // 然后取出桶中的数据，再次放入到原来的数组中去
            for (int i = 0; i < bucketIndexCount.length; i++) {

                for (int j = 0; j < bucketIndexCount[i]; j++) {
                    //再次放入原来的数组中
                    arr[index++] = bucket[i][j];
                }
                //重置桶中的计数器
                bucketIndexCount[i] = 0;
            }
        }


    }

    /**
     * 第一步， 先处理个位的数
     *
     * @param arr
     */
    public static void step1(int[] arr) {

        // 记录一个桶
        int[][] bucket = new int[10][arr.length];

        // 用于记录桶中存放的数量
        int[] bucketIndexCount = new int[10];

        //循环遍历
        for (int i = 0; i < arr.length; i++) {
            int bucketIndex = arr[i] / 1 % 10;

            if (bucketIndex >= 0) {
                //取出当前桶中存的数量  bucketIndexCount[bucketIndex]++;
                bucket[bucketIndex][bucketIndexCount[bucketIndex]++] = arr[i];
            }
        }

        int index = 0;
        // 然后取出桶中的数据，再次放入到原来的数组中去
        for (int i = 0; i < bucketIndexCount.length; i++) {

            for (int j = 0; j < bucketIndexCount[i]; j++) {
                //再次放入原来的数组中
                arr[index++] = bucket[i][j];
            }
            //重置桶中的计数器
            bucketIndexCount[i] = 0;
        }
    }


    /**
     * 第二步， 处理十位数
     *
     * @param arr
     */
    public static void step2(int[] arr) {

        // 记录一个桶
        int[][] bucket = new int[10][arr.length];

        // 用于记录桶中存放的数量
        int[] bucketIndexCount = new int[10];

        //循环遍历
        for (int i = 0; i < arr.length; i++) {
            int bucketIndex = arr[i] / 10 % 10;

            if (bucketIndex >= 0) {
                //取出当前桶中存的数量  bucketIndexCount[bucketIndex]++;
                bucket[bucketIndex][bucketIndexCount[bucketIndex]++] = arr[i];
            }
        }

        int index = 0;
        // 然后取出桶中的数据，再次放入到原来的数组中去
        for (int i = 0; i < bucketIndexCount.length; i++) {

            for (int j = 0; j < bucketIndexCount[i]; j++) {
                //再次放入原来的数组中
                arr[index++] = bucket[i][j];
            }
            //重置桶中的计数器
            bucketIndexCount[i] = 0;
        }
    }


    /**
     * 第二步， 处理百位数
     *
     * @param arr
     */
    public static void step3(int[] arr) {

        // 记录一个桶
        int[][] bucket = new int[10][arr.length];

        // 用于记录桶中存放的数量
        int[] bucketIndexCount = new int[10];

        //循环遍历
        for (int i = 0; i < arr.length; i++) {
            int bucketIndex = arr[i] / 100 % 10;  //例如  543    543/100%10=5  ， 214

            if (bucketIndex >= 0) {
                //取出当前桶中存的数量  bucketIndexCount[bucketIndex]++;
                bucket[bucketIndex][bucketIndexCount[bucketIndex]++] = arr[i];
            }
        }

        int index = 0;
        // 然后取出桶中的数据，再次放入到原来的数组中去
        for (int i = 0; i < bucketIndexCount.length; i++) {

            for (int j = 0; j < bucketIndexCount[i]; j++) {
                //再次放入原来的数组中
                arr[index++] = bucket[i][j];
            }
            //重置桶中的计数器
            bucketIndexCount[i] = 0;
        }
    }
}
