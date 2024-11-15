package com.axon.java.stack.data.structures.binary.tree;


import java.util.*;

/**
 * 赫夫曼编码
 */
public class HuffmanEncodeDemo {

    public static void main(String[] args) {
        String str = "i like like like java do you like a java ";

        byte[] bytes = str.getBytes();
        System.out.println(Arrays.toString(bytes));

        HuffmanEncode huffmanEncode = new HuffmanEncode();
        //List<EncodeNode> encodeNodes = huffmanEncode.getEncodeNodes(bytes);
        EncodeNode encodeNode = huffmanEncode.buildHuffmanTree(bytes);
        huffmanEncode.preOrder(encodeNode);

    }
}


class HuffmanEncode {

    /**
     * 构建霍夫曼书
     *
     * @param bytes
     * @return
     */
    public EncodeNode buildHuffmanTree(byte[] bytes) {

        List<EncodeNode> encodeNodes = getEncodeNodes(bytes);

        while (encodeNodes.size() > 1) {
            Collections.sort(encodeNodes);

            EncodeNode leftNode = encodeNodes.get(0);
            EncodeNode rightNode = encodeNodes.get(1);

            EncodeNode parentNode = new EncodeNode(null, leftNode.weight + rightNode.weight);
            parentNode.leftNode=leftNode;
            parentNode.rightNode=rightNode;

            encodeNodes.add(parentNode);
            encodeNodes.remove(leftNode);
            encodeNodes.remove(rightNode);
        }
        return encodeNodes.get(0);
    }


    /**
     * 构建node节点
     *
     * @param bytes
     * @return
     */
    public List<EncodeNode> getEncodeNodes(byte[] bytes) {
        List<EncodeNode> encodeNodes = new ArrayList<>();

        Map<Byte, Integer> map = new HashMap<>();

        for (int i = 0; i < bytes.length; i++) {

            if (map.containsKey(bytes[i])) {
                map.put(bytes[i], map.get(bytes[i]) + 1);
            } else {
                map.put(bytes[i], 1);
            }
        }

        for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
            EncodeNode encodeNode = new EncodeNode(entry.getKey(), entry.getValue());
            encodeNodes.add(encodeNode);
        }
        return encodeNodes;
    }


    /**
     * 前序遍历
     *
     * @param node
     */
    public void preOrder(EncodeNode node) {
        if (node != null) {
            node.preOrder();
        } else {
            System.out.println("当前节点为空");
        }

    }

}


class EncodeNode implements Comparable<EncodeNode> {


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
     *  用于储存ASIC码
     */
    private Byte data;

    /**
     *  用于存储权重， 即 一个ASIC码 对应出现了多少个
     */
    public int weight;

    /**
     *  左边节点
     */
    public EncodeNode leftNode;

    /**
     *  右边节点
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
