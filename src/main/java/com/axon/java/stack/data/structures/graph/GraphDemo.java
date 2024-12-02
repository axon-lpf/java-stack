package com.axon.java.stack.data.structures.graph;

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

        System.out.println("图的展示案例1");
        graph.showEdges();

        //深度遍历
        System.out.println("开始深度遍历案例1");
        graph.dfs();

        graph.isVisited=new boolean[vartexs.length];

        System.out.println("开始广度遍历案例1");
        //广度遍历
        graph.bfs();


        //第二个案例

        Graph graph2=new Graph(8);

        String []   vartexs2={"0", "1", "2", "3", "4", "5", "6", "7"};
        for (int i = 0; i < vartexs2.length; i++) {
            graph2.addGraph(vartexs2[i]);
        }
        graph2.addEdges(0,1,1);
        graph2.addEdges(0,2,1);
        graph2.addEdges(1,3,1);
        graph2.addEdges(1,4,1);
        graph2.addEdges(3,7,1);
        graph2.addEdges(4,7,1);
        graph2.addEdges(2,5,1);
        graph2.addEdges(2,6,1);
        graph2.addEdges(5,6,1);

        System.out.println("图的展示案例2");
        graph2.showEdges();

        //深度遍历
        System.out.println("开始深度遍历案例2");
        graph2.dfs();

        graph2.isVisited=new boolean[vartexs2.length];

        System.out.println("开始广度遍历案例2");
        //广度遍历
        graph2.bfs();








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
     *
     * 	0  1  2  3  4  5  6  7
     0 [0, 1, 1, 0, 0, 0, 0, 0]
     1 [1, 0, 0, 1, 1, 0, 0, 0]
     2 [1, 0, 0, 0, 0, 1, 1, 0]
     3 [0, 1, 0, 0, 0, 0, 0, 1]
     4 [0, 1, 0, 0, 0, 0, 0, 1]
     5 [0, 0, 1, 0, 0, 0, 1, 0]
     6 [0, 0, 1, 0, 0, 1, 0, 0]
     7 [0, 0, 0, 1, 1, 0, 0, 0]

     深度遍历的核心逻辑
     1>.以0为开头的查找点，首先进来的是0， 则将0标记已访问
     2>.0的下一个邻节点是1， 将1标记为已访问
     3>.跳到下标为1的行去看，1的下一个邻居节点是3， 则将3标记已访问。
     4>。跳转到第3行，3 的下一个邻节点是1， 1已经被访问，继续找3的下一个邻节点， 下一个邻节点是7， 则将7标记已访问，
     5>. 跳转到第7行，第7行的下一个邻居节点是3， 3 已经被标记为已访问， 继续寻找下一个节点，下一个节点是4，则将4标记为已访问。   0-1-3-7-4
     6>.挑战到第4行， 第四行的下一个邻节点是1， 1 已标记为1访问，在次继续寻找下一个节点是7,7也标记为已访问。 第四行已访问完毕，则回溯。
     7>.回溯到第7行，发现第7行无邻节点。
     8>.回溯到第3行， 第3行也无邻节点。
     9>.回溯到第1行， 在第一行时，发现已无邻节点
     10>回溯到第0行，继续访问0行的下一个节点，是2,将2 标记为已访问 0-1-3-7-4-2-5
     11>跳转到第2行，第二行0 已标记访问， 下一个节点是5， 则将5标记为已访问，  0-1-3-7-4-2-5
     12>跳转到第5行， 5行的下一个节点是2， 2 已标记为已访问， 继续访问下一个节点是6， 6未被访问，则标记已访问，此时访问的节点是  0-1-3-7-4-2-5-6
     13>第5行的下一个邻居节点已经没有了，则回溯，回溯到第2行。
     14> 回溯到第二行时，继续访问下一个接节点，下一个邻节点是6，此时6已被访问。继续向下寻找，没有了，
     15>又回溯到第0行，第0行，下一个节点没有了，则结束。

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
       // System.out.println(vertices+"相邻节点是"+this.getVertices(w));
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
     *
     * 	0  1  2  3  4  5  6  7
     0 [0, 1, 1, 0, 0, 0, 0, 0]
     1 [1, 0, 0, 1, 1, 0, 0, 0]
     2 [1, 0, 0, 0, 0, 1, 1, 0]
     3 [0, 1, 0, 0, 0, 0, 0, 1]
     4 [0, 1, 0, 0, 0, 0, 0, 1]
     5 [0, 0, 1, 0, 0, 0, 1, 0]
     6 [0, 0, 1, 0, 0, 1, 0, 0]
     7 [0, 0, 0, 1, 1, 0, 0, 0]

     广度遍历的核心逻辑
     1>.先从第0行开始遍历，打印出0。 并标记0已访问， 并将0 加入到队列中 ， 0
     2>.获取第队列中第一个节点，第1个节点是0，则0行的下一个邻节点下一个邻节点是1， 将1标记已访问，并将1加入到队列中  0-1
     3>.继续获取第0行的下一个节点，下一个节点是2，将2标记为已访问， 并将2加入到队列中 0-1-2
     4>.继续访0行问下一个邻节点， 下一个邻节点是没有，则本次的while结束。
     5>.继续获取队列中的头节点，头节点是1， 判断1是否已访问，1已经访问，
     6>继续获取1行的下个节点，下个接点是3， 判断3是未访问，则标记已访问。 0-1-2-3, 并将3加入到队列中
     7>继续获取1行的下个节点，下个节点是4， 判断4是未访问的，则标记已访问，  0-1-2-3-4. 并将4加入到队列中
     8>继续获取1行的下个节点， 下个节点是无，返回了-1
     9>.继续获取队列中头节点， 是2，  判断2是已访问，
     10> 2行的下一个节点是1， 1 是已访问，
     11>2 行的下个点是 5， 5是未访问， 将5标记为已访问，并放入到队列中， 则打印的是 0-1-2-3-4-5.
     12>2行的下个节点是6， 6是未访问， 则将6标记已访问，并放入到队列中，  则打印的是 0-1-2-3-4-5-6
     13>继续访问2行的下个节点，下个节点是无，返回-1.
     14>继续去队列的头节点，头节点是3， 3是已访问，跳过
     15>3行的下个节点是1， 1已访问，跳过。
     16>3行的下个节点是7， 7 未被访问，则标记已访问。  并将7加入到队列中 ， 则打印的是0-1-2-3-4-5-6-7
     17>3行下一个节点，访问时无，结束。
     18>.继续去队列的头节点是4， 4行中1和7都被访问， 结束
     19>.继续 5行   6行、7行。
     *
     *
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
