package com.axon.java.stack.data.structures.binary.tree;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 剑兰
 * 星火        浮生
 * 安迪    愚公  凌霄
 * <p>
 * 二叉树的处理
 */
public class BinaryTreeDemo {

    public static void main(String[] args) {

        TreeNode root = new TreeNode(1, "剑兰");
        TreeNode treeNode1 = new TreeNode(2, "星火");
        TreeNode treeNode2 = new TreeNode(3, "浮生");
        TreeNode treeNode3 = new TreeNode(4, "安迪");
        TreeNode treeNode4 = new TreeNode(5, "愚公");
        TreeNode treeNode5 = new TreeNode(7, "凌霄");

        root.setLeft(treeNode1);
        root.setRight(treeNode2);
        treeNode1.setLeft(treeNode3);
        treeNode1.setRight(treeNode4);
        treeNode2.setLeft(treeNode5);


        BinaryTree binaryTree = new BinaryTree(root);
        System.out.println("开始前序遍历");
        binaryTree.preOrder();

        System.out.println("开始中序遍历");
        binaryTree.inOrder();

        System.out.println("开始后序遍历");
        binaryTree.postOrder();

        //前缀查找
        TreeNode treeNode = binaryTree.preOrderSearch(5);
        System.out.println("前序查找结果" + treeNode);

        treeNode = binaryTree.inOrderSearch(4);
        System.out.println("中序查找结果" + treeNode);

        treeNode = binaryTree.postOrderSearch(3);
        System.out.println("后序查找结果" + treeNode);


        System.out.println("删除前开始前序遍历");
        binaryTree.preOrder();

        binaryTree.delNode(5);

        System.out.println("删除后开始前序遍历");
        binaryTree.preOrder();


    }
}

class BinaryTree {

    private TreeNode root;

    public BinaryTree(TreeNode root) {
        this.root = root;
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        if (root == null) {
            System.out.println("当前树节点为空");
            return;
        }
        this.root.preOrder();

    }

    /**
     * 中序遍历
     */
    public void inOrder() {
        if (root == null) {
            System.out.println("当前树节点为空");
            return;
        }
        this.root.inOrder();
    }

    /**
     * 后序遍历
     */
    public void postOrder() {

        if (root == null) {
            System.out.println("当前树节点为空");
            return;
        }
        this.root.postOrder();
    }


    /**
     * 前序遍历查找
     */
    public TreeNode preOrderSearch(int id) {
        if (root == null) {
            System.out.println("当前树节点为空");
            return null;
        }
        return this.root.preOrderSearch(id);

    }

    /**
     * 中序遍历
     */
    public TreeNode inOrderSearch(int id) {
        if (root == null) {
            System.out.println("当前树节点为空");
            return null;
        }
        return this.root.inOrderSearch(id);
    }

    /**
     * 这里去删除节点
     *
     * @param id
     */
    public void delNode(int id) {
        if (root == null) {
            System.out.println("当前节点为空");
            return;
        }
        if (this.root.getId() == id) {
            this.root = null;
        } else {
            // 去删除节点
            this.root.delNode(id);
        }

    }

    /**
     * 后序遍历
     */
    public TreeNode postOrderSearch(int id) {

        if (root == null) {
            System.out.println("当前树节点为空");
            return null;
        }
        return this.root.postOrderSearch(id);
    }
}


@Data
class TreeNode {

    public TreeNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;

    private String name;

    private TreeNode left;

    private TreeNode right;


    @Override
    public String toString() {
        return "TreeNode{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }


    /**
     * 前序遍历
     */
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    /**
     * 中序遍历
     */
    public void inOrder() {
        if (this.left != null) {
            this.left.inOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.inOrder();
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }
        if (this.right != null) {
            this.right.postOrder();
        }
        System.out.println(this);
    }


    /**
     * 前序遍历查找
     */
    public TreeNode preOrderSearch(int id) {
        if (this.id == id) {
            return this;
        }
        //System.out.println(this);
        TreeNode tempNode = null;
        if (this.left != null) {
            tempNode = this.left.preOrderSearch(id);
        }
        if (tempNode != null) {
            return tempNode;
        }
        if (this.right != null) {
            tempNode = this.right.preOrderSearch(id);
        }
        return tempNode;
    }

    /**
     * 中序遍历
     */
    public TreeNode inOrderSearch(int id) {
        TreeNode tempNode = null;
        if (this.left != null) {
            tempNode = this.left.inOrderSearch(id);
        }
        if (tempNode != null) {
            return tempNode;
        }
        if (this.id == id) {
            return this;
        }
        if (this.right != null) {
            tempNode = this.right.inOrderSearch(id);
        }
        return tempNode;
    }

    /**
     * 后序遍历
     */
    public TreeNode postOrderSearch(int id) {
        TreeNode tempNode = null;
        if (this.left != null) {
            tempNode = this.left.postOrderSearch(id);
        }
        if (tempNode != null) {
            return tempNode;
        }
        if (this.right != null) {
            tempNode = this.right.postOrderSearch(id);
        }
        if (tempNode != null) {
            return tempNode;
        }
        if (this.id == id) {
            return this;
        }
        return null;
    }


    public void delNode(int id) {
        // 检查左边的节点是否相等
        if (this.left != null && this.left.id == id) {
            this.setLeft(null);
            return;
        }
        //检查右边的节点是否相等
        if (this.right != null && this.right.id == id) {
            this.setRight(null);
            return;
        }

        // 从左边开始找
        if (this.left != null) {
            this.left.delNode(id);
        }

        /**
         *  从右边开始找
         */
        if (this.right != null) {
            this.right.delNode(id);
        }
    }
}
