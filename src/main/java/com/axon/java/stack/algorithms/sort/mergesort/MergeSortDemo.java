package com.axon.java.stack.algorithms.sort.mergesort;

import java.util.Arrays;

/**
 * 归并排序算法
 * 整体思路，如下：
 * 初始输入：int[] arr = {8, 5, 7, 1, 3, 6, 2, 0, 234};
 * 归并排序首先将数组从中间分成两个子数组，并对每个子数组递归地进行拆分，直到每个子数组的长度为1，然后合并排序。
 * <p>
 * 步骤 1：递归拆分数组
 * <p>
 * 1.	初始数组 [8, 5, 7, 1, 3, 6, 2, 0, 234]，left = 0, right = 8
 * •	中点 mid = (0 + 8) / 2 = 4
 * •	拆解成两部分 [8, 5, 7, 1, 3] 和 [6, 2, 0, 234]
 * 2.	拆解左边部分 [8, 5, 7, 1, 3]
 * •	左区间 left = 0, right = 4, 中点 mid = (0 + 4) / 2 = 2
 * •	拆解成 [8, 5, 7] 和 [1, 3]
 * 3.	拆解 [8, 5, 7]
 * •	左区间 left = 0, right = 2, 中点 mid = (0 + 2) / 2 = 1
 * •	拆解成 [8, 5] 和 [7]
 * 4.	拆解 [8, 5]
 * •	左区间 left = 0, right = 1, 中点 mid = (0 + 1) / 2 = 0
 * •	拆解成 [8] 和 [5]，拆解结束
 * 5.	拆解右边部分 [1, 3]
 * •	左区间 left = 3, right = 4, 中点 mid = (3 + 4) / 2 = 3
 * •	拆解成 [1] 和 [3]，拆解结束
 * 6.	拆解右边部分 [6, 2, 0, 234]
 * •	右区间 left = 5, right = 8, 中点 mid = (5 + 8) / 2 = 6
 * •	拆解成 [6, 2] 和 [0, 234]
 * 7.	拆解 [6, 2]
 * •	左区间 left = 5, right = 6, 中点 mid = (5 + 6) / 2 = 5
 * •	拆解成 [6] 和 [2]
 * 8.	拆解 [0, 234]
 * •	左区间 left = 7, right = 8, 中点 mid = (7 + 8) / 2 = 7
 * •	拆解成 [0] 和 [234]
 * <p>
 * 到这一步，数组被拆解到每个子数组都只有一个元素，接下来开始进行合并和排序。
 * <p>
 * 步骤 2：合并排序
 * <p>
 * 从最小的子数组开始合并，每一步都按从小到大的顺序将两个子数组合并。
 * <p>
 * 1.	合并 [8] 和 [5]
 * •	排序后得到 [5, 8]
 * 2.	合并 [5, 8] 和 [7]
 * •	排序后得到 [5, 7, 8]
 * 3.	合并 [1] 和 [3]
 * •	排序后得到 [1, 3]
 * 4.	合并 [5, 7, 8] 和 [1, 3]
 * •	排序后得到 [1, 3, 5, 7, 8]
 * 5.	合并 [6] 和 [2]
 * •	排序后得到 [2, 6]
 * 6.	合并 [0] 和 [234]
 * •	排序后得到 [0, 234]
 * 7.	合并 [2, 6] 和 [0, 234]
 * •	排序后得到 [0, 2, 6, 234]
 * 8.	合并 [1, 3, 5, 7, 8] 和 [0, 2, 6, 234]
 * •	按从小到大顺序合并所有元素，最终得到 [0, 1, 2, 3, 5, 6, 7, 8, 234]
 * <p>
 * 最终结果
 * <p>
 * 合并完成后，数组按升序排列为 [0, 1, 2, 3, 5, 6, 7, 8, 234]。
 */
public class MergeSortDemo {

    public static void main(String[] args) {

        int[] arr = {8, 5, 7, 1, 3, 6, 2, 0, 234};
        int[] temp = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, temp);
        System.out.println(Arrays.toString(arr));
    }


    /**
     * 此方法是递归拆解
     *
     * @param arr   原始数组
     * @param left  左边开始的位置
     * @param right 右边的开始位置
     * @param temp  临时数组
     */
    public static void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            System.out.println("拆解数组left=" + left + ", right=" + right + ", arr=" + Arrays.toString(Arrays.copyOfRange(arr, left, right + 1)));
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid, temp);
            mergeSort(arr, mid + 1, right, temp);
            sort(arr, left, right, temp);
        }

    }


    /**
     * 排序
     *
     * @param arr   原始数组
     * @param left  左边索引，
     * @param right 右边索引
     * @param temp  临时数组
     */
    public static void sort(int[] arr, int left, int right, int[] temp) {
        //System.out.println("本次合并数组left=" + left + ", right=" + right + ", temp=" + Arrays.toString(temp));

        int middle = (left + right) / 2;
        int i = left;
        int j = middle + 1;
        int t = 0;

        while (i <= middle && j <= right) {
            if (arr[i] < arr[j]) {
                temp[t] = arr[i];
                //左边的索引，向后移动一位
                i += 1;
                //临时的数组索引加一位
                t += 1;
            } else {
                temp[t] = arr[j];
                j += 1;
                t += 1;

            }
        }
        //处理完以上步骤，左边还有剩余的， 则追加到临时数组后边
        while (i <= middle) {
            temp[t] = arr[i];
            i += 1;
            t += 1;
        }

        //处理完以上步骤， 右边还有剩余的，则追加到临时数组后边
        while (j <= right) {
            temp[t] = arr[j];
            j += 1;
            t += 1;
        }

        // 这里进行合并，copy进去
        int tempLeft = left;
        t = 0;
        while (tempLeft <= right) {
            arr[tempLeft] = temp[t];
            tempLeft += 1;
            t += 1;
        }
        System.out.println("本次的临时数组" + Arrays.toString(temp) + "---合并后的数组" + Arrays.toString(arr));

    }

}
