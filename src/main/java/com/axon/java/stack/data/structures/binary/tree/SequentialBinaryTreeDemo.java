package com.axon.java.stack.data.structures.binary.tree;

/**
 * 顺序二叉树
 * 1>.顺序存储二叉树通常考虑完全二叉树
 * 2>.第n个元素的左子节点为2*n+1
 * 3>.第n个元素的右子节点为2*n+2
 * 4>.第n个元素的父节点为（n-1）/2
 *
 * 在完全二叉树（Complete Binary Tree）中，每层节点都从左到右依次填满，且结构紧凑，没有空隙。正因如此，可以将树的节点按层次顺序连续编号，然后通过编号关系推导出父子节点的位置。
 *
 * 完全二叉树的节点编号推导
 *
 * 假设树的根节点编号为 0，则对每个节点 i：
 *
 * 	1.	左子节点 的编号 = 2 * n + 1
 * 	2.	右子节点 的编号 = 2 * n + 2
 *
 * 这是因为在完全二叉树中，每一层节点数是上一层的 2 倍。
 *
 * 推导过程：
 *
 * 	1.	根节点编号为 0：假设根节点编号从 0 开始。
 * 	2.	第一层只有根节点，占用 1 个位置。
 * 	3.	第二层有 2 个节点（编号为 1 和 2）。
 * 	4.	第三层有 4 个节点（编号为 3、4、5、6），依此类推。
 *
 * 在这种结构下，编号为 i 的节点（假设它位于第 k 层），它的子节点编号可以推导如下：
 *
 * 	•	左子节点：按完全二叉树的排列规则，第 k+1 层第一个节点会出现在 2^(k+1) - 1 处，而当前节点的左子节点将会排在比它稍后的位置。因此，编号为 i 的节点的左子节点编号是 2 * i + 1。
 * 	•	右子节点：则在左子节点之后，即 2 * i + 2。
 *
 * 举例验证
 *
 * 以一个简单的完全二叉树为例：
 *
 * 假设节点编号从 0 开始：
 *
 *        0
 *      /   \
 *     1     2
 *    / \   / \
 *   3   4 5   6
 *
 *对于根节点 0：
 *
 * 	•	左子节点编号 = 2 * 0 + 1 = 1
 * 	•	右子节点编号 = 2 * 0 + 2 = 2
 *
 * 对于节点 1：
 *
 * 	•	左子节点编号 = 2 * 1 + 1 = 3
 * 	•	右子节点编号 = 2 * 1 + 2 = 4
 *
 * 对于节点 2：
 *
 * 	•	左子节点编号 = 2 * 2 + 1 = 5
 * 	•	右子节点编号 = 2 * 2 + 2 = 6
 *
 * 这符合完全二叉树的结构和编号规则。
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
