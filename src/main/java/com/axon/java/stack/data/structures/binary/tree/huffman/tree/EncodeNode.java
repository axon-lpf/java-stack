package com.axon.java.stack.data.structures.binary.tree.huffman.tree;

public class EncodeNode implements Comparable<EncodeNode> {


    public EncodeNode(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "EncodeNode{" +
                "weight=" + weight +
                ", data=" + data +
                '}';
    }

    /**
     * 用于储存ASIC码
     */
    public Byte data;

    /**
     * 用于存储权重， 即 一个ASIC码 对应出现了多少个
     */
    public int weight;

    /**
     * 左边节点
     */
    public EncodeNode leftNode;

    /**
     * 右边节点
     */
    public EncodeNode rightNode;


    /**
     * 比较大小
     *
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(EncodeNode o) {
        return this.weight - o.weight;
    }

    public void preOrder() {
        System.out.println(this);

        if (this.leftNode != null) {
            this.leftNode.preOrder();
        }
        if (this.rightNode != null) {
            this.rightNode.preOrder();
        }
    }
}
