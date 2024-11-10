package com.axon.java.stack.data.structures.binary.tree;

/**
 * 顺序二叉树
 * 1>.顺序存储二叉树通常考虑完全二叉树
 * 2>.第n个元素的左子节点为2*n+1
 * 3>.第n个元素的右子节点为2*n+2
 * 4>.第n个元素的父节点为（n-1）/2
 */
public class SequentialBinaryTreeDemo {

    public static void main(String[] args) {

        SequentialBinaryTree binaryTree = new SequentialBinaryTree(new ArrayTree(new int[]{1, 2, 3, 4, 5, 6, 7}));

        System.out.println("开始前序遍历");
        binaryTree.preOrder();

        System.out.println("开始中序遍历");
        binaryTree.inOrder();

        System.out.println("开始后序遍历");
        binaryTree.postOrder();

    }
}

class SequentialBinaryTree {
    private ArrayTree arrayTree;

    public SequentialBinaryTree(ArrayTree arr) {
        this.arrayTree = arr;
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        arrayTree.preOrder(0);
    }

    /**
     * 中序遍历
     */
    public void inOrder() {
        arrayTree.inOrder(0);
    }

    /**
     * 后续遍历
     */
    public void postOrder() {
        arrayTree.postOrder(0);
    }

}


class ArrayTree {

    private int[] arr;

    /**
     * 构造函数
     *
     * @param arr
     */
    public ArrayTree(int[] arr) {
        this.arr = arr;
    }

    /**
     * 前序遍历
     */

    public void preOrder(int n) {
        if (arr == null || arr.length == 0) {
            return;
        }
        System.out.println(arr[n]);

        if (2 * n + 1 < arr.length) {
            preOrder(2 * n + 1);
        }
        if ((2 * n + 2) < arr.length) {
            preOrder(2 * n + 2);
        }
    }

    /**
     * 中序遍历
     *
     * @param n
     */
    public void inOrder(int n) {
        if (arr == null || arr.length == 0) {
            return;
        }
        if (2 * n + 1 < arr.length) {
            inOrder(2 * n + 1);
        }
        System.out.println(arr[n]);
        if (2 * n + 2 < arr.length) {
            inOrder(2 * n + 2);
        }
    }

    /**
     * 后续遍历
     *
     * @param n
     */
    public void postOrder(int n) {
        if (arr == null || arr.length == 0) {
            return;
        }
        if (2 * n + 1 < arr.length) {
            postOrder(2 * n + 1);
        }
        if ((2 * n + 2) < arr.length) {
            postOrder(2 * n + 2);
        }
        System.out.println(arr[n]);
    }
}
