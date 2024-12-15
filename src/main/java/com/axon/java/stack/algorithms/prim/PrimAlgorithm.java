package com.axon.java.stack.algorithms.prim;

import lombok.Data;

import java.util.Arrays;

/**
 * 普利姆算法
 */
public class PrimAlgorithm {


    public static void main(String[] args) {

        int[][] weight = new int[][]{
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 6},
                {2, 3, 10000, 10000, 4, 6, 10000}};

        char[] data = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        MinTree minTree = new MinTree();
        Graph graph = new Graph(data.length);
        minTree.createMinTree(graph, data.length, data, weight);

        minTree.showMinTree(graph);

        minTree.prim(graph, 1);
    }


}


/**
 * 最小生成树
 */
class MinTree {
    /**
     * 创建最小生成树
     *
     * @param vertex
     * @param data
     * @param weights
     */
    public void createMinTree(Graph graph, int vertex, char[] data, int[][] weights) {
        graph.vertex = vertex;
        graph.data = new char[vertex];
        for (int i = 0; i < vertex; i++) {
            graph.data[i] = data[i];
            for (int j = 0; j < vertex; j++) {
                graph.weights[i][j] = weights[i][j];
            }

        }
    }

    /**
     * 显示最小生成树
     */
    public void showMinTree(Graph graph) {
        int[][] weights = graph.weights;
        for (int[] line : weights) {
            System.out.println(Arrays.toString(line));
        }
    }


    /**
     * 普利姆算法
     *
     * @param graph
     * @param vertex
     */
    public void prim(Graph graph, int vertex) {

        int h1 = -1;
        int h2 = -1;
        int minWeight = 10000; // 将minWeight赋值一个最大数，在后续中会被替换

        int[] visited = new int[graph.vertex];

        visited[vertex] = 1;

        //因为有graph.vertex-1个顶点，普利姆算法结束之后，有graph.vertex-1个边
        for (int k = 1; k < graph.vertex; k++) {

            for (int i = 0; i < graph.vertex; i++) { //i 表示被访问过的节点

                for (int j = 0; j < graph.vertex; j++) { // j表示还没有被访问过的节点

                    if (visited[i] == 1 && visited[j] == 0 && graph.weights[i][j] < minWeight) {
                        //替换minWeight，寻找未访问过的节点和已访问节点的最小值。
                        minWeight = graph.weights[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            System.out.println("边>>" + graph.data[h1] + "," + graph.data[h2] + "，权值" + minWeight);
            visited[h2] = 1;
            minWeight = 10000;
        }
    }

}


/**
 * 图截图
 */
class Graph {

    public char[] data;

    public int[][] weights;

    public int vertex;


    public Graph(int vertex) {
        this.vertex = vertex;
        data = new char[vertex];
        this.weights = new int[vertex][vertex];
    }

}
