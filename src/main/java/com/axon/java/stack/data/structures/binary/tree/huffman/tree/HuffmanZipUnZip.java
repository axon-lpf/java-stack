package com.axon.java.stack.data.structures.binary.tree.huffman.tree;

import java.io.*;
import java.util.Map;

/**
 * 赫夫曼的应用
 * 1.压缩文件
 * 2.解压文件
 */
public class HuffmanZipUnZip {

    public static void main(String[] args) {

        String inputFile = "D:\\workspace\\sun.bmp";
        String outFile = "D:\\workspace\\sun.zip";
        //压缩文件
        zip(inputFile, outFile);
        //解压文件
        inputFile = "D:\\workspace\\sun2.bmp";
        unZip(outFile, inputFile);

    }

    /**
     * 解压文件
     *
     * @param inputFile
     * @param outFile
     */
    public static void unZip(String inputFile, String outFile) {
        //输入流
        FileInputStream is = null;

        ObjectInputStream ois = null;

        //输出流
        FileOutputStream os = null;
        try {
            //读取压缩后的文件
            is = new FileInputStream(inputFile);
            //转换成对象流
            ois = new ObjectInputStream(is);
            byte[] humanBytes = (byte[]) ois.readObject();
            Map<Byte, String> humanCodes = (Map<Byte, String>) ois.readObject();
            HuffmanEncodeDecode huffmanEncode = new HuffmanEncodeDecode();
            byte[] decode = huffmanEncode.decode(humanBytes, humanCodes);
            os = new FileOutputStream(outFile);
            os.write(decode);
        } catch (Exception exception) {

        } finally {
            try {
                is.close();
                ois.close();
                os.close();
            } catch (IOException ioException) {
                System.out.println(ioException);
            }
        }


    }


    /**
     * 压缩文件
     *
     * @param inputFile
     * @param outputFile
     */
    public static void zip(String inputFile, String outputFile) {

        //输入流
        FileInputStream is = null;

        //对象流
        ObjectOutputStream oos = null;
        //输出流
        FileOutputStream os = null;

        try {
            is = new FileInputStream(inputFile);

            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            HuffmanEncodeDecode huffmanEncode = new HuffmanEncodeDecode();
            //获取赫夫曼树
            Map<Byte, String> huffmanCode = huffmanEncode.getHuffmanCode(huffmanEncode.buildHuffmanTree(bytes));
            //压缩处理
            byte[] zip = huffmanEncode.zip(bytes, huffmanCode);
            //处理输出流
            os = new FileOutputStream(outputFile);
            oos = new ObjectOutputStream(os);
            //写入压缩后的编码
            oos.writeObject(zip);
            //写入赫夫曼编码
            oos.writeObject(huffmanCode);


        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            try {
                is.close();
                os.close();
                oos.close();
            } catch (IOException ioException) {
                System.out.println(ioException);
            }
        }

    }
}
