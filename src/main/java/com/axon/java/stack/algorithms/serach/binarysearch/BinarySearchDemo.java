package com.axon.java.stack.algorithms.serach.binarysearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 二分查找算法
 * 二分查找法的前提是数组必须是有序的
 *
 * 使用二分查找算法在一个有序数组中查找元素时，逻辑是通过反复缩小查找范围来找到目标值。我们以查找 200 为例，逐步说明查找过程。
 *
 * 数组为 {10, 20, 33, 55, 99, 99, 200, 300}，查找目标值是 200。
 *
 * 步骤解析
 *
 * 	1.	初始化左右边界：
 * 	•	左边界 (left) 为 0（数组的第一个索引）。
 * 	•	右边界 (right) 为 7（数组的最后一个索引）。
 * 	2.	计算中间索引：
 * 	•	计算公式为 mid = (left + right) / 2，结果向下取整。
 * 	3.	逐步查找：
 * 	•	第一次查找：
 * 	•	left = 0，right = 7
 * 	•	计算 mid = (0 + 7) / 2 = 3
 * 	•	中间值 arr[3] = 55
 * 	•	比较 200 和 55：
 * 	•	因为 200 > 55，所以目标在 55 的右侧。
 * 	•	更新左边界 left = mid + 1 = 4
 * 	•	第二次查找：
 * 	•	left = 4，right = 7
 * 	•	计算 mid = (4 + 7) / 2 = 5
 * 	•	中间值 arr[5] = 99
 * 	•	比较 200 和 99：
 * 	•	因为 200 > 99，所以目标在 99 的右侧。
 * 	•	更新左边界 left = mid + 1 = 6
 * 	•	第三次查找：
 * 	•	left = 6，right = 7
 * 	•	计算 mid = (6 + 7) / 2 = 6
 * 	•	中间值 arr[6] = 200
 * 	•	比较 200 和 200：
 * 	•	因为 200 == 200，找到目标值 200，返回索引 6。
 *
 * 逻辑总结
 *
 * 二分查找的核心在于每次缩小查找范围，只需对比一次中间值并决定移动左或右边界，从而逐步锁定目标值的位置。这种方法的时间复杂度为 O(\log n)，适用于有序数组的快速查找。、
 *
 *
 * 在二分查找的逻辑中，left > right 表示没有找到目标值的原因如下：
 *
 * 	1.	查找范围缩小：
 * 	•	每次查找时，通过调整 left 或 right 来缩小查找范围。具体来说：
 * 	•	如果 searchKey 小于 mid 的值，则将 right 更新为 mid - 1，缩小到左半部分。
 * 	•	如果 searchKey 大于 mid 的值，则将 left 更新为 mid + 1，缩小到右半部分。
 * 	•	不断缩小的查找范围最终会导致 left 和 right 的位置不断靠近。
 * 	2.	无解情况：
 * 	•	假设目标值 searchKey 不在数组中，那么缩小范围的过程会继续，直到 left 超过 right。
 * 	•	当 left > right 时，查找范围为空，说明数组中已没有可以检查的值。这表明目标值不存在，因此可以返回 -1 表示未找到。
 * 	3.	递归和终止条件：
 * 	•	递归实现时，if (left > right) 也充当了终止条件，确保查找结束。
 * 	•	达到 left > right 是二分查找中唯一可能不返回结果的情况，因此直接返回 -1 表示目标值不在数组中。
 *
 * 例子
 *
 * 比如，查找一个不在数组 {10, 20, 33, 55, 99, 99, 200, 300} 中的值 500：
 *
 * 	•	初始范围：left = 0，right = 7
 * 	•	不断调整 left 和 right 后，最终会到达 left = 8，right = 7，这时 left > right，表示没有找到 500。
 *
 */
public class BinarySearchDemo {

    public static void main(String[] args) {

        int[] arr = {10, 20, 33, 55, 99, 99, 200, 300};
        int i = binarySearch(arr, 0, arr.length-1, 200);
        System.out.println("下标索引位置" + i);
        List<Integer> integers = binarySearchToList2(arr, 0, arr.length - 1, 99);
        System.out.println(Arrays.toString(integers.toArray()));
    }

    /**
     * 二分查找算法
     *
     * @param arr       原始数组
     * @param left      左边的起始位置
     * @param right     右边的结束位置
     * @param searchKey 查找的key值
     */
    public static int binarySearch(int[] arr, int left, int right, int searchKey) {
        //如果左边大于右边，则说明没有找到，结束
        if (left > right) {
            return -1;
        }
        //查找中间的索引
        int mid = (left + right) / 2;
        //中间值
        int midVal = arr[mid];
        //如果查找的值小于中间值， 则从左边开始查找
        if (searchKey < midVal) {
            return binarySearch(arr, left, mid - 1, searchKey);
        } else if (searchKey > midVal) {
            //如果大于中间值，则从右边开始查找
            return binarySearch(arr, mid + 1, right, searchKey);
        } else {
            return mid;
        }
    }


    /**
     * 查找出值相同的下标索引
     *
     * @param arr       原始数组
     * @param left      左边的起始位置
     * @param right     右边的结束位置
     * @param searchKey 查找的key值
     * @return List<Integer>
     */
    public static List<Integer> binarySearchToList(int[] arr, int left, int right, int searchKey) {
        //如果左边大于右边，则说明没有找到，结束
        if (left > right) {
            return new ArrayList<>();
        }
        //查找中间的索引
        int mid = (left + right) / 2;
        //中间值
        int midVal = arr[mid];
        //如果查找的值小于中间值， 则从左边开始查找
        if (searchKey < midVal) {
            return binarySearchToList(arr, left, mid - 1, searchKey);
        } else if (searchKey > midVal) {
            //如果大于中间值，则从右边开始查找
            return binarySearchToList(arr, mid + 1, right, searchKey);
        } else {
            List<Integer> result = new ArrayList<>();
            //如果等于了， 则从这个值向左边查找，
            while (mid > 0 && arr[mid] == midVal) {
                result.add(mid);
                mid -= 1;
            }
            while (mid < right && arr[mid] == midVal) {
                result.add(mid);
                mid += 1;
            }
            return result;
        }
    }


    /**
     *  这里是正确的版本
     * @param arr
     * @param left
     * @param right
     * @param searchKey
     * @return
     */
    public static List<Integer> binarySearchToList2(int[] arr, int left, int right, int searchKey) {
        List<Integer> result = new ArrayList<>();

        if (left > right) {
            return result;
        }

        int mid = (left + right) / 2;
        int midVal = arr[mid];

        if (searchKey < midVal) {
            return binarySearchToList(arr, left, mid - 1, searchKey);
        } else if (searchKey > midVal) {
            return binarySearchToList(arr, mid + 1, right, searchKey);
        } else {
            // 向左侧找到所有相同的元素
            int temp = mid;
            while (temp >= left && arr[temp] == searchKey) {
                result.add(temp);
                temp--;
            }

            // 中间位置元素
            result.add(mid);

            // 向右侧找到所有相同的元素
            temp = mid + 1;
            while (temp <= right && arr[temp] == searchKey) {
                result.add(temp);
                temp++;
            }

            // 排序结果
            result.sort(Integer::compareTo);
            return result;
        }
    }

}
