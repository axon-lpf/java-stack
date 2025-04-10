package com.axon.java.stack.algorithms.sort.heapsort;

import java.util.Arrays;

/**
 * 添加堆排序demo
 *
 * 堆排序步骤梳理
 *
 * 在堆排序中，n / 2 - 1 的计算用于确定从哪个索引开始构建最大堆，这个索引指向堆中最后一个非叶子节点。之所以需要从这个节点开始，是因为只有非叶子节点才能有子节点，因此可以成为堆调整的起点。以下是具体原因：
 *
 * 完全二叉树的性质
 *
 * 	1.	完全二叉树的节点编号：如果堆的根节点编号是 0，那么对于某个节点 i：
 * 	•	左子节点的编号是 2 * i + 1。
 * 	•	右子节点的编号是 2 * i + 2。
 * 	2.	叶子节点的特点：完全二叉树的叶子节点位于数组的后半部分，即从 n / 2 开始到 n - 1。
 * 	3.	非叶子节点的范围：由于 n / 2 到 n - 1 是叶子节点，因此非叶子节点的范围是从索引 0 到 n / 2 - 1。
 *
 * 为什么从 n / 2 - 1 开始？
 *
 * 堆排序中，堆调整（heapify）通常从最后一个非叶子节点向前进行，因为只有非叶子节点才需要检查并调整其子节点的顺序。n / 2 - 1 正是最后一个非叶子节点的索引位置，从这里开始，逐步向前调整整个堆，直到根节点，从而完成堆的初始化。
 *
 * 举例说明
 *
 * 假设有一个长度为 n = 10 的数组，表示一个完全二叉树结构：
 *
 * 	•	n / 2 - 1 = 10 / 2 - 1 = 4
 * 	•	索引为 4 的位置即为最后一个非叶子节点，索引 5 到 9 都是叶子节点。
 *
 * 因此，从索引 4 开始调整堆，将叶子节点与父节点满足堆的条件，再继续向前调整每个非叶子节点的子树，最终得到一个最大堆。
 *
 *
 * 我们用数组 arr = {4, 6, 8, 5, 9} 作为例子来详细展示堆排序过程，并配上简略结构图。这里我们希望将数组升序排列（从小到大），所以会首先构建一个最大堆。
 *
 * 1. 构建最大堆
 *
 * 从数组的第一个非叶节点开始自下而上调整。对于 arr = {4, 6, 8, 5, 9}，数组长度为 5，第一个非叶节点位置为 n / 2 - 1 = 5 / 2 - 1 = 1。
 *
 * (1) 从索引 1 开始调整
 *
 * 当前节点值为 6，左右子节点分别是 6 的位置 3 值为 5，位置 4 值为 9。
 * 发现右子节点 9 比 6 大，所以交换位置 1 和位置 4 的值。
 *
 * 调整前：
 *        4
 *      /   \
 *     6     8
 *    / \
 *   5   9
 *
 * 调整后：
 *        4
 *      /   \
 *     9     8
 *    / \
 *   5   6
 *
 * 调整后，数组变成 arr = {4, 9, 8, 5, 6}。
 *
 * (2) 从索引 0 开始调整
 *
 * 当前节点值为 4，左右子节点分别是 9 和 8。最大的是 9，交换位置 0 和位置 1 的值。
 *
 * 调整前：
 *        4
 *      /   \
 *     9     8
 *    / \
 *   5   6
 *
 * 调整后：
 *        9
 *      /   \
 *     4     8
 *    / \
 *   5   6
 *
 * 调整后，数组变成 arr = {9, 4, 8, 5, 6}。
 *
 *(3) 继续调整索引 1 的子节点
 *
 * 现在索引 1 的值为 4，其子节点为 5 和 6。最大的是 6，交换位置 1 和位置 4 的值。
 *
 * 调整前：
 *         9
 *      /   \
 *     4     8
 *    / \
 *   5   6
 *
 * 调整后：
 *        9
 *      /   \
 *     6     8
 *    / \
 *   5   4
 *
 * 调整后，数组变成 arr = {9, 6, 8, 5, 4}。
 *
 * 此时，最大堆构建完成，堆结构如下：
 *
 *         9
 *      /   \
 *     6     8
 *    / \
 *   5   4
 *
 *
 * 2. 排序过程
 *
 * 接下来，开始排序步骤，将堆顶元素（最大值）逐步放到数组的最后。
 *
 * (1) 第一次交换
 *
 * 将堆顶元素 9 与数组最后一个元素 4 交换，数组变为 arr = {4, 6, 8, 5, 9}。然后对前 4 个元素（{4, 6, 8, 5}）重新调整为最大堆。
 *
 * 调整前：
 *        4
 *      /   \
 *     6     8
 *    /
 *   5
 * 最大子节点为 8，交换位置 0 和位置 2 的值。
 *
 * 调整后：
 *        8
 *      /   \
 *     6     4
 *    /
 *   5
 *
 *调整后，数组变成 arr = {8, 6, 4, 5, 9}。
 *
 * (2) 第二次交换
 *
 * 将堆顶元素 8 与当前最后一个元素 5 交换，数组变为 arr = {5, 6, 4, 8, 9}。对前 3 个元素（{5, 6, 4}）调整为最大堆。
 *
 * 调整前：
 *         5
 *      /   \
 *     6     4
 *
 *最大子节点为 6，交换位置 0 和位置 1 的值。
 *
 * 调整后：
 *        6
 *      /   \
 *     5     4
 *调整后，数组变成 arr = {6, 5, 4, 8, 9}。
 *
 * (3) 第三次交换
 *
 * 将堆顶元素 6 与当前最后一个元素 4 交换，数组变为 arr = {4, 5, 6, 8, 9}。对前 2 个元素（{4, 5}）调整为最大堆。
 *
 * 调整前：
 *        4
 *      /
 *     5
 *交换位置 0 和位置 1 的值。
 *
 * 调整后：
 *        5
 *      /
 *     4
 * 调整后，数组变成 arr = {5, 4, 6, 8, 9}。
 *
 * (4) 第四次交换
 *
 * 最后将堆顶元素 5 与 4 交换，得到最终排序的数组 arr = {4, 5, 6, 8, 9}。
 *
 *
 *

 *
 *
 *
 */
public class HeapSortDemo {

    public static void main(String[] args) {

       int []  arr = {4, 6, 8, 5, 9};  //初始化数组

        for (int i = (arr.length/2)-1; i >=0 ; i--) {
            //构建大顶堆
            addJust(arr, i, arr.length);
            System.out.println(Arrays.toString(arr));
        }

        for (int j = arr.length-1; j >0 ; j--) {
            int temp = arr[j]; //将最小的值 赋值到临时变量
            arr[j]= arr[0]; // 将最大值赋值度到最后一位
            arr[0]=temp; // 将最小值赋值到第一位
            addJust(arr, 0, j);
        }

        System.out.println(Arrays.toString(arr));

    }

    /**
     *  构造大顶堆
     * @param arr
     * @param i
     * @param length
     */
    public  static  void addJust( int [] arr,int i, int length){
        int temp = arr[i];

        for (int k =i *2+1 ; k <length; k=k*2+1) {
            //如果左边节点小于右边节点， 则k++,取右边节点的索引
            if (k+1<length&& arr[k]< arr[k+1]) {
                k++;
            }
            //非叶子节点小于右节点
            if (temp<arr[k]) {
                //将右边节点的值，赋值给非叶子结点
                arr[i] = arr[k];
                //将右边节点的索引赋值给i
                i = k;
            }else {
                break;
            }
        }
        //将非叶子节点，赋值给右边节点， 此时就完成了交换
        arr[i] = temp;
    }
}
