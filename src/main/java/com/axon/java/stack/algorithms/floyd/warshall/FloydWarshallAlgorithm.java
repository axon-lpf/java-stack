package com.axon.java.stack.algorithms.floyd.warshall;

import com.sun.javafx.robot.FXRobotImage;

import java.util.Arrays;

/**
 * 添加弗洛伊德算法
 */
public class FloydWarshallAlgorithm {

    public static void main(String[] args) {

        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        //邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;// 表示不可以连接
        matrix[0] = new int[]{0, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, 0, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, 0, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, 0, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, 0, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, 0, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, 0};

        Graph graph = new Graph(vertex, matrix);

        graph.show();


        System.out.println("开始处理弗洛伊德算法");

        //弗洛伊德算法
        graph.floydWarshall();

        graph.show();
    }

}

/**
 * 创建图
 */
class Graph {


    char[] vertex;

    int[][] dis; // 顶点到其它顶点的最短距离

    int[][] pre; // 各个顶点的前驱节点

    public Graph(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.dis = matrix;
        this.pre = new int[vertex.length][vertex.length];

        //对pre初始化
        for (int i = 0; i < vertex.length; i++) {
            Arrays.fill(pre[i], i);
        }
    }


    /**
     *  显示的效果
     */
    public void show() {

        for (int k = 0; k < dis.length; k++) {

            for (int i = 0; i < dis.length; i++) {
                System.out.print(this.vertex[pre[k][i]] + " ");
            }

            System.out.println();

            for (int i = 0; i < dis.length; i++) {

                System.out.print(this.vertex[k]+"到"+this.vertex[i]+"的最短路径是"+dis[k][i] + " ");

            }
            System.out.println();
            System.out.println();
        }

    }

    /**
     * 弗洛伊德算法
     */
    public void floydWarshall() {

        int len = 0;
        for (int k = 0; k < vertex.length; k++) {  //中间节点

            for (int i = 0; i < vertex.length; i++) {   //起始节点

                for (int j = 0; j < vertex.length; j++) {  //结束节点

                    len = dis[i][k] + dis[k][j];  // i到k的距离 +  k到j的距离  < i到j的距离

                    if (len < dis[i][j]) {
                        dis[i][j] = len; // 更新距离，
                        pre[i][j] = pre[k][j];  // 更新前驱节点
                    }


                }

            }
        }
    }

}
