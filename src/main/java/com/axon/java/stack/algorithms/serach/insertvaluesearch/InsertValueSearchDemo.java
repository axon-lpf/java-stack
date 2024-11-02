package com.axon.java.stack.algorithms.serach.insertvaluesearch;

import java.util.ArrayList;
import java.util.List;

/**
 * 差值查找算法，是基于二分查找算法的优化。
 * 差值查找算法公式推导：
 * 插值查找算法是一种基于二分查找算法改进的查找算法，专门设计用于元素分布较为均匀的有序数组。在数组分布均匀的情况下，它可以提供比二分查找更高的效率。插值查找通过一个比例公式估算目标值所在的索引位置，使其更加接近查找目标的位置。
 * <p>
 * 插值查找算法的公式推导
 * <p>
 * 插值查找的核心公式为：
 * <p>
 * mid=left+(searchKey-arr[left])/(arr[right]-arr[left])*(right-left)
 * <p>
 * <p>
 * 这个公式的目的是估算目标值 searchKey 在数组中的位置。推导思路如下：
 * <p>
 * 1.	确定查找区间：
 * •	在数组中查找范围从 left 到 right。
 * •	arr[left] 为左边界的值，arr[right] 为右边界的值。
 * 2.	比例假设：
 * •	假设查找目标值 searchKey 在数组中均匀分布，则可以根据比例关系来确定 searchKey 可能位于的位置。
 * •	假设 searchKey 相对于整个区间 (arr[left], arr[right]) 的位置可以用比例关系来近似表达。我们设：
 * <p>
 * (mid-left)/(right-left) 约= (searchKey-arr[left])/(arr[right]/arr[left])
 * <p>
 * 其中：
 * •	(mid-left)/(right-left) 表示 mid 在整个区间位置的比例。
 * •	(searchKey-arr[left]) /(arr[right]-arr[left])表示 searchKey 在值范围内的比例。
 * 3.	计算 mid 的位置：
 * •	由上式可以推导出 mid 的值：
 * <p>
 * mid=left+(searchKey-arr[left])/(arr[right]-arr[left])*(right-left)
 * <p>
 * •	该公式通过插值来确定 mid 的位置，使其更接近目标值 searchKey 的预期位置，从而减少不必要的比较次数。
 * <p>
 * 插值查找算法的局限性
 * <p>
 * •	分布均匀的情况下，插值查找性能较好。如果数据不均匀（如有大量重复元素），插值查找的性能优势会降低，甚至可能退化到线性查找。
 * •	边界检查：在实现插值查找时需要注意避免除零错误（arr[right] - arr[left] == 0）。
 * <p>
 * 插值查找算法的复杂度
 * <p>
 * •	时间复杂度：在理想的均匀分布下，时间复杂度为 O(log(log n))，比二分查找更高效；但在不均匀分布的情况下，时间复杂度会接近 O(n)。
 * •	空间复杂度：通常为 O(1)，与二分查找类似。
 * <p>
 * 插值查找更适合分布均匀的大规模数据集，利用比例公式可以大幅减少查找步骤。
 */
public class InsertValueSearchDemo {

    public static void main(String[] args) {

        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i + 1;
        }
        int i = insertValueSearch(arr, 0, arr.length - 1, 50);
        System.out.println("插值查找当前索引的值是" + i);

        int i1 = binarySearch(arr, 0, arr.length - 1, 50);
        System.out.println("二分查找当前索引的值是" + i1);


    }

    /**
     * @param arr
     * @param left
     * @param right
     * @param findValue
     * @return
     */
    public static int insertValueSearch(int[] arr, int left, int right, int findValue) {
        System.out.println("插值查找算法");

        //如果查找左边的索引大于右边的，则说明没有找到
        //如果查找的值仍然小于第一个值，则说明没有找到
        //如果查找的值仍然大于最后一个值，则说明没有找到
        if (left > right || findValue < arr[0] || findValue > arr[right]) {
            return -1;
        }
        // TODO 这里要考虑除以0的情况， 会导致异常
        //根据公式获得 mid
        int mid = left + (findValue - arr[left]) / (arr[right] - arr[left]) * (right - left);

        //先去找左边
        if (findValue < arr[mid]) {
            return insertValueSearch(arr, left, mid - 1, findValue);
        }
        //再次向右边查找
        else if (findValue > arr[mid]) {
            return insertValueSearch(arr, mid + 1, right, findValue);
        } else {
            return mid;
        }
    }


    /**
     * 优化后的版本
     *
     * @param arr
     * @param left
     * @param right
     * @param findValue
     * @return
     */
    public static int insertValueSearch2(int[] arr, int left, int right, int findValue) {
        System.out.println("插值查找算法");

        // 边界条件检查
        if (left > right || findValue < arr[0] || findValue > arr[right]) {
            return -1;
        }

        // 防止除零错误
        if (arr[left] == arr[right]) {
            if (arr[left] == findValue) {
                return left; // 如果左值等于目标值，则返回当前索引
            } else {
                return -1; // 未找到
            }
        }

        // 根据公式获得 mid
        int mid = left + (findValue - arr[left]) / (arr[right] - arr[left]) * (right - left);

        // 边界检查，防止数组越界
        if (mid < left || mid > right) {
            return -1;
        }

        // 递归查找
        if (findValue < arr[mid]) {
            return insertValueSearch2(arr, left, mid - 1, findValue);
        } else if (findValue > arr[mid]) {
            return insertValueSearch2(arr, mid + 1, right, findValue);
        } else {
            return mid;
        }
    }


    /**
     * 二分查找算法
     *
     * @param arr
     * @param left
     * @param right
     * @param searchKey
     * @return
     */
    public static int binarySearch(int[] arr, int left, int right, int searchKey) {
        System.out.println("二分查找算法");
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

}
