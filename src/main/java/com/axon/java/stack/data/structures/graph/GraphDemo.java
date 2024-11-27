package com.axon.java.stack.data.structures.graph;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 图的相关结构和理论、
 *
 * 案例图， 图的表示连接矩阵
 *          A   B   C   D   E
 *      A   0   1   1   0   0
 *      B   1   0   1   1   1
 *      C   1   1   0   0   0
 *      D   0   1   0   0   0
 *      E   0   1   0   0   0
 */
public class GraphDemo {

    public static void main(String[] args) {

        Graph graph=new Graph(5);

        String []  vartexs={"A","B","C","D","E"};
        for (int i = 0; i < vartexs.length; i++) {
            graph.addGraph(vartexs[i]);
        }

        //A-B, A-C, B-D, B-E, B-C
        graph.addEdges(0,1,1);
        graph.addEdges(0,2,1);

        graph.addEdges(1,3,1);
        graph.addEdges(1,4,1);
        graph.addEdges(1,2,1);

        graph.showEdges();



    }
}

/**
 *  图的结构
 */
class Graph{

    /**
     *  用于存储顶点之间的的关系
     */
    private  int [][] edges;

    /**
     *  边的数量
     */
    private  int edgeCount;
    /**
     *  用于存储各个顶点的值
     */
    private ArrayList<String> graphValue;


    public Graph(int size) {
        edges = new int[size][size];
        graphValue = new ArrayList<>();
    }

    /**
     *  添加订单的值
     * @param value  插入的value的值
     */
    public  void  addGraph(String value){
        this.graphValue.add(value);
    }

    /**
     *  添加顶点之间的关系
     * @param v1 第一个顶点
     * @param v2 下一个顶点
     * @param weight 权重，  1 表示可以直接连接 ， 0 表示不能连接
     */
    public void addEdges(int v1, int  v2, int weight){
        edges[v1][v2]=weight;
        edges[v2][v1]=weight;
        edgeCount++;
    }

    /**
     *  获取顶点数量
     * @return 返回顶点数量
     */
    public  int  verticesSize(){
        return this.graphValue.size();
    }

    /**
     *  根据下标获取顶点的值
     * @param index 下标，
     * @return  返回对应的顶点
     */
    public  String  getVertices( int index){
        return this.graphValue.get(index);
    }

    /**
     *  获取边的数量
     * @return 返回边的数量
     */
    public  int getEdgeCounts(){
        return edgeCount;
    }

    /**
     *  返回对应顶点的权值
     * @param v1 顶点1
     * @param v2 顶点2
     * @return 权值
     */
    public  int  getWeight(int v1, int v2){
        return edges[v1][v2];
    }

    /**
     *  显示图的关系
     */
    public  void  showEdges(){
        for (int v = 0; v < graphValue.size(); v++) {
            System.out.println(Arrays.toString( this.edges[v]));
        }
    }


}
