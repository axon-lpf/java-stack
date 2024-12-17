package com.axon.java.stack.algorithms.kruskal;

/**
 * 克鲁斯卡尔算法.txt
 * 解决公交车问题
 * <p>
 * 克鲁斯卡尔算法介绍
 * 1)克鲁斯卡尔(Kruskal)算法，是用来求加权连通图的最小生成树的算法。（最小生成树也可以通过普里姆算法生成，具体可参考算法其实很简单—普利姆算法）
 * <p>
 * 2)基本思想:按照权值从小到大的顺序选择n-1条边，并保证这n-1条边不构成回路
 * <p>
 * 3)具体做法:首先构造一一个只含n个顶点的森林，然后依权值从小到大从连通网中选择边加入到森林中，并使森林中不产生回路，直至森林变成一棵树为止
 * <p>
 * <p>
 * 1)某城市新增7个站点(A,B,C,D,E,F,G)， 现在需要修路把7个站点连通
 * <p>
 * 2)各个站点的距离用边线表示(权)，比如A-B距离12公里
 * <p>
 * 3) 问:如何修路保证各个站点都能连通，并且总的修建公路总里程最短?
 * <p>
 * 参考博客地址: https://blog.csdn.net/guozhangjie1992/article/details/106821932
 * <p>
 * <p>
 * 核心步骤：
 * 1.构建邻结矩阵
 * 2.构建一个边的集合，   EData { start, end, weight }
 * 3.将这个集合按照从小到大顺序排列
 * 4.添加获取顶点点下标的方法
 * 5.获取顶点下标的终点的方法
 * 6.判断两者的终点是否一致， 不一致，则加入返回结果中去。
 */
public class KruskalAlgorithm {
    //最大常量值
    private static final Integer INF = Integer.MAX_VALUE;

    public static void main(String[] args) {

        char[] data = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
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

        Kruskal kruskal = new Kruskal(data, matrix);

        kruskal.print();

        //获取eData数组
        EData[] edata = kruskal.getEdata();
        //排序
        kruskal.sortEData(edata);

        for (int i = 0; i < edata.length; i++) {
            System.out.println(edata[i]);
        }
        // 处理最小生成树
        kruskal.minTreeResult();

    }
}

class Kruskal {

    //顶点数组
    char[] vertex;

    //邻结举证
    int[][] matrix;

    //边的数量
    int edgesNumber = 0;

    //最大常量值
    private static final Integer INF = Integer.MAX_VALUE;


    /**
     * 构造器初始化具体的函数
     *
     * @param vertex 顶点数量
     * @param matrix 邻结矩阵
     */
    public Kruskal(char[] vertex, int[][] matrix) {
        int vertexLength = vertex.length;
        this.vertex = new char[vertexLength]; //初始化
        this.matrix = new int[vertexLength][vertexLength]; //初始化
        //初始化顶点和邻结矩阵
        for (int i = 0; i < vertexLength; i++) {
            this.vertex[i] = vertex[i];
            for (int j = 0; j < vertexLength; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }

        //求当前边的数量
        for (int i = 0; i < vertexLength; i++) {
            for (int j = i + 1; j < vertexLength; j++) {
                if (this.matrix[i][j] != INF) {
                    edgesNumber++;
                }
            }
        }
    }


    /**
     * 打印邻结举证
     */
    public void print() {

        for (int i = 0; i < vertex.length; i++) {
            for (int j = 0; j < vertex.length; j++) {
                System.out.printf("%12d", matrix[i][j]);

                // System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 构建EData数据
     *
     * @return
     */
    public EData[] getEdata() {

        int index = 0;
        EData[] eData = new EData[edgesNumber];

        for (int i = 0; i < vertex.length; i++) {
            for (int j = i + 1; j < vertex.length; j++) {
                if (matrix[i][j] != INF) {
                    eData[index++] = new EData(vertex[i], vertex[j], matrix[i][j]);
                }
            }
        }
        return eData;
    }


    /**
     * 冒泡排序
     *
     * @param eData
     */
    public void sortEData(EData[] eData) {

        for (int i = 0; i < eData.length - 1; i++) {
            for (int j = 0; j < eData.length - 1 - i; j++) {
                if (eData[j].weight > eData[j + 1].weight) {
                    EData temp = eData[j + 1];
                    eData[j + 1] = eData[j];
                    eData[j] = temp;
                }

            }
        }
    }

    /**
     * 找到字符 对应的顶点的下标位置
     *
     * @param c
     * @return
     */
    public int getPosition(char c) {
        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i] == c) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取当前顶点的终点顶点的位置
     *
     * @param edges
     * @param i
     * @return
     */
    public int getEdges(int[] edges, int i) {
        while (edges[i] != 0) {
            i = edges[i];
        }
        return i;
    }


    public EData[] minTreeResult() {

        //获取原数据
        EData[] edata = getEdata();

        EData[] result = new EData[edgesNumber];
        //排序
        this.sortEData(edata);

        int[] edges = new int[edgesNumber];

        //记录执行的结果索引
        int index = 0;

        for (int i = 0; i < edata.length; i++) {
            EData tempEdata = edata[i];

            int p1 = getPosition(tempEdata.start);
            int p2 = getPosition(tempEdata.end);

            int m = this.getEdges(edges, p1);

            int n = this.getEdges(edges, p2);

            if (m != n) {
                edges[m] = n;
                result[index++] = tempEdata;
            }
        }

        System.out.println("最小生成树结果");
        for (int i = 0; i < index; i++) {
            System.out.println(result[i]);

        }
        return result;
    }

}

class EData {

    char start;

    char end;

    int weight;

    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }


    @Override
    public String toString() {
        return "EData{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                '}';
    }


}