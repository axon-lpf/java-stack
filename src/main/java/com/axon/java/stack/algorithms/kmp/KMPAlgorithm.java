package com.axon.java.stack.algorithms.kmp;

import java.util.Arrays;

/**
 * kmp算法
 * <p>
 * 经典博客讲解 https://www.cnblogs.com/zzuuoo666/p/9028287.html
 * <p>
 * String  s="BBC ABCDAB ABCDABCDABDE";  // 这是原串，
 * String  p="ABCDABD";  // 这是模式串
 * <p>
 * 求next的数组
 */
public class KMPAlgorithm {

    public static void main(String[] args) {

        String initStr = "ABABABCVA2ADSA ACAAAAABAACD";
        String targetStr = "AAAAAB";
        int[] kempNext = getKempNext(targetStr);
        System.out.println("对应的next数组是" + Arrays.toString(kempNext));

        int kmpIndex = getKmpIndex(initStr, targetStr, kempNext);

        System.out.println("最终结果是："+kmpIndex);


    }


    /**
     * 通过Kmp算法查找索引
     *
     * @param initStr   原始串
     * @param targetStr 目标串
     * @return 返回对应的下标
     * <p>
     * 步骤 1：初始化
     * 1.	把目标字符串转换为字符数组：char[] charArray = {'A', 'A', 'A', 'B'}。
     * 2.	初始化 next 数组：next = [0, 0, 0, 0]（长度与目标字符串一致）。
     * 3.	设置初始值：
     * •	i = 1（从第 1 个字符开始）。
     * •	j = 0（匹配的前缀长度）。
     * <p>
     * 步骤 2：构建 next 数组
     * 1.	i = 1，比较 charArray[1] == charArray[0]
     * •	相等：j++（匹配长度增加），此时 j = 1。
     * •	更新 next[1] = j = 1。
     * •	结果：next = [0, 1, 0, 0]。
     * 2.	i = 2，比较 charArray[2] == charArray[1]
     * •	相等：j++，此时 j = 2。
     * •	更新 next[2] = j = 2。
     * •	结果：next = [0, 1, 2, 0]。
     * 3.	i = 3，比较 charArray[3] != charArray[2]
     * •	不相等：回退 j = next[j - 1] = next[1] = 1。
     * •	再次比较：charArray[3] != charArray[1]，继续回退 j = next[j - 1] = next[0] = 0。
     * •	最终，next[3] = j = 0。
     * •	结果：next = [0, 1, 2, 0]。
     * <p>
     * 为什么是 j = next[j - 1]？
     * <p>
     * 情境分析：为什么需要回退？
     * <p>
     * 当前匹配失败时，已经匹配的前缀部分有可能可以重用，避免从头开始匹配。
     * <p>
     * 比如：当 i = 3 时
     * 1.	前面的匹配情况：
     * •	主串：AAAB
     * •	子串：AAAB
     * •	匹配到了 charArray[0..2] = "AAA"，但 charArray[3] != charArray[2]。
     * 2.	如何回退？
     * •	前面已经匹配的 charArray[0..2] 的前缀 "AA"，后缀也刚好是 "AA"。
     * •	我们可以直接跳过前缀部分，回退到 j = 1，避免从头再匹配。
     * 3.	再次失败怎么办？
     * •	如果再次匹配失败，则继续回退到 j = next[j - 1]，直到找到合适的前缀或回到开头。
     * <p>
     * <p>
     * 总结：j = next[j - 1]
     * <p>
     * 这条语句的核心作用是：
     * 1.	通过 next[j - 1] 快速找到子串前缀和后缀相等的位置。
     * 2.	避免重复检查已经匹配的部分，从而提升匹配效率。
     * <p>
     * 这就是 KMP 算法能够高效完成匹配的关键！
     */
    public static int getKmpIndex(String initStr, String targetStr, int[] next) {

        //原始串
        char[] initCharArray = initStr.toCharArray();

        //目标串
        char[] charArray = targetStr.toCharArray();

        for (int i = 0, j = 0; i < initCharArray.length; i++) {

            while (j >0 && charArray[j] != initCharArray[i]) {
                j = next[j - 1];
            }

            if (initCharArray[i] == charArray[j]) {
                j++;
            }
            if (j == charArray.length) {
                return i - j;
            }
        }
        return -1;
    }


    /**
     * 获取KMP对应的数组
     *
     * @param targetStr 查询目标数组
     * @return
     */
    public static int[] getKempNext(String targetStr) {

        //目标数组长度
        char[] charArray = targetStr.toCharArray();

        //初始化next
        int[] next = new int[charArray.length];
        next[0] = 0;

        for (int i = 1, j = 0; i < charArray.length; i++) {

            while (j > 0 && charArray[i] != charArray[j]) {
                j = next[j - 1];
            }
            //如果上一个，与本次的相等，则j++;
            if (charArray[i] == charArray[j]) {
                j++;
            }
            // 将j赋值给next对应的下标
            next[i] = j;
        }
        return next;
    }

}
