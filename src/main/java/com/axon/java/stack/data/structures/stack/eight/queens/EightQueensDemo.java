package com.axon.java.stack.data.structures.stack.eight.queens;

import com.axon.java.stack.juc.threadlocal.PhantomReferenceDemo;

/**
 * 8皇后问题， 也是使用了回溯算法，去解决问题
 * <p>
 * 八皇后问题通常使用的是回溯算法（Backtracking Algorithm）来解决。
 * <p>
 * 回溯算法的基本思想：
 * <p>
 * 1.	从第一个皇后开始，依次尝试把皇后放在每一行的不同列上。
 * 2.	放置每个皇后时，检查当前皇后是否与之前放置的皇后冲突（即是否在同一行、列或对角线上）。
 * 3.	如果不冲突，则继续放置下一个皇后；如果冲突，则回溯到上一步，尝试其他列的位置。
 * 4.	重复这一过程，直到找到一个满足条件的解，或者遍历所有可能的位置组合。
 * <p>
 * 使用回溯法解决八皇后问题的特点：
 * <p>
 * •	深度优先搜索：回溯会在搜索树的每一个分支中进行尝试，直到找到一个解或确定某个分支无解。
 * •	剪枝：当发现某个位置不符合要求时，立即放弃继续尝试该分支，并回到上一步，减少不必要的计算。
 * <p>
 * 通过回溯法，能够有效地找到八皇后问题的所有解。
 * <p>
 * <p>
 * 对角线冲突检查是八皇后问题中的一个关键概念。要理解为什么使用 Math.abs(board[i] - col) == Math.abs(i - row) 来判断是否在同一对角线上，我们可以从几何角度来分析。
 * <p>
 * 对角线的几何属性
 * <p>
 * 在棋盘上，皇后可以攻击和它在同一行、同一列和同一对角线的所有位置。我们通常使用二维数组来表示棋盘，其中行号和列号分别对应棋盘的行和列。
 * <p>
 * 对角线的特性：
 * <p>
 * •	如果两个皇后在同一条 从左上到右下的对角线 上，那么它们的行号减列号的差值是相同的。例如，位置 (i1, j1) 和 (i2, j2) 如果位于同一条左上到右下的对角线上，那么 (i1 - j1) == (i2 - j2)。
 * •	如果两个皇后在同一条 从右上到左下的对角线 上，那么它们的行号与列号之和是相同的。例如，位置 (i1, j1) 和 (i2, j2) 如果位于同一条右上到左下的对角线上，那么 (i1 + j1) == (i2 + j2)。
 * <p>
 * 但为了简化判断，我们可以用 行号与列号的差的绝对值 来统一处理这两个方向上的对角线。即如果两个皇后 (i1, j1) 和 (i2, j2) 满足 |i1 - i2| == |j1 - j2|，那么它们位于同一对角线上。
 * <p>
 * 用公式判断对角线冲突
 * <p>
 * •	board[i] 表示第 i 行皇后所在的列。
 * •	col 表示当前尝试在第 row 行放置皇后的列。
 * •	i 表示已经放置了皇后的行，row 是当前尝试放置皇后的行。
 * <p>
 * 公式 Math.abs(board[i] - col) == Math.abs(i - row) 的意思是：如果已经放置的皇后和当前尝试放置的皇后，它们之间的行差和列差的绝对值相等，则说明它们在同一条对角线上。
 * <p>
 * 具体案例
 * <p>
 * 假设我们在一个 4x4 棋盘上放置皇后。
 * <p>
 * 场景1：第0行放置皇后在第1列 (0, 1)，尝试在第2行的第3列 (2, 3) 放置皇后。
 * <p>
 * •	已放置的皇后位置是 (0, 1)。
 * •	当前尝试放置的位置是 (2, 3)。
 * <p>
 * 检查是否在对角线：
 * <p>
 * •	行差是：|2 - 0| = 2
 * •	列差是：|3 - 1| = 2
 * <p>
 * 行差和列差相等，说明 (0, 1) 和 (2, 3) 位于同一对角线上，所以冲突，无法放置在 (2, 3)。
 * <p>
 * 场景2：第0行放置皇后在第1列 (0, 1)，尝试在第2行的第2列 (2, 2) 放置皇后。
 * <p>
 * •	已放置的皇后位置是 (0, 1)。
 * •	当前尝试放置的位置是 (2, 2)。
 * <p>
 * 检查是否在对角线：
 * <p>
 * •	行差是：|2 - 0| = 2
 * •	列差是：|2 - 1| = 1
 * <p>
 * 行差和列差不相等，说明 (0, 1) 和 (2, 2) 不在同一对角线上，所以没有冲突，可以放置在 (2, 2)。
 * <p>
 * 关键点
 * <p>
 * •	同列冲突：board[i] == col 检查当前皇后是否与已经放置的皇后在同一列。
 * •	对角线冲突：Math.abs(board[i] - col) == Math.abs(i - row) 检查当前皇后是否与已经放置的皇后在同一对角线上。
 * <p>
 * <p>
 * <p>
 * if (board[i] == col || Math.abs(board[i] - col) == Math.abs(i - row)) {
 * return false;
 * }
 * <p>
 * 1.	列冲突检查 (board[i] == col)：
 * •	board[i] 代表第 i 行皇后所在的列。col 是当前想要放置的列。
 * •	如果 board[i] == col，说明第 i 行的皇后和当前行的皇后位于同一列，因此返回 false 表示不安全。
 * 2.	对角线冲突检查 (Math.abs(board[i] - col) == Math.abs(i - row))：
 * •	对角线上的位置满足两点：
 * •	在左对角线上的皇后，其列和行的差值相等。
 * •	在右对角线上的皇后，其列和行的和相等。
 * •	检查当前皇后是否和前面已经放置的皇后位于对角线位置，即通过计算行和列的差值，若满足绝对差相等，则表示它们位于同一对角线上，需要返回 false。
 */
public class EightQueensDemo {

    // 定义皇后的数量
    private static final int N = 8;

    // 定义棋盘，  board[i] 表示第i行皇后放在那一列
    private int[] board = new int[N];

    private int count = 0;


    public void solve(int row) {
        if (placeQueen(row)) {
            // printSolution();
        } else {
            System.out.println("没有找到解决方案");
        }
    }


    // 递归地尝试在每一行放置皇后
    private boolean placeQueen(int row) {
        //表示已经成功放置了所有的皇后
        if (row == N) {
            count++;
            System.out.println("开始打印第" + count + "方案");
            printSolution();

            return true;
        }
        for (int col = 0; col < N; col++) {
            if (isSafe(row, col)) {
                board[row] = col;  //将皇后放置在该列
                //进行下一行的放置
                placeQueen(row + 1);
            }
        }


        // 否则无法放置
        return true;

    }

    /**
     * 判断是否在同一列，或者同一对角线上
     *
     * @param row
     * @param col
     * @return
     */
    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            // 检查是否在同一对角线上
            if (board[i] == col || Math.abs(board[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }


    // 打印棋盘解决方案
    private void printSolution() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i] == j) {
                    System.out.print("Q "); // Q 表示皇后
                } else {
                    System.out.print(". "); // . 表示空位
                }
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        EightQueensDemo queensDemo = new EightQueensDemo();
        queensDemo.solve(0);
    }

}
