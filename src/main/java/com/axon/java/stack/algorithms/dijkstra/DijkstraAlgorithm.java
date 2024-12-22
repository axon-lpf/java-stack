package com.axon.java.stack.algorithms.dijkstra;

import com.sun.javafx.robot.FXRobotImage;

import java.util.Arrays;

/**
 * 迪杰斯特拉算法
 */
public class DijkstraAlgorithm {

    public static final int INF = 65535;

    public static void main(String[] args) {

        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        //邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;// 表示不可以连接
        matrix[0] = new int[]{N, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, N, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, N, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, N, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, N, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, N, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, N};
        Dijkstra dijkstra = new Dijkstra(vertex, matrix);
        dijkstra.dijkstra(6);

        dijkstra.shopDijkstra();

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
        for (int j = 0; j < matrix[index].length; j++) {
            //出发顶点到index顶点的距离+从index顶点到j顶点的距离的和
            len = vertexDijkstra.getDis(index) + matrix[index][j];
            if (!vertexDijkstra.in(j) && len < vertexDijkstra.getDis(j)) {

                //TODO 更新前置顶点 ，位置不要搞错
                vertexDijkstra.updatePre(j, index);

                //更新目标节点长度
                vertexDijkstra.updateDis(j, len);

            }
        }
    }


    /**
     * 获取下一个最小顶点的长度
     *
     * @return
     */
    public int getMinLengthIndex() {
        //TODO 特别注意这里的写法 取得里面最小的值
        int min =65535, index = -1;
        for (int i = 0; i < vertex.length; i++) {
            if (!vertexDijkstra.in(i) &&  vertexDijkstra.dis[i]<min) {
                min = vertexDijkstra.dis[i];
                index = i;
            }
        }
        vertexDijkstra.already_visited[index] = 1;
        return index;
    }



    public void shopDijkstra() {
        for (int i = 0; i < vertexDijkstra.already_visited.length; i++) {

            System.out.print(vertexDijkstra.already_visited[i] + " ");
        }
        System.out.println();

        for (int i = 0; i < vertexDijkstra.pre.length; i++) {
            System.out.print(vertexDijkstra.pre[i] + " ");
        }
        System.out.println();

        for (int i = 0; i < vertexDijkstra.dis.length; i++) {
            System.out.print(vertexDijkstra.dis[i] + " ");
        }
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
        dis[index] = 0;
        already_visited[index] = 1;

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
     * 更新pre这个顶点的前驱顶点为index顶点。
     *
     * @param index 顶点下标
     * @param j     下标
     */
    public void updatePre(int index, int j) {
        pre[index] = j;
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