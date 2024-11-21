package com.axon.java.stack.data.structures.binary.tree.sort.tree;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * 排序二叉树
 * 核心逻辑：
 * 1.插入的节点的值与当前节点的值比较
 * 1.1>如果插入的节点小于当前节点，且左子节点为空， 则将当前节点的值赋值给左子节点
 * 1.2>否则继续递归往下寻找左子节点
 * 1.3> 如果插入的节点大于当前节点，且右子节点为空， 则将当前节点的值赋值给右子节点
 * 1.4>否则继续向下寻找。
 * <p>
 * 论如何提高互联网技术团队中的项目上线质量以及团队工作人员的积极性？
 * 1.背景和原因
 * 2.团队人员分配分析
 * 3.项目开发上线流程分析
 * 4.总结现有存在的问题
 * 5.解决问题
 * 5.1> 项目立项后，设置项目奖金池，项目奖金池可以根据项目的大小、难度、人员分配去评估，评估人员由产品经理、老板、CTO，最低500 ，最大无上限
 * 获得奖金的条件： 1>项目上线后，平稳运行一月后，无产生线上bug。则参与人员的均或得该奖金，如果奖金池设置500， 则人均500(开发、测试).
 * 2>项目延期，则奖金池缩小一半。
 * 3>上线的过程中，发生回滚操作，则奖金撤销
 * 5.2>提高开发和测试人员的工作积极性以及工作态度
 * 1>开发人员: 按照禅道bug计数， 1-10个bug,每个bug10元，超过10个后的bug,即从第11个计数，每个20元。后面依次累加 这些费用由开发人员个人担负，并支付给测试人员。 (或者由个人一半，公司承担一半的处理)， 每个bug的价格可根据项目规模的大小进行动态调整。
 * 3>测试人员: 按照禅道bug计数， 找出1-10个bug,每个bug10元，超过10个后的bug,即从第11个计数，每个20元。测试人员提出的bug越多，则奖励越多。  公司在这基础之上，再次奖励1倍。
 * 如若上线后，产生了线上bug,则测试人员所获得的奖励，则没收，收归为公有，计入下一次的项目奖池中。
 *
 *
 *
 */
public class SortTreeDemo {


    /**
     *  排序二叉树结构图
     *
     *           7
     *        /   \
     *       3     10
     *      / \    /  \
     *     1   5  9   12
     *      \
     *       2
     *
     * @param args
     */
    public static void main(String[] args) {

        SortTree sortTree = new SortTree(new SortTreeNode(7));
        sortTree.addTree(new SortTreeNode(3));
        sortTree.addTree(new SortTreeNode(10));
        sortTree.addTree(new SortTreeNode(1));
        sortTree.addTree(new SortTreeNode(5));
        sortTree.addTree(new SortTreeNode(9));
        sortTree.addTree(new SortTreeNode(12));
        sortTree.addTree(new SortTreeNode(1));

        sortTree.indexOrder();

        sortTree.delTargetNode(12);

        sortTree.indexOrder();

        sortTree.delTargetNode(10);

        sortTree.indexOrder();

        sortTree.delTargetNode(1);

        sortTree.indexOrder();

        sortTree.delTargetNode(9);

        System.out.println("删除9节点后");
        sortTree.indexOrder();

        sortTree.delTargetNode(3);

        System.out.println("删除3节点后");
        sortTree.indexOrder();


        sortTree.delTargetNode(5);

        System.out.println("删除5节点后");
        sortTree.indexOrder();


        sortTree.delTargetNode(7);

        System.out.println("删除7节点后");
        sortTree.indexOrder();





    }
}

class SortTree {

    private SortTreeNode root;

    public SortTree(SortTreeNode root) {
        this.root = root;
    }


    /**
     * 添加节点
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

    /**
     * 寻找目标节点
     *
     * @param value 传入目标节点的值
     * @return 返回对应的目标节点
     */
    public SortTreeNode getTargetNode(int value) {
        if (root == null) {
            return null;
        }
        return this.root.getTargetNode(value);
    }

    /**
     * 寻找目标节点的父节点
     *
     * @param value 传入节点的值
     * @return 返回对应的父节点
     */
    public SortTreeNode getParentNode(int value) {
        if (this.root == null) {
            return null;
        }
        return this.root.getParentNode(value);
    }

    /**
     * 删除目标节点
     *
     * @param value
     */
    public void delTargetNode(int value) {
        if (root == null) {
            System.out.println("当前节点为空，不能删除");
            return;
        }
        SortTreeNode targetNode = this.getTargetNode(value);
        if (targetNode == null) {
            System.out.println("目标节点不存在");
            return;
        }
        //如果左子节点和右子节点都为null, 说明则是删除的root节点
        if (this.root.leftNode == null && this.root.rightNode == null) {
            this.root = null;
            return;
        }
        SortTreeNode parentNode = this.getParentNode(value);
        //1.处理叶子节点
        //2.处理非叶子接节点
        //3.处理拥有单个节点的
        if (targetNode.leftNode == null && targetNode.rightNode == null) {
            //处理叶子节点
            if (parentNode.leftNode.value.equals(targetNode.value)) {
                //当目标节点等于父节点的左边节点
                parentNode.leftNode = null;
            } else {
                //当目标节点等于父节点的右边节点
                parentNode.rightNode = null;
            }
        } else if (targetNode.leftNode != null && targetNode.rightNode != null) {
            //处理非叶子节点的
            //1.从右子节点入手，查询最小的节点
            //2. 临时变量保存最小的的节点
            //3.删除最小的节点
            //4.将最小节点赋值给当前target
            SortTreeNode minTreeNode = getMinTreeNode(targetNode.rightNode);
            targetNode.value = minTreeNode.value;
        } else {
            //当前节点拥有单个叶子节点
            //当左边的节点不为空时
            if (targetNode.leftNode != null) {
                if (parentNode != null) {
                    //当前父节点的左子节点等于当前节点
                    if (parentNode.leftNode.value.equals(targetNode.value)) {
                        parentNode.leftNode = targetNode.leftNode;
                    } else {
                        //当父节点的右子节点等于当前节点
                        parentNode.rightNode = targetNode.leftNode;
                    }
                } else {
                    /**
                     *  处理情况
                     *             10
                     *           /
                     *         9
                     */
                    root = targetNode.leftNode;
                }
            } else {

                if (parentNode != null) {
                    //当前右边的节点不为空时
                    if (parentNode.leftNode.value.equals(targetNode.value)) {
                        parentNode.leftNode = targetNode.rightNode;
                    } else {
                        parentNode.rightNode = targetNode.rightNode;
                    }
                } else {
                    /**
                     *  处理情况：
                     10
                     *                          \
                     *                            12
                     */
                    root = targetNode.rightNode;
                }

            }

        }
    }

    public SortTreeNode getMinTreeNode(SortTreeNode sortTreeNode) {

        SortTreeNode tempNode = sortTreeNode;
        while (tempNode.leftNode != null) {
            tempNode = tempNode.leftNode;
        }
        //删除最小的值，
        this.delTargetNode(tempNode.value);

        return tempNode;

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
    public Integer value;

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


    /**
     * 寻找目标节点
     *
     * @param value 传入对应的值
     * @return 返回对应的目标接待您
     */
    public SortTreeNode getTargetNode(int value) {
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
    public SortTreeNode getParentNode(int value) {

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

}
