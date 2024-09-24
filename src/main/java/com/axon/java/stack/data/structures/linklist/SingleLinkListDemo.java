package com.axon.java.stack.data.structures.linklist;


import java.util.Stack;

/**
 * 1.求单链表中有效节点的个数
 * 2.查找单链表中倒数第k个节点
 * 3.单链表进行反转
 * 4.从尾到头打印单链表
 * 5.合并两个有序的单链表，合并之后链表依然有序
 */
public class SingleLinkListDemo {

    public static void main(String[] args) {

        SingleLinkList list = new SingleLinkList();
        HeroNode heroNode1 = new HeroNode(30, "林冲");
        HeroNode heroNode2 = new HeroNode(10, "鲁智深");

        HeroNode heroNode3 = new HeroNode(50, "无用");

        HeroNode heroNode4 = new HeroNode(60, "武松");

        HeroNode heroNode5 = new HeroNode(20, "松江");


        list.addHeroNodeByOrder(heroNode1);
        list.addHeroNodeByOrder(heroNode2);
        list.addHeroNodeByOrder(heroNode3);
        list.addHeroNodeByOrder(heroNode4);
        list.addHeroNodeByOrder(heroNode5);

        System.out.println("遍历原始数据。。。。。。。");
        list.showList();

        //开始删除节点

        list.removeHeroNode(10);
        System.out.println("删除后的节点列表。。。。。。。");
        list.showList();

        list.updateHero(new HeroNode(50, "智多星"));

        System.out.println("修改后的节点列表");

        list.showList();

        System.out.println("获取有效节点的个数。。。。。。。");

        int linkCount = list.getLinkCount(list.getHeroHeadNode());

        System.out.println("当前有效节点的个数是" + linkCount);

        System.out.println("获取倒数第K个节点。。。。。。。。");
        list.queryKNode(list.getHeroHeadNode(), 1);

        System.out.println("从尾到头打印单链表。。。。。。");

        list.printLink(list.getHeroHeadNode());

        System.out.println("开始反转链表。。。。。。。。");

        list.reversalLink(list.getHeroHeadNode());

        System.out.println("反转后端list集合列表。。。。。。。");

        list.showList();

        System.out.println();

    }
}


class SingleLinkList {

    //这是头节点
    HeroNode heroHeadNode = new HeroNode(0, null);


    /**
     * 获取头节点
     *
     * @return
     */
    public HeroNode getHeroHeadNode() {
        return this.heroHeadNode;
    }


    /**
     * 添加英雄节点
     *
     * @param currentNode
     */
    public void addHeroNode(HeroNode currentNode) {
        //获取一个临时节点
        HeroNode temp = heroHeadNode;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        // 为下一个节点赋值
        temp.setNext(currentNode);
    }

    /**
     * 按照顺序添加有英雄节点
     *
     * @param currentNode
     */
    public void addHeroNodeByOrder(HeroNode currentNode) {
        //获取一个临时节点
        HeroNode temp = heroHeadNode;
        boolean flag = false;
        while (true) {
            if (temp.getNext() == null) {
                break;
            } else if (temp.getNext().getNo() > currentNode.getNo()) {
                break;
            } else if (temp.getNext().getNo() == currentNode.getNo()) {
                flag = true;
            }
            temp = temp.getNext();
        }
        if (flag) {
            System.out.println("编号 " + currentNode.getNo() + " 已经存在，无法添加");
        } else {
            currentNode.setNext(temp.getNext());
            // 为下一个节点赋值
            temp.setNext(currentNode);
        }
    }


    /**
     * 移除系节点
     *
     * @param no
     */
    public void removeHeroNode(int no) {

        if (no < 0) {
            System.out.println("编号异常， 无法删除");
            return;
        }
        HeroNode temp = heroHeadNode;

        boolean flag = false;

        while (true) {

            if (temp.getNext() == null) {
                break;
            }
            if (temp.getNext().getNo() == no) {
                flag = true;
                break;
            }
            temp = temp.getNext();
        }
        if (flag) {
            //将当前节点设置为
            temp.setNext(temp.getNext().getNext());
        } else {
            System.out.println("未找到对应的节点");
        }
    }

    /**
     * 更新英雄
     *
     * @param heroNode
     */
    public void updateHero(HeroNode heroNode) {
        HeroNode tempNode = heroHeadNode;
        if (tempNode.getNext() == null) {
            System.out.println("当前链表的节点为空，无法进行修改");
            return;
        }

        boolean flag = false;
        while (true) {
            if (tempNode.getNo() == heroNode.getNo()) {
                flag = true;
                break;
            } else if (tempNode.getNext() == null) {
                break;
            }
            tempNode = tempNode.getNext();
        }
        if (flag) {
            //修改名称
            tempNode.setName(heroNode.getName());
        } else {
            System.out.println("更新异常了");
        }
    }


    /**
     * 展示链表中的数据
     */
    public void showList() {
        // 判断链表是否为空
        if (heroHeadNode.getNext() == null) {
            System.out.println("链表为空");
            return;
        }

        // 因为头节点不能动，因此需要一个临时变量来遍历
        HeroNode temp = heroHeadNode.getNext();
        while (temp != null) {
            // 输出节点的信息
            System.out.println(temp);
            temp = temp.getNext();
        }
    }

    /**
     * 获取有效节点的个数
     */
    public int getLinkCount(HeroNode heroNode) {
        if (heroNode.getNext() == null) {
            System.out.println("链表节点数为空");
            return 0;
        }
        int count = 0;
        HeroNode tempNode = heroNode;
        while (tempNode.getNext() != null) {
            count = count + 1;
            tempNode = tempNode.getNext();
        }
        return count;
    }

    /**
     * 查找单链表中倒数第k个节点
     *
     * @param heardNode
     * @param kNode
     */
    public void queryKNode(HeroNode heardNode, int kNode) {
        if (kNode < 0) {
            System.out.println("倒数节点错误");
            return;
        }
        int linkCount = this.getLinkCount(heardNode);
        if (kNode > linkCount) {
            System.out.println("倒数节点错误,不能超过链表节点总和");
            return;
        }
        //不算头节点， 头节点不是有效节点
        HeroNode temp = heardNode.getNext();
        for (int i = 0; i < linkCount - kNode; i++) {
            temp = temp.getNext();
        }
        System.out.println("找到节点了，节点信息" + temp.toString());

    }

    /**
     * 反转链表
     *
     * @param heardNode
     */
    public void reversalLink(HeroNode heardNode) {
        if (heardNode.getNext() == null) {
            System.out.println("当前链表为空");
            return;
        }
        HeroNode curNode = heardNode.getNext();
        HeroNode next = null; // 指向当前节点的下一个节点
        HeroNode reversalHead = new HeroNode(0, "");
        while (curNode != null) {
            next = curNode.getNext(); // 获取到当前节点的下一节点
            curNode.setNext(reversalHead.getNext());         //当前节点的下一节点设置为头
            reversalHead.setNext(curNode);   // 设置当前节点
            curNode = next; //后移
        }
        heardNode.setNext(reversalHead.getNext());
    }

    /**
     * 从尾到头打印单链表
     *
     * @param heardNode
     */
    public void printLink(HeroNode heardNode) {
        if (heardNode.getNext() == null) {
            System.out.println("当前链表为空");
            return;
        }
        HeroNode temp = heardNode;
        Stack<HeroNode> stack = new Stack<>();
        while (temp.getNext() != null) {
            stack.push(temp.getNext());
            temp = temp.getNext();
        }
        while (!stack.empty()) {
            HeroNode pop = stack.pop();
            System.out.println(pop.toString());
        }
    }


}


class HeroNode {

    public HeroNode getNext() {
        return next;
    }

    public void setNext(HeroNode next) {
        this.next = next;
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

    private int no;

    private String name;

    private HeroNode next;


    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}
