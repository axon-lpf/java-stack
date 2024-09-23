package com.axon.java.stack.data.structures.sparsearry;


/**
 * 稀疏数组案例
 * <p>
 * 模拟围棋盘， 围棋盘  11 * 11 的方格，  中间有部分有子， 有部分没子。
 * <p>
 * 初始版本： 可以采用二维数组存储， 但是二维数组对  11* 11 一一进行储存太浪费空间， 因为没有子的的她的值则为0。
 * <p>
 * 行   列     值
 * <p>
 * <p>
 * 优化版本： 采用稀疏数组， 针对有值的进行存储，  稀疏数组
 * 稀疏数组格索引则储存的是  有多上行， 多少列，  总共有多少值
 * <p>
 * 总结
 * •	稀疏数组 是一种高效存储具有大量相同值的稀疏矩阵的方式。它只存储非零值的位置及其数值，减少了空间浪费。
 * •	在围棋盘模拟中，稀疏数组能够显著节省空间，并且方便数据的存储和恢复。
 * •	该代码直观地展示了二维数组和稀疏数组的转换过程，是学习稀疏数组概念的良好范例。
 */
public class SparseArrayDemo {

    public static void main(String[] args) {
        int sumCount = 0;
        // 原始版本
        int[][] chars = new int[11][11];
        //对棋盘进行赋值
        chars[3][5] = 2;
        //对棋盘进行赋值
        chars[6][10] = 1;
        chars[5][3] = 1;
        // 循环遍历出棋盘结构
        for (int i = 0; i < chars.length; i++) {
            int[] cells = chars[i];
            System.out.println();
            for (int j = 0; j < cells.length; j++) {

                int value = chars[i][j];
                if (value > 0) {
                    sumCount = sumCount + 1;
                }
                System.out.print(chars[i][j] + "\t");
            }
        }
        System.out.println();

        //给稀疏数组进行赋值
        int[][] sparse = new int[sumCount + 1][3];
        //设置行
        sparse[0][0] = 11;
        // 设置列
        sparse[0][1] = 11;

        // 设置总数
        sparse[0][2] = sumCount;
        int count = 0;

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chars[i][j] > 0) {
                    count++;
                    // 将行数赋值
                    sparse[count][0] = i;
                    // 将列进行赋值
                    sparse[count][1] = j;
                    // 将对应的值进行赋值
                    sparse[count][2] = chars[i][j];
                }
            }
        }

        System.out.println("以下是稀疏数组的值------------------");
        // 打印出稀疏数组
        for (int i = 0; i < sparse.length; i++) {
            int[] cells = sparse[i];
            System.out.println();
            for (int j = 0; j < cells.length; j++) {

                System.out.print(sparse[i][j] + "\t");
            }
        }
        System.out.println();
        System.out.println("开始恢复棋盘");
        //恢复棋盘
        int rows = sparse[0][0];
        int cells = sparse[0][1];

        int newChars[][] = new int[rows][cells];
        for (int i = 1; i < sparse.length; i++) {
            // 进行赋值 还原
            newChars[sparse[i][0]][sparse[i][1]] = sparse[i][2];
        }

        for (int i = 0; i < newChars.length; i++) {
            System.out.println();
            for (int j = 0; j < newChars[i].length; j++) {

                System.out.print(newChars[i][j] + "\t");
            }
        }
        System.out.println();

    }
}
