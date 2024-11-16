package com.axon.java.stack.data.structures.binary.tree;


import java.util.*;

/**
 * 赫夫曼编码
 */
public class HuffmanEncodeDemo {

    public static void main(String[] args) {
        String str = "i like like like java do you like a java";

        byte[] bytes = str.getBytes();
        System.out.println("原始长度是" + bytes.length);
        System.out.println(Arrays.toString(bytes));

        HuffmanEncode huffmanEncode = new HuffmanEncode();
        //这里构建赫夫曼树
        EncodeNode encodeNode = huffmanEncode.buildHuffmanTree(bytes);
        huffmanEncode.preOrder(encodeNode);
        //根据赫夫曼树生成赫夫曼编码表  key 对应的的是字符串的ASIC码， value对应的是赫夫曼码
        Map<Byte, String> huffmanCode = huffmanEncode.getHuffmanCode(encodeNode);
        System.out.println("生成的赫夫曼编码表" + huffmanCode);

        //生成二进制的bytes
        byte[] zip = huffmanEncode.zip(bytes, huffmanCode);

        System.out.println("压缩后的结果是：" + Arrays.toString(zip));

        // 后面进行解码
        //1.将压缩后的二进制的字节码绩效恢复， 即恢复成原来的赫夫曼码
        //2.将原来的赫夫曼编码表中的key和value进行交换。 即key对应是赫夫曼码，value 对应的则是asic码
        //3.逐步遍历第一步恢复的赫夫曼码，去第二步中寻找，找出对应的value 然后返回一个byte数组，这个byte数组，则是原来的字符串的byte.


    }


    /**
     *  编码压缩核心步骤
     *  1>.获取字符串的bytes.
     *  2>.循环遍历字符串的bytes, 根据每个byte值，不断生成一个赫夫曼树
     *     TreeNode{
     *         Byte data; 对应字符串的byte值，
     *         int  Weight;  权重，该字符串出现了多少次
     *         TreeNode left;
     *         TreeNode right;
     *     }
     *  3>.根据赫夫曼树左边节点是0 ，右边节点是1， 父节点data为空的规则生成一个hashMap编码表 ， key 对应的是字符串的ASIC值， value 对应的是赫夫曼码
     *  4>.编码压缩
     *      4.1>循环遍历原有的字符串的bytes, 然后根据 byte值，即ASIC码，取出 赫夫曼编码表中的 赫夫曼编码，
     *      4.2>将所有的赫夫曼编码拼接成一个字符串
     *      4.3>将整个赫夫曼编码的字符串，按照二进制的8位进行切割， 然后转换成一个byte ,然后返回数组。
     * @param bytes
     * @return
     */
    public static byte[] zipEncode(byte[] bytes) {
        HuffmanEncode huffmanEncode = new HuffmanEncode();
        //这里构建赫夫曼树
        EncodeNode encodeNode = huffmanEncode.buildHuffmanTree(bytes);

        Map<Byte, String> huffmanCode = huffmanEncode.getHuffmanCode(encodeNode);
        System.out.println("生成的赫夫曼编码表" + huffmanCode);
        //生成二进制的bytes
        byte[] zip = huffmanEncode.zip(bytes, huffmanCode);

        System.out.println("压缩后的结果是：" + Arrays.toString(zip));

        return zip;
    }
}


class HuffmanEncode {


    /**
     * 根据赫夫曼去压缩
     *
     * @param bytes       原始字符串的字节码
     * @param huffmanCode 赫夫曼编码表
     * @return 返回根据赫夫曼压缩后的字节数组
     */
    public byte[] zip(byte[] bytes, Map<Byte, String> huffmanCode) {

        StringBuilder stringBuilder1 = new StringBuilder();
        // 循环遍历拼接， 这里是根据顺序拼接而成
        for (int i = 0; i < bytes.length; i++) {
            //按照字符串的字节码顺序，找出对应的赫夫曼码进行拼接，
            stringBuilder1.append(huffmanCode.get(bytes[i]));
        }
        System.out.println("拼接结果：" + stringBuilder1);

        //根据拼接的结果转换成一个二进制的数组。
        //1.计算byte长度
        int length;
        if (stringBuilder1.length() % 8 == 0) {
            length = stringBuilder1.length() / 8;
        } else {
            length = stringBuilder1.length() / 8 + 1;
        }

        byte[] result = new byte[length];

        int index = 0;
        // for循环遍历length
        for (int i = 0; i < stringBuilder1.length(); i += 8) {

            byte b;
            if (i + 8 > stringBuilder1.length()) {
                b = (byte) Integer.parseInt(stringBuilder1.substring(i, stringBuilder1.length()), 2);
            } else {
                b = (byte) Integer.parseInt(stringBuilder1.substring(i, i + 8), 2);
            }
            result[index] = b;
            index++;
        }
        return result;

    }


    /**
     * 拼接， 用于获取哈夫曼cod
     */
    static StringBuilder stringBuilder = new StringBuilder();

    /**
     * 返回对应赫夫曼code
     */
    static Map<Byte, String> huffmanCodes = new HashMap<>();


    public Map<Byte, String> getHuffmanCode(EncodeNode node) {
        if (node == null) {
            return new HashMap<>();
        }
        getHuffmanCode(node.leftNode, "0", stringBuilder);
        getHuffmanCode(node.rightNode, "1", stringBuilder);

        return huffmanCodes;
    }

    /**
     * 获取哈夫曼编码表
     *
     * @param node          根节点
     * @param code          路径
     * @param stringBuilder 拼接路径
     */
    private void getHuffmanCode(EncodeNode node, String code, StringBuilder stringBuilder) {

        StringBuilder result = new StringBuilder(stringBuilder);
        result.append(code);

        if (node != null) {
            if (node.data == null) {
                getHuffmanCode(node.leftNode, "0", result);
                getHuffmanCode(node.rightNode, "1", result);
            } else {
                //可以对应的是字符串的ASIC码， value 对应的是赫夫曼码
                huffmanCodes.put(node.data, result.toString());
            }
        }

    }

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
            parentNode.leftNode = leftNode;
            parentNode.rightNode = rightNode;

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
