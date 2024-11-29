package com.axon.java.stack.data.structures.graph;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

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

        //深度遍历
        System.out.println("开始深度遍历");
        graph.dfs();

        graph.isVisited=new boolean[vartexs.length];

        System.out.println("开始广度遍历");
        //广度遍历
        graph.bfs();



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
    public   int edgeCount;
    /**
     *  用于存储各个顶点的值
     */
    public ArrayList<String> graphValue;

    /**
     *  是否被访问过
     */
    public   boolean [] isVisited;


    public Graph(int size) {
        edges = new int[size][size];
        graphValue = new ArrayList<>();
        isVisited =new boolean[size];
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


    /**
     *  获取第一个相邻顶点
     * @param index 下标位置
     * @return
     */
    public  int  getFirstVertices(int index){
        for (int i = 0; i < this.graphValue.size(); i++) {

            if (this.edges[index][i]>0){
                return i;
            }
        }
        return -1;
    }

    /**
     *   查询下一个相邻顶点
     * @param v1Index  第一个顶点的位置
     * @param v2Index  第二个顶点的位置
     * @return
     */
    public  int getNextVertices(int v1Index, int v2Index){
        for (int i = v2Index+1; i < edges.length ; i++) {
            if (edges[v1Index][i]>0){
                return i;
            }
        }
        return -1;
    }

    /**
     *  深度优先遍历
     *
     *       *            A   B   C   D   E
     *      *  *      A   0   1   1   0   0
     *      *  *      B   1   0   1   1   1
     *      *  *      C   1   1   0   0   0
     *      *  *      D   0   1   0   0   0
     *      *  *      E   0   1   0   0   0
     *
     *
     *  以下为代码运行的流程（按邻接矩阵和递归顺序）：
     * 	1.	入口调用：dfs()
     * 	•	遍历 isVisited，从第一个未访问的节点（A）开始调用 dfs(isVisited, 0)。
     * 	2.	递归过程：
     * 	•	dfs(isVisited, 0)：
     * 	•	输出：当前节点被访问A。
     * 	•	标记 A 为已访问。
     * 	•	找到 A 的第一个相邻节点 B（下标 1）。
     * 	•	递归调用：dfs(isVisited, 1)。
     * 	•	dfs(isVisited, 1)：
     * 	•	输出：当前节点被访问B。
     * 	•	标记 B 为已访问。
     * 	•	找到 B 的第一个相邻节点 A，但已访问，继续找下一个相邻节点 C。
     * 	•	递归调用：dfs(isVisited, 2)。
     * 	•	dfs(isVisited, 2)：
     * 	•	输出：当前节点被访问C。
     * 	•	标记 C 为已访问。
     * 	•	找到 C 的第一个相邻节点 A，但已访问，继续找下一个相邻节点 B，但已访问。
     * 	•	没有其他未访问的相邻节点，递归结束返回上一层。
     * 	•	回到 dfs(isVisited, 1)：
     * 	•	继续寻找 B 的下一个相邻节点 D。
     * 	•	递归调用：dfs(isVisited, 3)。
     * 	•	dfs(isVisited, 3)：
     * 	•	输出：当前节点被访问D。
     * 	•	标记 D 为已访问。
     * 	•	找到 D 的相邻节点 B，但已访问。
     * 	•	没有其他未访问的相邻节点，递归结束返回上一层。
     * 	•	回到 dfs(isVisited, 1)：
     * 	•	继续寻找 B 的下一个相邻节点 E。
     * 	•	递归调用：dfs(isVisited, 4)。
     * 	•	dfs(isVisited, 4)：
     * 	•	输出：当前节点被访问E。
     * 	•	标记 E 为已访问。
     * 	•	找到 E 的相邻节点 B，但已访问。
     * 	•	没有其他未访问的相邻节点，递归结束返回上一层。
     * 	3.	递归结束，回到 dfs() 主循环：
     * 	•	所有节点已访问，深度优先遍历完成。
     *
     * 	Depth-First Search
     */
    public  void dfs(){

        for (int i = 0; i < isVisited.length; i++) {
            if (!isVisited[i]) {
                dfs(isVisited,i);
            }
        }
    }


    /**
     *
     *  * 案例图， 图的表示连接矩阵
     *  *          A   B   C   D   E
     *  *      A   0   1   1   0   0
     *  *      B   1   0   1   1   1
     *  *      C   1   1   0   0   0
     *  *      D   0   1   0   0   0
     *  *      E   0   1   0   0   0
     *
     *
     *
     *
     * @param isVisited
     * @param i
     */
    public  void  dfs(boolean [] isVisited, int i){
        String vertices = this.getVertices(i);
        System.out.println("当前节点被访问"+vertices);
        this.isVisited[i]=true;

        //获取与他相邻的第一个顶点
        int w = this.getFirstVertices(i);
        System.out.println(vertices+"相邻节点是"+this.getVertices(w));
        while (w != -1){
            if (!isVisited[w]){
                dfs(isVisited,w);
            }
            //否则的话获取下一个顶点
            w = this.getNextVertices(i,w);
        }

    }

    /**
     *  广度遍历
     */
    public  void  bfs(){
        for (int i = 0; i < isVisited.length; i++) {

            if (!isVisited[i]) {
                bfs(isVisited,i);
            }
        }
    }

    /**
     *  广度遍历 - Breadth-First Search
     * @param isVisited
     * @param i
     */
    public  void  bfs(boolean [] isVisited, int i){
        int u;
        int w;
        String vertices = this.getVertices(i);
        System.out.println("当前节点"+vertices);
        isVisited[i]=true;
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addLast(i);

        while (!queue.isEmpty()){
            //获取头节点
            u = queue.removeFirst();
            //获取第一个相邻的节点
            w = this.getFirstVertices(u);

            while (w != -1){
                //判断当前节点是否有别访问过
                if (!isVisited[w]){
                    System.out.println("当前节点"+this.getVertices(w));
                    isVisited[w]=true;
                    queue.addLast(w);
                }
                //获取下一个相邻的节点
                w = this.getNextVertices(u,w);
            }

        }


    }

}
