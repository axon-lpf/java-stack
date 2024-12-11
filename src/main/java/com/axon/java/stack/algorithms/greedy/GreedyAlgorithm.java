package com.axon.java.stack.algorithms.greedy;

import java.util.*;

/**
 * 贪心算法
 *
 */
public class GreedyAlgorithm {

    public static void main(String[] args) {

        //定义所有集合
        Map<String, HashSet> map = new HashMap<>();

        HashSet<String> k1Set = new HashSet<>();
        k1Set.add("北京");
        k1Set.add("上海");
        k1Set.add("天津");


        HashSet<String> k2Set = new HashSet<>();
        k2Set.add("广州");
        k2Set.add("北京");
        k2Set.add("深圳");

        HashSet<String> k3Set = new HashSet<>();
        k3Set.add("成都");
        k3Set.add("上海");
        k3Set.add("杭州");

        HashSet<String> k4Set = new HashSet<>();
        k4Set.add("上海");
        k4Set.add("天津");


        HashSet<String> k5Set = new HashSet<>();
        k5Set.add("杭州");
        k5Set.add("大连");

        map.put("k1", k1Set);
        map.put("k2", k2Set);
        map.put("k3", k3Set);
        map.put("k4", k4Set);
        map.put("k5", k5Set);

        List<String> tempAreas = new ArrayList<>();
        map.forEach((k, v) -> tempAreas.addAll(v));

        // 定义所有的区域
        List<String> allAreas = new ArrayList<>(new HashSet<>(tempAreas));

        //用于存储key值
        List<String> select = new ArrayList<>();

        //用于保存命中最大的key
        String maxKey;

        //如果allAreas大于0 ，则继续循环
        while (!allAreas.isEmpty()) {
             maxKey=null;
            //循环获取key中的值，并判断
            for (Map.Entry<String, HashSet> key : map.entrySet()) {

                List<String> tempArea = new ArrayList<>(key.getValue());
                //获取两个的交集
                tempArea.retainAll(allAreas);
                 // 获取的当前的size 大于0 ，  或者当前的size的值大于之前最大key中的数量，则重新赋值maxkey, 这里是找到最优的。
                if (tempArea.size() > 0 && (maxKey == null || tempArea.size() > map.get(maxKey).size())) {
                    maxKey = key.getKey();
                }
            }
            if (maxKey != null) {
                select.add(maxKey);
                allAreas.removeAll(map.get(maxKey));
            }

        }

        System.out.println("最终的结果是"+Arrays.toString(select.toArray()));

    }


}
