package com.axon.java.stack.algorithms.dijkstra;

import java.util.Arrays;

/**
 * 迪杰斯特拉算法
 */
public class DijkstraAlgorithm {

    public static final int INF = 65535;

    public static void main(String[] args) {

        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        //克鲁斯卡尔算法的邻接矩阵
        int matrix[][] = {
                /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/ {0, 12, INF, INF, INF, 16, 14},
                /*B*/ {12, 0, 10, INF, INF, 7, INF},
                /*C*/ {INF, 10, 0, 3, 5, 6, INF},
                /*D*/ {INF, INF, 3, 0, 4, INF, INF},
                /*E*/ {INF, INF, 5, 4, 0, 2, 8},
                /*F*/ {16, 7, 6, INF, 2, 0, 9},
                /*G*/ {14, INF, INF, INF, 8, 9, 0}
        };

        Dijkstra dijkstra = new Dijkstra(vertex, matrix);
        dijkstra.dijkstra(6);


    }
}


class Dijkstra {

    //顶点数组
    char[] vertex;

    //邻结举证
    int[][] matrix;

    VertexDijkstra vertexDijkstra;


    public Dijkstra(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;

    }

    /**
     * 迪杰斯特拉算
     *
     * @param index
     */
    public void dijkstra(int index) {
        vertexDijkstra = new VertexDijkstra(index, vertex.length);
        update(index);
        for (int i = 1; i < vertex.length; i++) {
            int minLengthIndex = getMinLengthIndex();
            update(minLengthIndex);
        }
    }

    /**
     * 更新一个顶点到其它顶点的长度
     *
     * @param index 顶点下标
     */
    public void update(int index) {
        int len;
        for (int j = 1; j < matrix[index].length; j++) {
            // 说明是连通道
            if (matrix[index][j] < 65535) {
                len = vertexDijkstra.getDis(index) + matrix[index][j];

                if (!vertexDijkstra.in(index) && len < vertexDijkstra.getDis(j)) {
                    //更新目标节点长度
                    vertexDijkstra.updateDis(j, len);
                    //更新前置顶点
                    vertexDijkstra.updatePre(j, index);
                }
            }
        }
    }


    /**
     * 获取下一个最小顶点的长度
     *
     * @return
     */
    public int getMinLengthIndex() {
        int min = 0, index = -1;
        for (int i = 0; i < vertex.length; i++) {
            if (!vertexDijkstra.in(i) && min < vertexDijkstra.dis[i]) {
                min = vertexDijkstra.dis[i];
                index = i;
            }
        }
        vertexDijkstra.already_visited[index]=1;
        return index;
    }


}

class VertexDijkstra {

    // 已经访问的顶点
    int[] already_visited;

    //前置顶点
    int[] pre;

    //访问
    int[] dis;


    public VertexDijkstra(int index, int length) {
        already_visited = new int[length];
        pre = new int[length];
        dis = new int[length];
        Arrays.fill(dis, 65535);
        dis[index]=0;
        already_visited[index]=1;

    }

    /**
     * 判断是否已经被访问过程
     *
     * @param index 顶点下标
     * @return true代表已经访问
     */
    public boolean in(int index) {
        return already_visited[index] == 1;
    }

    /**
     * 更新前置节点
     *
     * @param index 顶点下标
     * @param j     下标
     */
    public void updatePre(int index, int j) {
        pre[j] = index;
    }

    /**
     * 更新对应顶点的终点长度
     *
     * @param index  顶点位置
     * @param length 长度
     */
    public void updateDis(int index, int length) {
        dis[index] = length;
    }

    /**
     * 获取终点顶点的长度
     *
     * @param index 顶点下标索引
     * @return 返回值
     */
    public int getDis(int index) {
        return dis[index];
    }

}