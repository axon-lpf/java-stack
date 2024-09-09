package com.axon.java.stack.RedBlackTree;

public class LinkMapCovertReadBlackTree {


    public static void main(String[] args) {
        // Create a sorted linked list
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(6);
        head.next.next.next.next.next.next = new ListNode(7);

        // Build the Red-Black Tree
        RedBlackTreeConvert rbTree = new RedBlackTreeConvert(head);

        // Print the Red-Black Tree
        rbTree.inorderTraversal(rbTree.root);
    }

}


class ListNode {
    int value;
    ListNode next;

    ListNode(int value) {
        this.value = value;
    }
}

class RedBlackTreeNode {
    int value;
    RedBlackTreeNode left, right, parent;
    boolean isRed; // true for red, false for black

    RedBlackTreeNode(int value) {
        this.value = value;
        this.isRed = true; // new nodes are always red initially
    }
}


class RedBlackTreeConvert {
    public RedBlackTreeNode root;

    public RedBlackTreeConvert(ListNode head) {
        this.root = sortedListToRBTree(head);
    }

    private RedBlackTreeNode sortedListToRBTree(ListNode head) {
        // Count the number of nodes in the list
        int size = getSize(head);
        return sortedListToRBTree(head, size);
    }

    private int getSize(ListNode head) {
        int size = 0;
        ListNode current = head;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }

    private RedBlackTreeNode sortedListToRBTree(ListNode head, int size) {
        //	•	当 size 为 0 或负数时，表示没有节点需要处理，因此返回 null。这是递归的结束条件。
        if (size <= 0) {
            return null;
        }

        // Recursively build the left subtree

        //	•	递归构建左子树。size / 2 表示左子树的节点数（大约一半），这样可以确保左右子树的大小大致平衡。
        //	•	在递归调用中，head 不变，因此左子树的构建从链表的当前 head 开始。
        RedBlackTreeNode left = sortedListToRBTree(head, size / 2);

        // Build the root node
        //•	 创建一个新 RedBlackTreeNode 实例，值为链表当前节点的值。
        //	•将构建好的左子树（如果有）设置为当前节点的左子树。
        RedBlackTreeNode root = new RedBlackTreeNode(head.value);
        root.left = left;

        // Move to the next list node
        //如果左子树不为空，则链表的 head 移动到下一个节点。这样 head 就指向了右子树的起始位置。
        if (left != null) {
            head = head.next;
        }

        // Build the right subtree
        //•	递归构建右子树。head.next 是右子树的起始位置，size - size / 2 - 1 是右子树的大小。
        // 这里减去 size / 2 + 1 是因为已经处理了当前节点和左子树节点。
        root.right = sortedListToRBTree(head.next, size - size / 2 - 1);
        return root;
    }

    // Method to print tree nodes in inorder traversal
    public void inorderTraversal(RedBlackTreeNode node) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.left);
        System.out.print(node.value + " ");
        inorderTraversal(node.right);
    }
}