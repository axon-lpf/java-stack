package com.axon.java.stack.algorithms.dynamic.programming;

import java.util.Arrays;

/**
 *  动态规划算法
 *
 *  背包问题解决思路:
 *  背包问题是动态规划的经典案例，图解能更直观地展示其计算过程。以下以0/1 背包问题为例，通过图表和表格逐步说明其计算过程。
 *
 *  问题描述
 *
 * 输入：
 * 	•	物品重量： weights = {2, 3, 4, 5}
 * 	•	物品价值： values = {3, 4, 5, 6}
 * 	•	背包容量： capacity = 8
 *
 * 目标：
 * 在不超过背包容量的前提下，选择物品使总价值最大。
 *
 * 动态规划表的构造
 *
 * 我们构建一个二维动态规划表  dp[i][j] ，其中：
 * 	•	 i  表示前  i  个物品。
 * 	•	 j  表示当前背包容量。
 *
 * 表格初始化
 *
 * 	•	**行数：**物品数量 + 1（考虑到第 0 行代表没有物品）。
 * 	•	**列数：**背包容量 + 1（容量从 0 到  C ）。
 * 	•	初始值： dp[0][j] = 0 （没有物品时，价值为 0）。
 *
 * 物品数\容量	0	1	2	3	4	5	6	7	8
 *      0	    0	0	0	0	0	0	0	0	0
 *      1
 *      2
 *      3
 *      4
 *
 * 填表过程
 *
 * 逐步计算每个  dp[i][j]  的值，遵循状态转移方程：
 *
 * 核心公式
 * dp[i][j]= dp[i-1][j]
 *           max(dp[i-1][j],dp[i-1][j-w[i]]+v[i])
 *           j<w[i]
 *           j>=w[i]
 *  * 输入：
 *  * 	•	物品重量： weights = {2, 3, 4, 5}
 *  * 	•	物品价值： values = {3, 4, 5, 6}
 *  * 	•	背包容量： capacity = 8
 *
 * 步骤 1：处理物品 1
 *
 * 物品 1：重量  w[1] = 2 ，价值  v[1] = 3 。
 * 	•	背包容量  j = 0  到  1 ：装不下物品 1， dp[1][j] = dp[0][j] 。
 * 	•	背包容量  j >=2 ：可以装下物品 1，价值更新为  dp[1][j] = max(dp[0][j], dp[0][j-2] + 3) 。
 *
 * 结果表：
 * 物品数\容量	0	1	2	3	4	5	6	7	8
 *      0	    0	0	0	0	0	0	0	0	0
 *      1       0   0   3   3   3   3   3   3   3
 *      2
 *      3
 *      4
 *
 * 步骤 2：处理物品 2
 *
 * 物品 2：重量  w[2] = 3 ，价值  v[2] = 4 。
 * 	•	背包容量  j = 0  到  2 ：装不下物品 2， dp[2][j] = dp[1][j] 。
 * 	•	背包容量  j>= 3 ：可以装下物品 2，价值更新为  dp[2][j] = max(dp[1][j], dp[1][j-3] + 4) 。
 *
 *
 * 	 * 输入：
 *  * 	•	物品重量： weights = {2, 3, 4, 5}
 *  * 	•	物品价值： values = {3, 4, 5, 6}
 *  * 	•	背包容量： capacity = 8
 *
 *
 * 结果表：
 * 物品数\容量	0	1	2	3	4	5	6	7	8
 *      0	    0	0	0	0	0	0	0	0	0
 *      1       0   0   3   3   3   3   3   3   3
 *      2       0   0   3   4   4   7   7   7   7
 *      3
 *      4
 *
 *步骤 3：处理物品 3
 *
 * 物品 3：重量  w[3] = 4 ，价值  v[3] = 5 。
 * 	•	背包容量  j = 0  到  3 ：装不下物品 3， dp[3][j] = dp[2][j] 。
 * 	•	背包容量  j>= 4 ：可以装下物品 3，价值更新为  dp[3][j] = max(dp[2][j], dp[2][j-4] + 5) 。
 *
 *
 * 	 * 输入：
 *  * 	•	物品重量： weights = {2, 3, 4, 5}
 *  * 	•	物品价值： values = {3, 4, 5, 6}
 *  * 	•	背包容量： capacity = 8
 *
 * 结果表：
 * 物品数\容量	0	1	2	3	4	5	6	7	8
 *      0	    0	0	0	0	0	0	0	0	0
 *      1       0   0   3   3   3   3   3   3   3
 *      2       0   0   3   4   4   7   7   7   7
 *      3       0   0   3   4   5   7   8   9   9
 *      4
 *
 *
 步骤 4：处理物品 4
 物品 4：重量  w[4] = 5 ，价值  v[4] = 6 。
 •	背包容量  j = 0  到  4 ：装不下物品 4， dp[4][j] = dp[3][j] 。
 •	背包容量  j >= 5 ：可以装下物品 4，价值更新为  dp[4][j] = max(dp[3][j], dp[3][j-5] + 6) 。

 * 输入：
 * 	•	物品重量： weights = {2, 3, 4, 5}
 * 	•	物品价值： values = {3, 4, 5, 6}
 * 	•	背包容量： capacity = 8

 最终结果表：

 * 物品数\容量	0	1	2	3	4	5	6	7	8
 *      0	    0	0	0	0	0	0	0	0	0
 *      1       0   0   3   3   3   3   3   3   3
 *      2       0   0   3   4   4   7   7   7   7
 *      3       0   0   3   4   5   7   8   9   9
 *      4       0   0   3   4   5   7   8   9   10
 *

 */
public class DynamicProgrammingAlgorithm {

    public static void main(String[] args) {
        int []	weights = {2, 3, 4, 5} ;
	    int [] values = {3, 4, 5, 6};
	    int capacity = 8;
        int[][] knapsack = knapsack(weights, values, capacity);
        System.out.println("第一种方法");
        knapsack2Show(knapsack);
        int knapsac2 = knapsack2(weights, values, capacity);
       System.out.println("当前结果值"+knapsac2);


        //第二种方法
        knapsack2();


    }


    /**
     *  核心公式：  Math.max(dp[i-1][j],values2[i-1]+dp[i-1][j-weights2[i-1]])
     *
     *  物品和容量使用二维数组展示
     * [0,   0,    0,    0,    0 ]
     * [0, 1500, 1500, 1500, 1500]
     * [0, 1500, 1500, 1500, 3000]
     * [0, 1500, 1500, 2000, 3500]
     */
    public  static void knapsack2() {

        //每个物品的重量，单位kg
        int [] weights2={1,4,3};
        // 物品对应的价值
        int [] values2={1500,3000,2000};
        //容量，背包最大能装多少重量的物品
        int capacity2=4;

        // 初始化一个数组
        // 由于行和列需要包含下标为0 的初始值，
        int [][] dp = new int [values2.length+1][capacity2+1];

        // 用于记录装入的路径
        int [] [] path = new int [values2.length+1][capacity2+1];

        //打印出初始数组
        knapsack2Show(dp);

        for (int i = 1; i < dp.length; i++) {  // 这里对应的是第几个物品，
            for (int j = 1; j < dp[0].length; j++) {  //这里是对应的列，指定的容量， 容量是动态变化的

                if (weights2[i-1]>j){ //由于循环下标是从1开始的，所以这里需要减去1， //如果当前物品的重量大于当前背包的容量, 则取上一个的容量下标对应的价值, 即容量不足。
                    dp[i][j] = dp[i-1][j]; //将上一个物品的价值，赋值给当前物品
                }else {  //当前物品的容量小于背包的容量
                   // Math.max(dp[i-1][j],values2[i-1]+dp[i-1][j-weights2[i-1]]); 以下的代码是对该句代码的转换
//                    if (dp[i-1][j]<(values2[i-1]+dp[i-1][j-weights2[i-1]])){  //上一个物品价值小于 当前（物品的价值+
//                        dp[i][j]=values2[i-1]+dp[i][j-weights2[i-1]];
//                    }else {
//                        dp[i][j] = dp[i-1][j];
//                    }
                    //以下代码，则是对 上面这部分代码的转换
                    int currentValue = values2[i - 1]; //当前物品的价值
                    int currentWeight = weights2[i - 1]; //当前物品的重量
                    int remaining=  j-currentWeight; //装入当前物品后，还剩下多少的容量，主要是用剩下的这个容量去匹配上一个对应的价值
                    int preValue= dp[i-1][remaining];  //剩余容量对应上一个物品的价值
                    int totalCurrentVale=currentValue+preValue;   //当前容量对应的总价值

                    if (totalCurrentVale>dp[i-1][j]){  //如果加起来的总价值大于上一个物品下相同容量下的总价值，则取当前的、
                        dp[i][j] = totalCurrentVale;
                        path[i][j]=1; // 用于记录路径， 这里是最优的方案
                    }else {
                        dp[i][j] = dp[i-1][j]; //否则用上一个物品对应的容量的价值
                    }
                }
                System.out.println("第"+i+"个物品，第"+j+"容量后，显示");
                knapsack2Show(dp);

            }

        }

        System.out.println("填表之后的结果");
        knapsack2Show(dp);

        System.out.println("开始打印路径");

        int pi=path.length-1;
        int pj=path[0].length-1;

        while (pi>0 && pj>0){

            if (path[pi][pj]==1){
                System.out.println("装入第"+pi+"个物品");
                pj=pj-weights2[pi-1];
            }
            pi=pi-1;
        }



    }

    public  static  void  knapsack2Show(int [][] dp){
        for (int i = 0; i < dp.length; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
    }


    /**
     *   第一种方法
     * @param weights
     * @param values
     * @param capacity
     * @return
     */
    public static int [] [] knapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (j < weights[i - 1]) {
                    dp[i][j] = dp[i - 1][j]; // 容量不足，不能选
                } else {
                   int  x=  dp[i - 1][j];
                   int y= dp[i - 1][j - weights[i - 1]];
                   int value=values[i - 1];
                    dp[i][j] = Math.max(x,y + value); // 选或不选
                }
            }
        }
        return dp;
    }


    public static int knapsack2(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (j < weights[i - 1]) {
                    dp[i][j] = dp[i - 1][j]; // 容量不足，不能选
                } else {
                    int  x=  dp[i - 1][j];
                    int y= dp[i - 1][j - weights[i - 1]];
                    int value=values[i - 1];
                    dp[i][j] = Math.max(x,y + value); // 选或不选
                }
            }
        }
        return dp[n][capacity];
    }
}
