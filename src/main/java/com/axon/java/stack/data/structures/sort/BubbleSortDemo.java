package com.axon.java.stack.data.structures.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 冒泡排序算法
 * <p>
 * 思路：
 * 1. 循环遍历每一个元素，
 * 2. 判断元素与下一个相邻元素的大小
 * 3. 如果当前元素大于下个相邻元素，则互换位置。
 * 4. 直到最后将最大的元素放到最后一个位置
 * 5. 依次重试上面的步骤
 */
public class BubbleSortDemo {

    public static void main(String[] args) {

        int[] arrs = new int[]{20, -1, 30, 8, 0, 13, 55, 3};
        int[] ints = generateRandomArray(80000);

        long beginTime = System.currentTimeMillis();
        System.out.println("开始时间" + beginTime);
        // 冒泡排序
        ints = getBubbleSortV3(arrs);
        System.out.println(Arrays.toString(ints));
        long endTime = System.currentTimeMillis();
        System.out.println("结束时间" + endTime);
        System.out.println("总共耗时" + (endTime - beginTime));


    }

    /**
     * 第三个版本
     *
     * @param arrs
     * @return
     */
    public static int[] getBubbleSortV3(int[] arrs) {

        for (int i = 0; i < arrs.length - 1; i++) {
            boolean flag = false;
            for (int j = 0; j < arrs.length - 1 - i; j++) {

                if (arrs[j] > arrs[j + 1]) {
                    int temp = arrs[j];
                    arrs[j] = arrs[j + 1];
                    arrs[j + 1] = temp;
                    flag = true;
                }
            }

            if (!flag) {
                break;
            }
        }

        return arrs;
    }

    /**
     * 这个代码中的排序逻辑不是标准的冒泡排序，而且存在效率低下的问题。它实际上更类似于选择排序。以下是代码中几个需要注意的问题：
     * <p>
     * 1. 不是标准的冒泡排序算法
     * <p>
     * 在冒泡排序中，每次迭代都会将最大的元素”冒泡”到数组的末尾，而你的算法没有遵循这一点。
     * <p>
     * 冒泡排序的标准实现：
     * <p>
     * •	冒泡排序的核心思想是在每一轮比较中，两两比较相邻元素。如果前一个元素大于后一个元素，则交换位置，这样经过一轮之后，最大的元素就被移到了数组的末尾。
     * •	而你代码中的实现，for (int j = i + 1; j < arr.length; j++) 的逻辑更像是选择排序，直接找到剩余部分中的最小值并与当前元素交换。
     * <p>
     * 2. 效率问题
     * <p>
     * •	外层循环: 你的外层循环从 i = 0 到 arr.length，没有减少比较次数。在冒泡排序中，每一轮完成后，最大的元素已经就位，所以可以减少下一轮的比较次数。
     * •	内部比较: 冒泡排序应该比较相邻的元素，而你的实现是比较 arr[i] 和 arr[j]，这使得算法复杂度更高，交换的位置不正确。
     *
     * @param arr
     * @return 这是有一个错误的写法
     */
    private static int[] getBubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                //如果arr[i]的位置大于 arr[j]
                // 即当前位置大于下一位，则与下一位互换位置
                if (arr[i] > arr[j]) {
                    /// 临时变量保存arr[j]的值
                    int temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                    // 错误的写法
                    // arr[i]=arr[j] 因为j的值在上一步已经修改了。
                }
            }
        }
        return arr;
    }


    /**
     * 优化后的冒泡排序
     *
     * @param arr
     * @return
     */
    public static int[] getBubbleSortV2(int[] arr) {
        // 标准的冒泡排序
        for (int i = 0; i < arr.length - 1; i++) {
            // 提前退出标志
            boolean swapped = false;
            // 内部循环，每次比较相邻两个元素
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // 交换相邻元素
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;  // 发生交换
                }
            }
            // 如果没有交换，说明已经有序，可以提前退出
            if (!swapped) {
                break;
            }
        }
        return arr;
    }

    /**
     * 生成指定大小的随机整数数组
     *
     * @param size 数组大小
     * @return 随机整数数组
     */
    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            // 生成范围在 -100000 到 100000 之间的随机数
            arr[i] = random.nextInt(size) - 500000;
        }
        return arr;
    }
}
