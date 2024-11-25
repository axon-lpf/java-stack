package com.axon.java.stack.data.structures.graph;

import java.util.ArrayList;

/**
 * 图的相关结构和理论
 */
public class GraphDemo {

    public static void main(String[] args) {

    }
}

/**
 *  图的结构
 */
class Graph{

    /**
     *  用于存储顶点之间的的关系
     */
    private  int [][] graphArr;

    /**
     *  用于存储各个顶点的值
     */
    private ArrayList<String> graphValue;


    public Graph(int size) {
        graphArr = new int[size][size];
        graphValue = new ArrayList<>();
    }

    /**
     *  添加订单的值
     * @param value  插入的value的值
     */
    public  void  addGraph(String value){
        this.graphValue.add(value);
    }

}
