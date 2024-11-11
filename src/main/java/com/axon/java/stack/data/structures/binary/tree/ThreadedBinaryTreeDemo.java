package com.axon.java.stack.data.structures.binary.tree;

import lombok.Data;

import javax.swing.plaf.synth.SynthOptionPaneUI;

/**
 * 线索化二叉树
 * <p>
 * 1 (剑兰)
 * /         \
 * 2 (星火)      3 (浮生)
 * /     \            /
 * 4 (安迪) 5 (愚公) 7 (凌霄)
 * <p>
 * 按照中序输出结果是    4（安迪） → 2（星火） → 5（愚公） → 1（剑兰） → 7（凌霄） → 3（浮生）
 * <p>
 * <p>
 * 1(TOM)
 * /       \
 * 3(jack)      6(smith)
 * /       \        /
 * 8(mary)   10(king) 14(tom)
 * <p>
 * 因此，中序遍历的结果为：8(mary), 3(jack), 10(king), 1(TOM), 14(tom), 6(smith)。
 * <p>
 * <p>
 * 线索化步骤说明
 * <p>
 * 1.	初始化变量：pre 开始为空，用于记录前驱节点。
 * 2.	线索化左子节点：调用 threadedTreeNodes(treeNode.getLeft())，开始递归处理左子节点。
 * <p>
 * 具体步骤
 * <p>
 * 1.	线索化节点 8(mary)：
 * •	8(mary) 没有左子节点，进入步骤 二。
 * •	将 8(mary) 的左指针指向 pre（此时 pre 为 null），并将 8(mary) 的 leftType 设置为 1，表示为前驱线索。
 * •	设置 pre = 8(mary)，继续递归，进入 三，线索化右子节点（但 8(mary) 没有右子节点）。
 * 2.	线索化节点 3(jack)：
 * •	3(jack) 有左子节点 8(mary)，此时 8(mary) 已线索化完毕，继续到步骤 二。
 * •	将 3(jack) 的左指针指向 8(mary)（无需修改，因为它是实际左子节点）。
 * •	将 8(mary) 的右指针指向 3(jack)，并将 8(mary) 的 rightType 设置为 1，表示它的后继节点是 3(jack)。
 * •	设置 pre = 3(jack)。
 * •	进入步骤 三，线索化右子节点，处理 10(king)。
 * 3.	线索化节点 10(king)：
 * •	10(king) 没有左子节点，进入步骤 二。
 * •	将 10(king) 的左指针指向 pre = 3(jack)，并将 10(king) 的 leftType 设置为 1。
 * •	将 3(jack) 的右指针指向 10(king)，并将 3(jack) 的 rightType 设置为 1，表示 10(king) 是 3(jack) 的后继。
 * •	设置 pre = 10(king)，线索化右子节点（但 10(king) 没有右子节点）。
 * 4.	线索化节点 1(TOM)：
 * •	1(TOM) 有左子节点 3(jack)，但 3(jack) 已线索化完毕，进入步骤 二。
 * •	将 1(TOM) 的左指针指向 pre = 10(king)（无需修改）。
 * •	将 10(king) 的右指针指向 1(TOM)，并将 10(king) 的 rightType 设置为 1。
 * •	设置 pre = 1(TOM)。
 * •	进入步骤 三，线索化右子节点，处理 6(smith)。
 * 5.	线索化节点 14(tom)：
 * •	14(tom) 没有左子节点，进入步骤 二。
 * •	将 14(tom) 的左指针指向 pre = 1(TOM)，并将 14(tom) 的 leftType 设置为 1。
 * •	将 1(TOM) 的右指针指向 14(tom)，并将 1(TOM) 的 rightType 设置为 1。
 * •	设置 pre = 14(tom)，线索化右子节点（但 14(tom) 没有右子节点）。
 * 6.	线索化节点 6(smith)：
 * •	6(smith) 有左子节点 14(tom)，但 14(tom) 已线索化完毕，进入步骤 二。
 * •	将 6(smith) 的左指针指向 pre = 14(tom)。
 * •	将 14(tom) 的右指针指向 6(smith)，并将 14(tom) 的 rightType 设置为 1。
 * •	设置 pre = 6(smith)，完成整个线索化。
 * <p>
 * 线索化后的节点关系
 * <p>
 * 通过线索化，中序遍历的前驱和后继关系已用空指针保存下来，有效提升了遍历效率。
 */
public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {


        ThreadedTreeNode root = new ThreadedTreeNode(1, "剑兰");
        ThreadedTreeNode treeNode1 = new ThreadedTreeNode(2, "星火");
        ThreadedTreeNode treeNode2 = new ThreadedTreeNode(3, "浮生");
        ThreadedTreeNode treeNode3 = new ThreadedTreeNode(4, "安迪");
        ThreadedTreeNode treeNode4 = new ThreadedTreeNode(5, "愚公");
        ThreadedTreeNode treeNode5 = new ThreadedTreeNode(7, "凌霄");

        root.setLeft(treeNode1);
        root.setRight(treeNode2);
        treeNode1.setLeft(treeNode3);
        treeNode1.setRight(treeNode4);
        treeNode2.setLeft(treeNode5);


       /* ThreadedTreeNode root = new ThreadedTreeNode(1, "TOM");
        ThreadedTreeNode treeNode2 = new ThreadedTreeNode(3, "jack");
        ThreadedTreeNode treeNode3 = new ThreadedTreeNode(6, "smith");
        ThreadedTreeNode treeNode4 = new ThreadedTreeNode(8, "mary");
        ThreadedTreeNode treeNode5 = new ThreadedTreeNode(10, "king");
        ThreadedTreeNode treeNode6 = new ThreadedTreeNode(14, "tom");

        root.setLeft(treeNode2);
        root.setRight(treeNode3);
        treeNode2.setLeft(treeNode4);
        treeNode2.setRight(treeNode5);
        treeNode3.setLeft(treeNode6);
*/

        //开始线索化处理
        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree(root);

        System.out.println("开始前序遍历");
        threadedBinaryTree.preOrder();

        System.out.println("开始中序遍历");
        threadedBinaryTree.inOrder();

        System.out.println("开始后序遍历");
        threadedBinaryTree.postOrder();


        threadedBinaryTree.threadedTreeNodes(root);
        //测试，以10号节点入手，
        System.out.println("测试线索化的前驱节点");
        ThreadedTreeNode left = treeNode5.getLeft();
        System.out.println(left);

        System.out.println("测试线索化的后接节点");

        ThreadedTreeNode right = treeNode5.getRight();
        System.out.println(right);

        System.out.println("开始循环遍历线索化的树节点");
        threadedBinaryTree.threadedTreeList(root);


    }
}


class ThreadedBinaryTree {

    private ThreadedTreeNode root;

    /**
     * 临时的前驱节点
     */
    private ThreadedTreeNode pre;

    public ThreadedBinaryTree(ThreadedTreeNode root) {
        this.root = root;
    }

    /**
     * 循环遍历-线索化二叉树
     *
     * @param root
     */
    public void threadedTreeList(ThreadedTreeNode root) {
        if (root == null) {
            System.out.println("节点为空");
            return;
        }
        ThreadedTreeNode tempNode = root;
        while (tempNode != null) {

            //循环遍历，找到leftType=1的 节点
            while (tempNode.getLeftType() == 0) {
                tempNode = tempNode.getLeft();
            }

            //打印出当前节点
            System.out.println(tempNode);

            //leftType=1,则说明是后继节点,不等于1，则跳出，则需要找下一个节点
            while (tempNode.getRightType() == 1) {
                tempNode = tempNode.getRight();
                System.out.println(tempNode);
            }
            tempNode = tempNode.getRight();
        }

    }

    /**
     * 线索化二叉树
     *
     * @param treeNode
     */
    public void threadedTreeNodes(ThreadedTreeNode treeNode) {

        if (treeNode == null) {
            return;
        }
        //一、线索化左子节点
        threadedTreeNodes(treeNode.getLeft());

        //二、线索化当前节点
        //线索化处理
        if (treeNode.getLeft() == null) {
            treeNode.setLeft(pre);
            treeNode.setLeftType(1);
        }
        //线索化处理
        if (pre != null && pre.getRight() == null) {
            pre.setRight(treeNode);
            pre.setRightType(1);
        }
        pre = treeNode;
        //三、线索化右子节点
        threadedTreeNodes(treeNode.getRight());
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
    public ThreadedTreeNode preOrderSearch(int id) {
        if (root == null) {
            System.out.println("当前树节点为空");
            return null;
        }
        return this.root.preOrderSearch(id);

    }

    /**
     * 中序遍历
     */
    public ThreadedTreeNode inOrderSearch(int id) {
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
    public ThreadedTreeNode postOrderSearch(int id) {

        if (root == null) {
            System.out.println("当前树节点为空");
            return null;
        }
        return this.root.postOrderSearch(id);
    }
}


@Data
class ThreadedTreeNode {

    public ThreadedTreeNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;

    private String name;

    private ThreadedTreeNode left;

    private ThreadedTreeNode right;

    //标记当前节点的类型 0 ：表示左子节点 ，1 表示的是前驱节点
    private int leftType;

    //标记当前节点的类型 0：表示右子节点， 1 表示后继结点 。
    private int rightType;


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
    public ThreadedTreeNode preOrderSearch(int id) {
        if (this.id == id) {
            return this;
        }
        //System.out.println(this);
        ThreadedTreeNode tempNode = null;
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
    public ThreadedTreeNode inOrderSearch(int id) {
        ThreadedTreeNode tempNode = null;
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
    public ThreadedTreeNode postOrderSearch(int id) {
        ThreadedTreeNode tempNode = null;
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
