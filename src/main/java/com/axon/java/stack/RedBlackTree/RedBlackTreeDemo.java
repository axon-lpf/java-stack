package com.axon.java.stack.RedBlackTree;

public class RedBlackTreeDemo {



}



// 节点定义
class Node<K extends Comparable<K>, V> {
    K key;
    V value;
    Node<K, V> left, right, parent;
    boolean isRed; // true for red, false for black

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
        this.isRed = true; // new nodes are always red initially
    }
}


//红黑树实现
class RedBlackTree<K extends Comparable<K>, V> {
    private Node<K, V> root;

    // 左旋
    private void leftRotate(Node<K, V> node) {
        Node<K, V> rightChild = node.right;
        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }
        rightChild.parent = node.parent;
        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }
        rightChild.left = node;
        node.parent = rightChild;
    }

    // 右旋
    private void rightRotate(Node<K, V> node) {
        Node<K, V> leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }
        leftChild.parent = node.parent;
        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.right) {
            node.parent.right = leftChild;
        } else {
            node.parent.left = leftChild;
        }
        leftChild.right = node;
        node.parent = leftChild;
    }

    // 插入
    public void insert(K key, V value) {
        Node<K, V> newNode = new Node<>(key, value);
        root = insertNode(root, newNode);
        fixInsert(newNode); // 修正树的平衡
    }

    // 插入节点
    private Node<K, V> insertNode(Node<K, V> root, Node<K, V> newNode) {
        if (root == null) {
            return newNode;
        }
        if (newNode.key.compareTo(root.key) < 0) {
            root.left = insertNode(root.left, newNode);
            root.left.parent = root;
        } else if (newNode.key.compareTo(root.key) > 0) {
            root.right = insertNode(root.right, newNode);
            root.right.parent = root;
        }
        return root;
    }

    // 插入后修正
    private void fixInsert(Node<K, V> node) {
        while (node != root && node.parent.isRed) {
            if (node.parent == node.parent.parent.left) {
                Node<K, V> uncle = node.parent.parent.right;
                if (uncle != null && uncle.isRed) {
                    node.parent.isRed = false;
                    uncle.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    rightRotate(node.parent.parent);
                }
            } else {
                Node<K, V> uncle = node.parent.parent.left;
                if (uncle != null && uncle.isRed) {
                    node.parent.isRed = false;
                    uncle.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    leftRotate(node.parent.parent);
                }
            }
        }
        root.isRed = false;
    }
}