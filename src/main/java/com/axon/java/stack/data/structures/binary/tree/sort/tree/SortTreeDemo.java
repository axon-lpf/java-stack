package com.axon.java.stack.data.structures.binary.tree.sort.tree;

/**
 * 排序二叉树
 *  核心逻辑：
 *      1.插入的节点的值与当前节点的值比较
 *          1.1>如果插入的节点小于当前节点，且左子节点为空， 则将当前节点的值赋值给左子节点
 *          1.2>否则继续递归往下寻找左子节点
 *          1.3> 如果插入的节点大于当前节点，且右子节点为空， 则将当前节点的值赋值给右子节点
 *          1.4>否则继续向下寻找。
 *
 */
public class SortTreeDemo {


    public static void main(String[] args) {

        SortTree sortTree = new SortTree(new SortTreeNode(1));
        sortTree.addTree(new SortTreeNode(200));
        sortTree.addTree(new SortTreeNode(30));
        sortTree.addTree(new SortTreeNode(400));
        sortTree.addTree(new SortTreeNode(5));
        sortTree.addTree(new SortTreeNode(60));

        sortTree.indexOrder();

    }
}

class SortTree {

    private final SortTreeNode root;

    public SortTree(SortTreeNode root) {
        this.root = root;
    }


    /**
     * 添加节点
     *
     */
    public void addTree(SortTreeNode node) {
        this.root.addTree(node);
    }


    /**
     * 中序遍历
     */
    public void indexOrder() {
        if (root == null) {
            System.out.println("当前节点为空");
            return;
        }
        root.indexOrder();
    }
}


/**
 * 排序二超数
 */
class SortTreeNode {


    /**
     * 构造函数
     *
     * @param value
     */
    public SortTreeNode(int value) {
        this.value = value;
    }


    /**
     * 树的值
     */
    public int value;

    /**
     * 左子节点
     */
    public SortTreeNode leftNode;

    /**
     * 右子节点
     */
    public SortTreeNode rightNode;


    @Override
    public String toString() {
        return "SortTreeNode{" +
                "value=" + value +
                '}';
    }

    /**
     * 插入节点
     *
     * @param node
     */
    public void addTree(SortTreeNode node) {
        if (node == null) {
            System.out.println("添加节点不能为空");
            return;
        }
        //插入的节点，小于当前节点，则继续寻找，插入到左边节点
        if (node.value < this.value) {
            if (this.leftNode == null) {
                this.leftNode = node;
            } else {
                this.leftNode.addTree(node);
            }
        }
        //如果插入的节点大于当前节点
        if (node.value > this.value) {
            if (this.rightNode == null) {
                this.rightNode = node;
            } else {
                this.rightNode.addTree(node);
            }
        }
    }

    /**
     * 中序遍历
     */
    public void indexOrder() {
        if (this.leftNode != null) {
            this.leftNode.indexOrder();
        }

        System.out.println(this);

        if (this.rightNode != null) {
            this.rightNode.indexOrder();
        }
    }

}
