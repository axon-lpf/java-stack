package com.axon.java.stack.algorithms.kmp;


import org.apache.poi.hssf.record.chart.FontIndexRecord;

/**
 * 添加暴力匹配算法
 */
public class BruteForceAlgorithm {

    public static void main(String[] args) {

        String str = "ABCD KLKS ABABABCD ";  //原始文本值

        String p = "ABABA";

        int i = bruteForce(str, p);

        System.out.println("最终匹配的位置是"+i);

    }


    public static int  bruteForce(String str,String p) {

        //获取原始的数组
        char[] strChar = str.toCharArray();

        //获取子串的数组
        char[] pChar  = p.toCharArray();

        int  i=0;  //原串的下标
        int  j=0;  // 目标串的下标

        while (i<strChar.length && j<pChar.length) {
            if(strChar[i] == pChar[j]) {
                //如果相等则  i++, j++
                i++;
                j++;

            }else {
                // 这里举个案例进行说明
                // 如果不相当， 则i的位置开始改变， 则j继续等于0， 重新匹配
                // 原串： ABCD KLKS ABABABCD    目标串： ABABA
                // 当i=2 ,j=2 时， 则匹配到
                //  ABCD KLKS ABABABCD   下标为2 时的值为 C
                //  ABABA                下标为2 时的值为 A   所以不匹配
                // 则i=2-(2-1)=1   下标为1 , 则i继续从1 开始匹配 即：
                // ABCD KLKS ABABABCD
                //  ABABA
              //  i=i-(j-1);  //TODO  这个位置很重要。  下次重新匹配的原串位置= 用原串的下标-已匹配的下标+1
                // 为什么是j-1？ , 因为匹配失败之后，要找到原串的下一个位置， 即  i-j+1 ;  而 i-j+1 等价  i-(j-1)
                i=i-j+1;
                j=0;
            }
            if (j==pChar.length) {
                break;
            }
        }

        return  i-j;
    }

}
