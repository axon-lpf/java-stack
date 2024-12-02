package com.axon.java.stack.algorithms.divide.conquer;


/**
 *  汉诺塔问题处理-使用的是分治算法
 *
 *  这是分治算法的一个体现。
 */
public class TowerOfHanoiDemo {

    public static void main(String[] args) {

        towerOfHano(5,'A','B','C');
    }


    /**
     *  汉诺塔游戏
     *   分成两部分， 第一层上面的所有部分，  和第一层
     *   A-B
     *   A-C
     *   B-C
     *
     *
     * @param number
     * @param a
     * @param b
     * @param c
     */
    public  static  void  towerOfHano(int number, char a , char b, char c) {
        if (number==1){
            System.out.println("第1个盘从"+a+"------->"+c);
        }else {
            //这里首先处理A-C
            towerOfHano(number-1, a, c, b);
            //这里打印A-C
            System.out.println("第"+number+"个盘从"+a+"------->"+c);
            towerOfHano(number-1, b, a, c);
        }
    }
}
