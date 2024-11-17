package com.axon.java.stack.data.structures.binary.tree.huffman.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 赫夫曼树
 */
public class HuffmanTreeDemo {

    public static void main(String[] args) {

        int[] arr = {13, 7, 8, 3, 29, 6,1};

        HuffmanTree huffmanTree = new HuffmanTree();

        Node node = huffmanTree.buildHuffmanTree(arr);

        huffmanTree.preOrder(node);


    }
}


class HuffmanTree {

    /**
     * 构建一个赫夫曼树
     *
     * @param arr
     * @return
     */
    public Node buildHuffmanTree(int[] arr) {

        List<Node> nodeList = new ArrayList<Node>();
        //构建成一个集合
        for (int i = 0; i < arr.length; i++) {
            nodeList.add(new Node(arr[i]));
        }

        while (nodeList.size() > 1) {
            //排序，从到大排序
            Collections.sort(nodeList);


            Node leftNode = nodeList.get(0);
            Node rightNode = nodeList.get(1);

            Node parentNode = new Node(leftNode.value + rightNode.value);

            parentNode.left = leftNode;
            parentNode.right = rightNode;

            nodeList.add(parentNode);

            nodeList.remove(leftNode);
            nodeList.remove(rightNode);
        }

        return nodeList.get(0);

    }

    public void preOrder(Node root) {
        if (root == null) {
            System.out.println("当前的赫夫曼树为空");
            return;
        }
        root.preOrder();
    }

}


/**
 * node 节点
 */
class Node implements Comparable<Node> {



    public int value;

    public Node left;

    public Node right;

    public Node(int value) {
        this.value = value;
    }

    /**
     * 前缀遍历
     */
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    /**
     * 比较器
     *
     * compareTo 方法的返回值意义如下：
     *
     * 返回负值：表示当前对象 (this) 小于参数对象 (o)。
     * 返回零：表示当前对象 (this) 等于参数对象 (o)。
     * 返回正值：表示当前对象 (this) 大于参数对象 (o)。
     *
     * 在这个方法中，Node 是一个对象，包含了一个名为 value 的字段，通常是一个整数。此方法比较 this 对象的 value 字段与 o 对象的 value 字段的差值。具体来说：
     *
     * this.value - o.value：通过计算当前对象的 value 与另一个对象的 value 的差值来实现比较：
     * 如果 this.value > o.value，返回正值。
     * 如果 this.value < o.value，返回负值。
     * 如果 this.value == o.value，返回零。
     * 3. 底层原理
     * 整数计算：this.value - o.value 直接通过整数相减来进行比较。这是一个非常高效的操作，因为整数相减在计算机底层通常是一个直接的算术运算。
     *
     * 避免溢出问题：这种实现方式假设 value 是整数类型。如果两个整数相差过大，可能会导致溢出（例如，Integer.MIN_VALUE - Integer.MAX_VALUE 会导致溢出）。为了避免这种情况，可以采用 Integer.compare(this.value, o.value) 来安全地进行比较，避免溢出问题。
     *
     *Integer.compare(a, b) 的底层会首先进行 a - b 计算，然后根据结果返回合适的值，这个方法能够有效避免溢出的情况。
     *
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
