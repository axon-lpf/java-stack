package com.axon.java.stack.data.structures.binary.tree.huffman.tree;

import java.util.*;

public class HuffmanEncodeDecode {


    /**
     * 解码逻辑
     *
     * @param bytes        压缩后的byte数组
     * @param huffmanCodes 赫夫曼编码表 ， ASIC和 赫夫曼码的 key  value
     * @return 返回解码后的byte数组
     */
    public byte[] decode(byte[] bytes, Map<Byte, String> huffmanCodes) {

        StringBuilder decodeString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {

            //判断当前是不是最后一位
            boolean flag = (i == bytes.length - 1);

            String s = this.byteToString(bytes[i], !flag);
            decodeString.append(s);
        }

        System.out.println("解码后的赫夫曼码" + decodeString);

        //处理huffmanCodes
        Map<String, Byte> huffmanByteCodes = new HashMap<>();

        for (Map.Entry<Byte, String> key : huffmanCodes.entrySet()) {
            huffmanByteCodes.put(key.getValue(), key.getKey());
        }

        List<Byte> result = new ArrayList<>();
        for (int i = 0; i < decodeString.length(); ) {

            boolean flag = true;
            int count = 1;
            Byte b = null;
            while (flag) {
                String key = decodeString.substring(i, i + count);

                b = huffmanByteCodes.get(key);
                if (b == null) {
                    count++;
                } else {
                    flag = false;
                }
            }
            result.add(b);
            i = i + count;
        }

        byte[] byteResult = new byte[result.size()];
        for (int i = 0; i < byteResult.length; i++) {

            byteResult[i] = result.get(i);
        }

        return byteResult;


    }


    /**
     * 将字节进行转换
     *
     * @param b
     * @param flag
     * @return
     */
    public String byteToString(byte b, boolean flag) {

        int temp = b;

        //如果是正数，则需要补高位
        if (flag) {
            temp |= 256; // 按位与运算
        }
        String binaryString = Integer.toBinaryString(temp);

        if (flag) {
            return binaryString.substring(binaryString.length() - 8);
        } else {
            return binaryString;
        }
    }


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
        //System.out.println("拼接结果：" + stringBuilder1);

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
