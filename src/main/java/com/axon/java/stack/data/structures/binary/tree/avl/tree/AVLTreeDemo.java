package com.axon.java.stack.data.structures.binary.tree.avl.tree;

import java.util.ArrayList;
import java.util.List;

/**
 *  添加平衡二叉树
 *
 * 案例一：
 *  int []  arr={4,3,6,5,7,8}     演示左旋转
 *  构建成一个排序二叉树，如图所示：
 *          4
 *        /   \
 *       3     6
 *            / \
 *           5   7
 *                \
 *                 8
 *  将排序二叉树构建成一个平衡二叉树，如图所示：
 *  主要基于4的节点，向左旋转
 *
 *          6
 *        /   \
 *       4     7
 *     /  \      \
 *    3     5     8
 *
 *
 *
 *  案例二：
 *  int []  arr={10,11,8,9,7,6}          演示右旋转
 *  构建成一个排序二叉树，如图所示：
 *        10
 *       /  \
 *      8    11
 *     / \
 *    7   9
 *   /
 *  6
 *
 * 2.1>构建平衡二叉树， 基于10 的节点，向右旋转， 最终构建一个平衡二叉树
 *
 *        8
 *       /  \
 *      7    10
 *     /    /  \
 *    6    9   11
 *

 *
 *
 *
 *  案例三
 *   int [] arr= {10, 11, 7, 6, 8, 9}  主要演示双旋转
 *   构建成一个排序二叉树，如图所示：
 *
 *        10
 *       /  \
 *      7   11
 *     / \
 *    6   8
 *         \
 *          9
 *  3.1> 先基于以7的左子节点向左旋转 ，  先向左旋转，再次向右旋转。
 *
 *        10
 *       /  \
 *      8   11
 *     / \
 *    7   9
 *   /
 *  6
 *
 *
 *  3.2> 再次基于10的节点，向右旋转，将排序二叉树构建成一个平衡二叉树，如图所示：
 *         8
 *       /  \
 *      7   10
 *     /    / \
 *    6   9    11
 *
 *
 *
 *
 *
 * 案例四
 *  int [] arr = {2, 1, 6, 5, 7, 3}         主要演示双旋转， 先向右旋转， 再次左旋转
 *  构建成一个排序二叉树，如图所示：
 *
 *        2
 *       / \
 *      1   6
 *         / \
 *        5   7
 *       /
 *      3
 *
 *  4.1> 先基于6的右子节点，向右旋转
 *
 *        2
 *       / \
 *      1   5
 *         / \
 *        3   6
 *             \
 *              7

 * 4.2>再次基于2的节点，向左旋转 ，最终二叉树构建成一个平衡二叉树，如图所示：
 *        5
 *       / \
 *      2   6
 *     / \    \
 *    1   3    7
 *
 *
 */
public class AVLTreeDemo {

    public static void main(String[] args) {

        //左旋测试
        int  [] arrLeft={4,3,6,5,7,8};

        AVLTreeNode avlTreeNode=new AVLTreeNode(arrLeft[0]);
        for (int i = 1; i < arrLeft.length; i++) {
            avlTreeNode.addTree(new AVLTreeNode(arrLeft[i]));
        }
        System.out.println("开始中序遍历arrLeft");
        avlTreeNode.indexOrder();


        System.out.println("arrLeft树的总高度为"+avlTreeNode.getTreeHeight());

        System.out.println("arrLeft树的左边高度为"+avlTreeNode.getLeftHeight());

        System.out.println("arrLeft树的右边边高度为"+avlTreeNode.getRightHeight());

        printTree(avlTreeNode);


        //右旋测试
        int  []  arrRight= {10, 11, 8, 9, 7, 6};
         avlTreeNode=new AVLTreeNode(arrRight[0]);
        for (int i = 1; i < arrRight.length; i++) {
            avlTreeNode.addTree(new AVLTreeNode(arrRight[i]));
        }

        System.out.println("开始中序遍历arrRight");
        avlTreeNode.indexOrder();

        System.out.println("arrRight树的总高度为"+avlTreeNode.getTreeHeight());

        System.out.println("arrRight树的左边高度为"+avlTreeNode.getLeftHeight());

        System.out.println("arrRight树的右边边高度为"+avlTreeNode.getRightHeight());
        printTree(avlTreeNode);


        //双旋转测试

        int  []  arrLeftRight= {10, 11, 7, 6, 8, 9};
        avlTreeNode=new AVLTreeNode(arrLeftRight[0]);
        for (int i = 1; i < arrLeftRight.length; i++) {
            avlTreeNode.addTree(new AVLTreeNode(arrLeftRight[i]));
        }

        System.out.println("开始中序遍历arrLeftRight");
        avlTreeNode.indexOrder();

        System.out.println("arrLeftRight树的总高度为"+avlTreeNode.getTreeHeight());

        System.out.println("arrLeftRight树的左边高度为"+avlTreeNode.getLeftHeight());

        System.out.println("arrLeftRight树的右边边高度为"+avlTreeNode.getRightHeight());

        printTree(avlTreeNode);


        //双旋转测试

        int  []  arrLeftRight2= {2, 1, 6, 5, 7, 3};
        avlTreeNode=new AVLTreeNode(arrLeftRight2[0]);
        for (int i = 1; i < arrLeftRight2.length; i++) {
            avlTreeNode.addTree(new AVLTreeNode(arrLeftRight2[i]));
        }

        System.out.println("开始中序遍历arrLeftRight2");
        avlTreeNode.indexOrder();

        System.out.println("arrLeftRight2树的总高度为"+avlTreeNode.getTreeHeight());

        System.out.println("arrLeftRight2树的左边高度为"+avlTreeNode.getLeftHeight());

        System.out.println("arrLeftRight2树的右边边高度为"+avlTreeNode.getRightHeight());

        printTree(avlTreeNode);




    }

    public static   void printTree(AVLTreeNode root) {
        List<String> lines = new ArrayList<>();
        int height = getTreeHeight(root);
        int width = (int) Math.pow(2, height) - 1; // 树的宽度
        List<List<String>> treeMatrix = new ArrayList<>();

        // 初始化空矩阵
        for (int i = 0; i < height; i++) {
            List<String> line = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                line.add(" ");
            }
            treeMatrix.add(line);
        }

        // 填充矩阵
        fillMatrix(root, treeMatrix, 0, 0, width - 1);

        // 转成字符串列表
        for (List<String> row : treeMatrix) {
            StringBuilder sb = new StringBuilder();
            for (String cell : row) {
                sb.append(cell);
            }
            lines.add(sb.toString());
        }

        // 打印结果
        for (String line : lines) {
            System.out.println(line);
        }
    }

    private static void fillMatrix(AVLTreeNode node, List<List<String>> treeMatrix, int depth, int left, int right) {
        if (node == null) {
            return;
        }

        // 确定当前节点的位置
        int mid = (left + right) / 2;
        treeMatrix.get(depth).set(mid, String.valueOf(node.value));

        // 递归处理左右子树
        fillMatrix(node.leftNode, treeMatrix, depth + 1, left, mid - 1);
        fillMatrix(node.rightNode, treeMatrix, depth + 1, mid + 1, right);
    }

    private static int getTreeHeight(AVLTreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getTreeHeight(node.leftNode), getTreeHeight(node.rightNode));
    }
}


/**
 * 排序二超数
 */
class AVLTreeNode {


    /**
     * 构造函数
     *
     * @param value
     */
    public AVLTreeNode(int value) {
        this.value = value;
    }


    /**
     * 树的值
     */
    public Integer value;

    /**
     * 左子节点
     */
    public AVLTreeNode leftNode;

    /**
     * 右子节点
     */
    public AVLTreeNode rightNode;


    @Override
    public String toString() {
        return "AVLTreeNode{" +
                "value=" + value +
                '}';
    }

    /**
     * 插入节点
     *
     * @param node
     */
    public void addTree(AVLTreeNode node) {
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
        //添加avl  avl 平衡二叉树
        //如果左边的长度大于右边的长度，则向右旋转
        if (this.getLeftHeight()-this.getRightHeight()>1){
            //如果当前节点左子节点的右边长度大于左子节点的左边长度，则先将左子节点向左边旋转
            if (this.leftNode != null && this.leftNode.getRightHeight()>this.leftNode.getLeftHeight()) {
                    this.leftNode.leftRotation();
                    System.out.println("先进行左子节点，向左旋转");
                    AVLTreeDemo.printTree(this);
                    this.rightRotation();
            }else {
                this.rightRotation();
            }
            //TODO 注意这里很重要， 这里处理完成之后，不能继续往下走了，否则会错乱
            return;
        }
        //如果右边长度大于左边长度，则向左边旋转
        if (this.getRightHeight()-this.getLeftHeight()>1){
            //如果当前节点的的右子节点的左边长度大于右边长度，则向右旋转
            if (this.rightNode != null && this.rightNode.getLeftHeight()>this.rightNode.getRightHeight()) {
                this.rightNode.rightRotation();
                System.out.println("先进行右边子节点，向右旋转");
                AVLTreeDemo.printTree(this);
                this.leftRotation();
            }else {
                this.leftRotation();
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


    /**
     * 寻找目标节点
     *
     * @param value 传入对应的值
     * @return 返回对应的目标接待您
     */
    public AVLTreeNode getTargetNode(int value) {
        if (this.value.equals(value)) {
            return this;
        } else {

            //判断传入的值小于当前节点，则向左边进行寻找
            if (value < this.value) {
                if (this.leftNode == null) {
                    return null;
                }
                return this.leftNode.getTargetNode(value);
            } else {
                if (this.rightNode == null) {
                    return null;
                }
                //判断当前传入的值大于当前节点，则继续向右边查找
                return this.rightNode.getTargetNode(value);
            }
        }
    }

    /**
     * 寻找目标节点的父节点
     *
     * @param value 传入需要查找的值
     * @return 返回对应的父节点
     */
    public AVLTreeNode getParentNode(int value) {

        if ((this.leftNode != null && this.leftNode.value.equals(value)) || (this.rightNode != null && this.rightNode.value.equals(value))) {
            return this;
        } else {
            //判断当前的值，小于当前节点的值，则继续向左边寻找
            if (value < this.value) {

                if (this.leftNode == null) {
                    return null;
                }
                return this.leftNode.getParentNode(value);
            } else if (value > this.value) {
                if (this.rightNode == null) {
                    return null;
                }
                return this.rightNode.getParentNode(value);
            } else {
                return null;
            }
        }

    }


    /**
     *  求树的最大高度
     * @return 返回最大高度值
     */
    public  int  getTreeHeight() {
        return Math.max(this.leftNode==null?0:this.leftNode.getTreeHeight(),this.rightNode==null?0:this.rightNode.getTreeHeight())+1;
    }

    /**
     *  求左子树的高度
     * @return 返回左边的高度值
     */
    public  int  getLeftHeight() {
        return this.leftNode==null?0:this.leftNode.getTreeHeight();
    }

    /**
     *  求右子树的高度
     * @return 返回右边的高度值
     */
    public  int  getRightHeight() {
        return this.rightNode==null?0:this.rightNode.getTreeHeight();
    }


    /**
     *  左旋转
     *  *          4
     *  *        /   \
     *  *       3     6
     *  *            / \
     *  *           5   7
     *  *                \
     *  *                 8
     */
    public  void  	leftRotation(){
        //设置当前节点为1个新的节点
        AVLTreeNode avlTreeNode = new AVLTreeNode(this.value);
        //将avlTreeNode的左子节点设置为当前节点左子节点
        avlTreeNode.leftNode = this.leftNode;
        //将avlTreeNode的右子节点设置为当前节点右子节点的左子节点
        avlTreeNode.rightNode = this.rightNode.leftNode;
        //将当前节点的左子节点设置为avlTreeNode
        this.leftNode = avlTreeNode;
        //将当前的value 设置为右子节点的 value
        this.value=this.rightNode.value;
        //将当前右子节点设置为当前节的右子节点的右子节点
        this.rightNode=this.rightNode.rightNode;;
    }

    /**
     *  右旋转
     *        10
     *       /  \
     *      8    12
     *     / \
     *    7   9
     *   /
     *  6
     */
    public  void  	rightRotation(){
        //设置当前节点为1个新的节点
        AVLTreeNode avlTreeNode = new AVLTreeNode(this.value);
        //将  avlTreeNode 的左子节点设置为当前左子节点的右子节点
        avlTreeNode.leftNode = this.leftNode.rightNode;
        //将  avlTreeNode 的右子节点设置为右子节点
        avlTreeNode.rightNode = this.rightNode;
        //将当前的右子节点设置为新的节点
        this.rightNode = avlTreeNode;
        //将当前的值设置为左子节点的值
        this.value=this.leftNode.value;
        //当前的左子节点设置为左子节点的左子节点
        this.leftNode=this.leftNode.leftNode;

    }

}