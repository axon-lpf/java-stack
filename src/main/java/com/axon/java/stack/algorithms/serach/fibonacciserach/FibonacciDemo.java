package com.axon.java.stack.algorithms.serach.fibonacciserach;

import java.util.Arrays;

/**
 * 斐波拉切查找算法
 * 假设数组为 {1, 8, 10, 89, 1000, 1234}，目标是查找 89 的位置
 *
 * 初始数组长度是 6，对应的斐波那契数 F(k) = 8（假设一个填充后的虚拟数组，方便符合斐波那契数列要求）。
 *
 * 步骤 1：初始划分
 *
 * 	1.	设置初始的 k = 6，因为我们选择 F(k) = 8 覆盖查找区间，实际数组范围为 0 到 5。
 * 	2.	根据 mid = left + F(k-1) - 1 = 0 + F(5) - 1 = 4，我们将 mid 设置在位置 4。
 *
 * 	原数组（填充虚拟值）：{1, 8, 10, 89, 1000, 1234, ?, ?}
 * 斐波那契分割数： F(6) = 8
 *
 * 初始划分：
 *       ┌────────────┬────────────┐
 *       │ 左半部分   │ 右半部分   │
 *       │ F(5) = 5  │ F(4) = 3   │
 *       └────────────┴────────────┘
 *          ^                     ^
 *         left                  right
 *                ↑
 *              mid=4
 *
 * 3.	此时 arr[mid] = 1000，89 < 1000，所以目标在左半部分。
 *
 * 步骤 2：缩小区间到左半部分
 *
 * 	1.	更新 k = k - 2 = 6 - 2 = 4，因为查找范围缩小为左半部分，新的长度为 F(4) = 3。
 * 	2.	计算新的 mid = left + F(k-1) - 1 = 0 + F(3) - 1 = 1。
 * 图示：
 *      缩小至左半部分：
 *       ┌──────┬────────┐
 *       │ 左半部分     │ 右半部分 │
 *       │ F(3) = 2    │ F(2) = 1 │
 *       └──────┴────────┘
 *         ^               ^
 *       left            right
 *            ↑
 *           mid=1
 * 	3.	此时 arr[mid] = 8，89 > 8，所以目标在右半部分。
 *
 * 步骤 3：缩小区间到右半部分
 *
 * 	1.	更新 k = k - 1 = 4 - 1 = 3，因为查找范围缩小为右半部分，新的长度为 F(3) = 2。
 * 	2.	更新 left = mid + 1 = 2，计算新的 mid = left + F(k-1) - 1 = 2 + F(2) - 1 = 2.
 * 图示：
 *      缩小至右半部分：
 *        ┌──────┬────────┐
 *        │左半部分      │右半部分│
 *        │ F(2) = 1    │ F(1) = 1 │
 *        └──────┴────────┘
 *         ^               ^
 *        left           right
 *             ↑
 *           mid=2
 *
 * 3.	此时 arr[mid] = 10，89 > 10，所以目标还在右半部分。
 *
 * 步骤 4：进一步缩小至右半部分
 *
 * 	1.	更新 k = k - 1 = 3 - 1 = 2，此时查找范围继续缩小。
 * 	2.	更新 left = mid + 1 = 3，计算新的 mid = left + F(k-1) - 1 = 3 + F(1) - 1 = 3。
 * 图示：
 *     进一步缩小至右半部分：
 *        ┌──────┬────────┐
 *        │左半部分      │右半部分│
 *        │ F(1) = 1    │ F(0) = 0 │
 *        └──────┴────────┘
 *         ^              ^
 *       left           right
 *             ↑
 *           mid=3
 *
 * 	3.	此时 arr[mid] = 89，刚好找到了目标值 89，索引为 3。
 *
 * 总结
 *
 * 每次选择 F(k-1) 和 F(k-2) 是为了根据斐波那契数列的特性将区间划分成符合比例的两部分。k 的更新方式（k - 2 或 k - 1）是根据当前区间落在左半部分或右半部分来确定，确保每次划分的区间仍然满足斐波那契的比例结构。
 */
public class FibonacciDemo {

    public static void main(String[] args) {

        int[] arr = {1, 8, 10, 89, 1000, 1234};
        int i = fibonacciRecursive(arr, 89);
        System.out.println(i);

    }


    public static int[] fibonacci(int size) {

        int[] fibArray = new int[size];
        fibArray[0] = 0;
        fibArray[1] = 1;
        for (int i = 2; i < size; i++) {
            fibArray[i] = fibArray[i - 1] + fibArray[i - 2];
        }
        return fibArray;
    }


    public static int fibonacciRecursive(int[] arr, int key) {

        int left = 0;
        int right = arr.length - 1;
        int k = 1;
        int mid = 0;
        int[] fib = fibonacci(20);

        //开始寻找k的位置， k对应的值，应该要大于或者等于right
        while (right > fib[k - 1]) {
            k++;
        }

        //补充填0
        int[] temp = Arrays.copyOf(arr, fib[k]);

        // 将临时数组的多余的给填充成原始数组的最后一位
        for (int i = right + 1; i < temp.length; i++) {
            temp[i] = arr[right];
        }

        while (left <= right) {
            mid = left + fib[k - 1] - 1;  //TODO 为什么是这个公式？  f[k]=f[k-1]+f[k-2]   数组的总长度  arr.length=f(k)   取下标位置 则是  arr.length-1=f(k)-1，  按照斐波拉契的规则， 上一个数与下一个数的比例间距差不多是0.61左右。
            //TODO  那这里 f[k-1] 类似接近于二分查找法的 arr.length/2   所以 mid=left+fib[k-1]-1;
            if (key < temp[mid]) {  // 如果当前的key值小于中间值， 则向继续向左边查找
                right = mid - 1;
                k--;   //由于 F[k]=f(k-1)+k(k-2)   f(k-1) 对应的是左边的索引范围，  f(k-2)对应的是右边的范围  所以这里是-1
            } else if (key > temp[mid]) {  // 如果当前的值大于中间的值， 则继续向右边查找
                left = mid + 1;
                k = k - 2;  // 由于 F[k]=f(k-1)+k(k-2)   f(k-1) 对应的是左边的索引范围，  f(k-2)对应的是右边的范围  所以这里是-2
            } else {
                if (mid <= right) {
                    return mid;
                } else {
                    return right;
                }
            }

        }
        return -1;
    }
}
