package com.axon.java.stack.algorithms.prim;

import lombok.Data;

import java.util.Arrays;

/**
 *  普利姆算法
 */
public class PrimAlgorithm {


    public static void main(String[] args) {

    }
}


/**
 *  最小生成树
 */
class  MinTree{

    private Graph graph;

    public MinTree(Graph graph) {
        this.graph = graph;
    }


    /**
     *  创建最小生成树
     * @param vertex
     * @param data
     * @param weights
     */
    public  void  createMinTree(int vertex,char[] data, int [][] weights){
        this.graph.setVertex(vertex);
        for (int i = 0; i < vertex; i++) {
            this.graph.setData(data[i]);
            for (int j = 0; j < vertex; j++) {
                this.graph.getWeights()[i][j]=weights[i][j];
            }

        }
    }

    /**
     *  显示最小生成树
     */
    public  void  showMinTree(){
        int[][] weights = this.graph.getWeights();
        for (int [] line : weights) {
            System.out.println(Arrays.toString(line));
        }
    }


    public  void  prim( int vertex){

        int [] visited = new int[this.graph.getVertex()];

        for (int k = 1; k < this.graph.getVertex(); k++) {

            for (int i = 0; i < this.graph.getVertex(); i++) {

                for (int j = 0; j < this.graph.getVertex(); j++) {

                    if (visited[i]==1 && visited[j]==0 && this.graph.getWeights()[i][j]<10000) {}
                }
            }

        }
    }

}


/**
 *  图截图
 */
@Data
class Graph{

    private  char [] data;

    private int [] [] weights;

    private  int vertex;


    public Graph(int vertex){
        this.vertex = vertex;
        data = new char[vertex];
        this.weights=new int[vertex][ vertex];
    }

}
