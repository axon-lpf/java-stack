package com.axon.java.stack.data.structures.binary.tree.huffman.tree;


import java.util.*;

/**
 * 赫夫曼编码
 *
 *     /**
 *      * 编码压缩核心步骤
 *      * 1>.获取字符串的bytes.
 *      * 2>.循环遍历字符串的bytes, 根据每个byte值，不断生成一个赫夫曼树
 *      * TreeNode{
 *      * Byte data; 对应字符串的byte值，
 *      * int  Weight;  权重，该字符串出现了多少次
 *      * TreeNode left;
 *      * TreeNode right;
 *      * }
 *      * 3>.根据赫夫曼树左边节点是0 ，右边节点是1， 父节点data为空的规则生成一个hashMap编码表 ， key 对应的是字符串的ASIC值， value 对应的是赫夫曼码
 *      * 4>.编码压缩
 *      * 4.1>循环遍历原有的字符串的bytes, 然后根据 byte值，即ASIC码，取出 赫夫曼编码表中的 赫夫曼编码，
 *      * 4.2>将所有的赫夫曼编码拼接成一个字符串
 *      * 4.3>将整个赫夫曼编码的字符串，按照二进制的8位进行切割， 然后转换成一个byte ,然后返回数组。
 *      *
 *
 *       解码核心步骤：
 *         1>.获得压缩后的byte数组 和赫夫曼编码表
 *         2>.循环遍历byte数组，恢复成原来的赫夫曼码
 *         3>.将赫夫曼编码表中key 和value进行对调，  原来的value变成key(赫夫曼码), 原来的key 变成value(ASIC码).
 *         4>.将第二步骤恢复的赫夫曼编码的字符串，循环遍历，根据 转换后的赫夫曼编码表中的key 进行匹配 。
 *         5>.第四步操作完毕之后得到一个list 或者byte数组，
 *         6>.将数组转换成String,就得到了原始的字符串结果
 *
 *  赫夫曼编码注意的事项：
 *      1>.压缩视频或者ppt等文件，压缩的效率不会有明显的变化
 *      2>.赫夫曼编码是按照字节来处理的，因此可以处理所有的文件（二进制文件、文本文件）
 *      3>.如果一个文件中，重复的内容不多，则压缩效果不是很明显
 *
 */
public class HuffmanEncodeDecodeDemo {

    public static void main(String[] args) {
        String str = "i like like like java do you like a java";
        str="你好，世界，世界是你们的，也是我们的，但还是你们的";

        byte[] bytes = str.getBytes();
        System.out.println("原始长度是" + bytes.length);
        System.out.println(Arrays.toString(bytes));


        HuffmanEncodeDecode huffmanEncode = new HuffmanEncodeDecode();
        //这里构建赫夫曼树
        EncodeNode encodeNode = huffmanEncode.buildHuffmanTree(bytes);
        huffmanEncode.preOrder(encodeNode);
        //根据赫夫曼树生成赫夫曼编码表  key 对应的的是字符串的ASIC码， value对应的是赫夫曼码
        Map<Byte, String> huffmanCode = huffmanEncode.getHuffmanCode(encodeNode);
        System.out.println("生成的赫夫曼编码表" + huffmanCode);

        //生成二进制的bytes
        byte[] zip = huffmanEncode.zip(bytes, huffmanCode);
        System.out.println("压缩后的结果是：" + Arrays.toString(zip));

        byte[] decode = huffmanEncode.decode(zip, huffmanCode);

        System.out.println("解码后结果是" + new String(decode));


        // 后面进行解码
        //1.将压缩后的二进制的字节码绩效恢复， 即恢复成原来的赫夫曼码
        //2.将原来的赫夫曼编码表中的key和value进行交换。 即key对应是赫夫曼码，value 对应的则是asic码
        //3.逐步遍历第一步恢复的赫夫曼码，去第二步中寻找，找出对应的value 然后返回一个byte数组，这个byte数组，则是原来的字符串的byte.


    }



}


