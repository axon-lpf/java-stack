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
     * 这段代码的作用是将一个 byte 类型的值 转换为 二进制字符串表示。它同时支持处理 负数的补码 表示，确保输出的二进制结果长度固定为 8 位。
     * 代码的功能分解：
     * 使用示例
     *      输入正数：byte b = 5，flag = true
     *      temp = 5，二进制表示：00000000 00000000 00000000 00000101
     *      Integer.toBinaryString(temp) 输出：101
     *      执行 temp |= 256，结果不变。
     *      最终截取 8 位为：00000101
     *      输入负数：byte b = -128，flag = true
     *      temp = -128，补码表示：11111111 11111111 11111111 10000000
     *      执行 temp |= 256，结果为：11111111 11111111 11111111 10000000
     *      截取最后 8 位为：10000000
     *      输入负数：byte b = -128，flag = false
     *      temp = -128
     *      Integer.toBinaryString(temp) 输出补码：11111111111111111111111110000000
     *      直接返回这整个二进制串。
     * @param b
     * @param flag
     * @return
     */
    public String byteToString(byte b, boolean flag) {
        //Java 中，byte 是 8 位有符号整数，范围是 -128 到 127。
        //由于 byte 类型参与位运算时会自动提升为 int（32 位），这里将 byte 转为 int 存储。
        //例如：-87   正数 87 的二进制表示：01010111，  -87 的补码：取反加 1：
        //取反：10101000
        //加 1：10101001
        //扩展到 32 位（符号扩展）：补高位：11111111 11111111 11111111 10101001
        //所以才有后续的截取操作binaryString.substring(binaryString.length() - 8);
        int temp = b;

        //如果是正数，则需要补高位
        //256 的二进制表示是 00000001 00000000（即补 8 个高位为 1 字节的长度）。
        if (flag) {
            //如果 temp 是负数（如 -128），Java 存储时用 补码 表示，直接转换成二进制可能少于 8位（例如补码的负数可能前面有一堆 1 高位）。
            //= 256 确保补齐 8 位长度的有效部分。
            temp |= 256; // 按位与运算
        }
        //将整数转换为 二进制字符串。
        //但结果的长度可能超过 8 位，比如负数的补码或加了 256 的正数。
        String binaryString = Integer.toBinaryString(temp);

        if (flag) {
            //表示需要强制取二进制表示的最后 8 位（有效的 1 字节部分）
            //binaryString.substring(binaryString.length() - 8) 的作用是截取最后 8 位。
            return binaryString.substring(binaryString.length() - 8);
        } else {
           // 表示直接返回整个二进制字符串，可能超过 8 位。
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
