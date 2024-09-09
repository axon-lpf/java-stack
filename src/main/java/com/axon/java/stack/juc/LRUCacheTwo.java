package com.axon.java.stack.juc;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码说明
 *
 * 	1.	LRUCache 类:
 * 	•	capacity: 缓存的容量。
 * 	•	cache: 哈希表，用于快速查找和删除缓存元素。
 * 	•	list: 双向链表，维护元素的访问顺序。
 * 	2.	get 方法:
 * 	•	获取缓存中指定键的值，如果键存在则将对应的节点移动到链表头部。
 * 	3.	put 方法:
 * 	•	添加或更新缓存中的键值对。新元素添加到链表头部，如果缓存已满，则移除链表尾部的元素（最久未使用的元素）。
 * 	4.	Node 类:
 * 	•	双向链表的节点类，包含键值对和前后指针。
 * 	5.	DoublyLinkedList 类:
 * 	•	双向链表实现类，提供节点添加、删除、移动操作。
 *
 * 运行结果
 *
 * 示例代码中，缓存容量为3，初始添加了三个元素。访问某个元素后，将其移动到头部。再添加新元素时，移除链表尾部的元素，保持缓存容量不变。
 *
 * @param <K>
 * @param <V>
 */
public class LRUCacheTwo<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> cache;
    private final DoublyLinkedList<K, V> list;

    public LRUCacheTwo(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.list = new DoublyLinkedList<>();
    }

    public V get(K key) {
        Node<K, V> node = cache.get(key);
        if (node == null) {
            return null; // Key not found
        }
        // Move the accessed node to the head of the list
        list.moveToHead(node);
        return node.value;
    }

    public void put(K key, V value) {
        Node<K, V> node = cache.get(key);
        if (node == null) {
            // Create a new node
            node = new Node<>(key, value);
            cache.put(key, node);
            list.addNode(node);
            if (cache.size() > capacity) {
                // Remove the least recently used node
                Node<K, V> tail = list.popTail();
                cache.remove(tail.key);
            }
        } else {
            // Update the value and move the node to the head
            node.value = value;
            list.moveToHead(node);
        }
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class DoublyLinkedList<K, V> {
        private final Node<K, V> head;
        private final Node<K, V> tail;

        DoublyLinkedList() {
            head = new Node<>(null, null);
            tail = new Node<>(null, null);
            head.next = tail;
            tail.prev = head;
        }

        void addNode(Node<K, V> node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }

        void removeNode(Node<K, V> node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        void moveToHead(Node<K, V> node) {
            removeNode(node);
            addNode(node);
        }

        Node<K, V> popTail() {
            Node<K, V> res = tail.prev;
            removeNode(res);
            return res;
        }
    }

    public static void main(String[] args) {
        LRUCacheTwo<Integer, String> lruCache = new LRUCacheTwo<>(3);
        lruCache.put(1, "one");
        lruCache.put(2, "two");
        lruCache.put(3, "three");
        System.out.println(lruCache.cache.keySet()); // [1, 2, 3]

        lruCache.get(1);
        lruCache.put(4, "four");
        System.out.println(lruCache.cache.keySet()); // [1, 3, 4]

        lruCache.put(5, "five");
        System.out.println(lruCache.cache.keySet()); // [1, 4, 5]
    }
}
