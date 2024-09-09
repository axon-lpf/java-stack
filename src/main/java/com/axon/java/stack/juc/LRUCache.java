package com.axon.java.stack.juc;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * /**
 *  * LRU（Least Recently Used）算法是一种常见的缓存淘汰策略，用于在缓存已满时，移除最近最少使用的项目。下面是使用Java实现一个简单的LRU缓存示例，利用LinkedHashMap来实现LRU的特性。
 *  *
 *  * 代码解释
 *  *
 *  * 	1.	LRUCache 类: 这个类定义了一个LRU缓存，其中capacity表示缓存的最大容量，cache是一个内部的LinkedHashMap，用于存储缓存的数据。
 *  * 	2.	构造方法:
 *  * 	•	LRUCache(int capacity): 初始化缓存，指定缓存的最大容量。使用LinkedHashMap时，将accessOrder参数设置为true，以便按照访问顺序维护键值对。
 *  * 	3.	get 方法:
 *  * 	•	public V get(K key): 获取指定键的值。如果键存在于缓存中，则返回对应的值；否则返回null。
 *  * 	4.	put 方法:
 *  * 	•	public void put(K key, V value): 向缓存中添加一个键值对。如果缓存已满，则会自动移除最久未使用的键值对。
 *  * 	5.	remove 方法:
 *  * 	•	public void remove(K key): 从缓存中移除指定的键值对。
 *  * 	6.	removeEldestEntry 方法:
 *  * 	•	protected boolean removeEldestEntry(Map.Entry<K, V> eldest): 当LinkedHashMap的大小超过指定的容量时，该方法返回true，自动移除最久未使用的键值对。
 *  *
 *  * 运行结果
 *  *
 *  * 该示例展示了LRU缓存的工作方式。最后一个访问（获取或放入）的元素将移到链表的末尾，当新元素加入缓存且缓存已满时，将移除链表头部的最久未使用的元素。
 *  * @param <K>
 *  * @param <V>
 *  */

public class LRUCache<K, V> {
    private final int capacity;
    private final Map<K, V> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > LRUCache.this.capacity;
            }
        };
    }

    public V get(K key) {
        return cache.getOrDefault(key, null);
    }

    public void put(K key, V value) {
        cache.put(key, value);
    }

    public void remove(K key) {
        cache.remove(key);
    }

    public static void main(String[] args) {
        LRUCache<Integer, String> lruCache = new LRUCache<>(3);
        lruCache.put(1, "one");
        lruCache.put(2, "two");
        lruCache.put(3, "three");
        System.out.println(lruCache.cache); // {1=one, 2=two, 3=three}

        lruCache.get(1);
        lruCache.put(4, "four");
        System.out.println(lruCache.cache); // {3=three, 1=one, 4=four}

        lruCache.put(5, "five");
        System.out.println(lruCache.cache); // {1=one, 4=four, 5=five}
    }
}