package com.axon.java.stack.data.structures.stack.backtracking;

/**
 * 回溯算法
 * <p>
 * 对应场景： 迷宫的问题
 * <p>
 * 迷宫问题通常是一个经典的回溯问题或图的遍历问题，解决方案可以使用深度优先搜索（DFS）、广度优先搜索（BFS）或者回溯法。下面以回溯法（即深度优先搜索）的方式来编写一个解决迷宫问题的算法，迷宫中我们假设有如下规则：
 * <p>
 * •	0 表示可以通过的空格
 * •	1 表示墙壁或障碍物
 * •	S 表示起点
 * •	E 表示终点
 * <p>
 * 我们将编写一个迷宫求解算法，找到从起点到终点的一条可行路径。
 * <p>
 * 迷宫问题的算法：深度优先搜索（DFS）
 * <p>
 * 迷宫示例
 * <p>
 * 假设迷宫是一个二维数组：
 * <p>
 * [
 * [0, 1, 0, 0, 0],
 * [0, 1, 0, 1, 0],
 * [0, 0, 0, 1, 0],
 * [0, 1, 1, 1, 0],
 * [0, 0, 0, 0, 0]
 * ]
 * 起点为 (0, 0)，终点为 (4, 4)，我们将使用深度优先搜索寻找一条从起点到终点的路径。
 */
public class BacktrackingDemo {


    // 设置迷宫的大小为5
    private static final int N = 5;


    //这里表示是否被访问过
    private static boolean[][] visited = new boolean[N][N];

    //设置起点
    private static int startX = 1, startY = 2;

    // 设置终点
    private static int endX = 4, endY = 4;


    //方向数组， 表示上下左右4个方向
    private static final int[] dx = {1, -1, 0, 0};
    private static final int[] dy = {0, 0, 1, -1};


    // 迷宫示例：0 表示可以通过，1 表示障碍物
    private static int[][] maze = {
            {0, 1, 0, 0, 0},
            {0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
    };


    /**
     * 执行主方法
     *
     * @param args
     */
    public static void main(String[] args) {
        if (solveMaze(startX, startY)) {
            System.out.println("找到一条路径！");
        } else {
            System.out.println("没有找到路径。");
        }
        // 说明找到了终点
        printSolution();

    }


    /**
     * 深度优先的去寻找路径
     *
     * @param x
     * @param y
     * @return
     */
    public static boolean solveMaze(int x, int y) {
        System.out.println("访问坐标:(x=" + x + ",y=" + y + ")");

        // 如果相等，说明找到了终点
        if (x == endX && y == endY) {
            visited[x][y] = true;  // 标记终点为路径的一部分
            buildResult(x, y, true);
            return true;
        }
        if (!isSafe(x, y)) {
            buildResult(x, y, false);
            return false;
        }

        //标记当前节点已访问
        visited[x][y] = true;

        // 递归探索4个方向，
        for (int i = 0; i < 4; i++) {
            // 这里先去验证下， 上两个方位
            // 再次去验证右左的方位
            int newX = x + dx[i];
            int newY = y + dy[i];

            // 这里尝试递归到下一个节点
            if (solveMaze(newX, newY)) {
                buildResult(x, y, true);
                return true;
            }
        }
        //如果没有找到路径， 则回溯， 撤销该点的访问状态
        visited[x][y] = false;
        buildResult(x, y, false);
        return false;
    }

    private static void buildResult(int x, int y, Boolean result) {
        System.out.println("访问坐标:(x=" + x + ",y=" + y + ")" + "处理结束了。。。。。。。。。。。。。。返回" + result);
    }

    /**
     * 这里检查是否可以移动到新的位置
     *
     * @param x
     * @param y
     * @return
     */
    private static boolean isSafe(int x, int y) {
        // maze[x][y] == 0  表示不是墙
        // !visited[x][y] 表示没有被访问过
        //判断xy 是否在范围内
        return (x >= 0 && x < N && y >= 0 && y < N && maze[x][y] == 0 && !visited[x][y]);
    }

    private static void printSolution() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (visited[i][j]) {
                    System.out.print("S "); // S 表示路径
                } else {
                    System.out.print(maze[i][j] + " ");
                }
            }
            System.out.println();

        }
    }


}
