package com.axon.java.stack.data.structures.linklist.josephus;

/**
 * 约瑟夫问题
 * <p>
 * 场景：小朋友围城一圈，手绢的游戏
 * <p>
 * 这里是初版-错误的， 不够全面的。
 */
public class JosephusDemo {

    public static void main(String[] args) {

        JosephusList josephusList = new JosephusList();

        BoyNode boyNode1 = new BoyNode(1, "小明");
        BoyNode boyNode2 = new BoyNode(2, "小话");
        BoyNode boyNode3 = new BoyNode(3, "阿胖");
        BoyNode boyNode4 = new BoyNode(4, "胖猪");

        josephusList.add(boyNode1);
        josephusList.add(boyNode2);
        josephusList.add(boyNode3);
        josephusList.add(boyNode4);

        josephusList.showList();

        System.out.println("获取有效队列的个数。。。。。");
        int totalCount = josephusList.totalCount(josephusList.getHeadBoyNode());
        System.out.println("链表总数量" + totalCount);

        josephusList.countBoy(2, 2, 5);
    }

}


class JosephusList {


    BoyNode headBoyNode;

    /**
     * 获取头节点
     *
     * @return
     */
    public BoyNode getHeadBoyNode() {
        return headBoyNode;
    }

    /**
     * 初始化头结点
     */
    public JosephusList() {
        headBoyNode = new BoyNode(0, "默认小孩");
        headBoyNode.setNext(headBoyNode);
    }


    /**
     * 添加节点
     *
     * @param boyNode
     */
    public void add(BoyNode boyNode) {
        if (boyNode == null) {
            System.out.println("添加的节点不能为空");
        }
        BoyNode tempBoyNode = headBoyNode;
        while (true) {
            if (tempBoyNode.getNext() == headBoyNode) {
                break;
            }
            tempBoyNode = tempBoyNode.getNext();
        }
        //添加的当前节点的下一节点设置为头节点
        boyNode.setNext(headBoyNode);
        // 设置下一节点
        tempBoyNode.setNext(boyNode);
    }

    public void showList() {
        BoyNode tempBoyNode = headBoyNode;
        while (true) {
            if (tempBoyNode.getNext() == headBoyNode) {
                System.out.println("节点" + tempBoyNode);
                break;
            }
            System.out.println("节点" + tempBoyNode);
            tempBoyNode = tempBoyNode.getNext();
        }
    }

    /**
     * 获取链表的总数量
     *
     * @param headBoyNode
     * @return
     */
    public int totalCount(BoyNode headBoyNode) {
        if (headBoyNode.getNext() == headBoyNode) {
            System.out.println("链表的总数量为0 ");
            return 0;
        }
        int count = 0;
        BoyNode tempBoyNode = headBoyNode;
        while (true) {

            if (tempBoyNode.getNext() == headBoyNode) {
                break;
            }
            count++;
            tempBoyNode = tempBoyNode.getNext();
        }
        return count;
    }


    /**
     * 约瑟夫算法
     *
     * @param startIndex
     * @param countNumber
     * @param totalCount
     */
    public void countBoy(int startIndex, int countNumber, int totalCount) {
        if (startIndex < 0) {
            System.out.println("开始位置异常");
            return;
        }
        if (countNumber > totalCount) {
            System.out.println("数数的数量异常");
            return;
        }
        // 创建一个辅助指针 helperNode，帮助遍历环形链表
        BoyNode helperNode = headBoyNode;
        BoyNode firstNode = headBoyNode;
        BoyNode tempBoyNode = headBoyNode;
        while (true) {
            if (tempBoyNode.getNext() == headBoyNode) {
                helperNode = tempBoyNode;
                break;
            }
            tempBoyNode = tempBoyNode.getNext();
        }

        //开始报数之前，先后移节点
        for (int j = 0; j < startIndex - 1; j++) {
            firstNode = firstNode.getNext();
            helperNode = helperNode.getNext();
        }

        while (true) {
            if (helperNode == firstNode) {
                break;
            }
            for (int i = 0; i < countNumber - 1; i++) {
                firstNode = firstNode.getNext();
                helperNode = helperNode.getNext();
            }

            System.out.println("小孩出队了" + firstNode.toString());
            firstNode = firstNode.getNext();
            helperNode.setNext(firstNode);

        }


    }


}

/**
 * 小朋友节点
 */
class BoyNode {

    private int no;

    private String name;

    private BoyNode next;

    public BoyNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BoyNode getNext() {
        return next;
    }

    public void setNext(BoyNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "BoyNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

}
