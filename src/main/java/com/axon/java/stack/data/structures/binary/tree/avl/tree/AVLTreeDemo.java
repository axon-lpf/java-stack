package com.axon.java.stack.data.structures.binary.tree.avl.tree;

/**
 *  添加平衡二叉树
 *
 * 案例一：
 *  int []  arr={4,3,6,5,7,8}
 *  构建成一个排序二叉树，如图所示：
 *          4
 *        /   \
 *       3     6
 *            / \
 *           5   7
 *                \
 *                 8
 *  将排序二叉树构建成一个平衡二叉树，如图所示：
 *  主要演示左旋转
 *
 *
 *  案例二：
 *  int []  arr={10,12,8,9,7,6}
 *  构建成一个排序二叉树，如图所示：
 *        10
 *       /  \
 *      8    12
 *     / \
 *    7   9
 *   /
 *  6
 *
 *
 *  案例二
 *   int [] arr= {10, 11, 7, 6, 8, 9}
 *   构建成一个排序二叉树，如图所示：
 *
 *        10
 *       /  \
 *      7   11
 *     / \
 *    6   8
 *         \
 *          9
 *   将排序二叉树构建成一个平衡二叉树，如图所示：
 *
 *
 *
 *
 * 案例三
 *  int [] arr = {2, 1, 6, 5, 7, 3}
 *  构建成一个排序二叉树，如图所示：
 *
 *        2
 *       / \
 *      1   6
 *         / \
 *        5   7
 *       /
 *      3
 *将排序二叉树构建成一个平衡二叉树，如图所示：
 *         5
 *       / \
 *      2   6
 *     / \    \
 *    1   3    7
 *
 *
 */
public class AVLTreeDemo {

    public static void main(String[] args) {

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
                    this.leftNode.getLeftHeight();
            }else {
                this.rightRotation();
            }
        }
        //如果右边长度大于左边长度，则向左边旋转
        if (this.getRightHeight()-this.getLeftHeight()>1){
            //如果当前节点的的右子节点的左边长度大于右边长度，则向右旋转
            if (this.rightNode != null && this.rightNode.getLeftHeight()>this.rightNode.getRightHeight()) {
                this.rightNode.getRightHeight();
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
        return this.leftNode==null?0:this.leftNode.getLeftHeight();
    }

    /**
     *  求右子树的高度
     * @return 返回右边的高度值
     */
    public  int  getRightHeight() {
        return this.rightNode==null?0:this.rightNode.getRightHeight();
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