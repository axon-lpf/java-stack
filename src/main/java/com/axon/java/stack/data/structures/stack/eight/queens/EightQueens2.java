package com.axon.java.stack.data.structures.stack.eight.queens;

public class EightQueens2 {

    private static final int N = 8; // 棋盘的大小 (8x8)
    private static int[] board = new int[N]; // 存储每行皇后的位置
    private static int solutionCount = 0; // 解法的数量

    public static void main(String[] args) {
        // 从第0行开始放置皇后
        solve(0);
        System.out.println("总共找到的解法数量为: " + solutionCount);
    }

    // 尝试在第 row 行放置皇后
    public static void solve(int row) {
        // 如果已经成功放置了 N 个皇后，说明找到一个解
        if (row == N) {
            solutionCount++;
            printSolutionWithBoard();
            //printSolution();
            return;
        }

        // 尝试在当前行的每一列放置皇后
        for (int col = 0; col < N; col++) {
            if (isSafe(row, col)) {
                board[row] = col; // 将皇后放置在当前列
                solve(row + 1);   // 递归尝试下一行
                // 回溯，不需要手动重置 board[row]，因为在下一次递归时会覆盖
            }
        }
    }

    // 检查在 (row, col) 位置放置皇后是否安全
    public static boolean isSafe(int row, int col) {
        for (int x = 0; x < row; x++) {
            // 检查同一列是否已有皇后
            if (board[x] == col) {
                return false;
            }
            // 检查是否在同一对角线上
            if (Math.abs(board[x] - col) == Math.abs(x - row)) {
                return false;
            }
        }
        return true;
    }

    // 打印一个解法中所有皇后的位置
    public static void printSolution() {
        System.out.println("解法 " + solutionCount + ":");
        for (int row = 0; row < N; row++) {
            System.out.print("皇后" + (row + 1) + "： (行, 列) = (" + (row + 1) + ", " + (board[row] + 1) + ")");
            System.out.println();
        }
        System.out.println();
    }


    // 打印一个解法中所有皇后的位置和棋盘布局
    public static void printSolutionWithBoard() {
        System.out.println("解法 " + solutionCount + ":");
        // 打印每个皇后的坐标位置
        for (int row = 0; row < N; row++) {
            System.out.print("皇后" + (row + 1) + "： (行, 列) = (" + (row + 1) + ", " + (board[row] + 1) + ")");
            System.out.println();
        }
        System.out.println();

        // 打印棋盘布局
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
        System.out.println();
    }

}
