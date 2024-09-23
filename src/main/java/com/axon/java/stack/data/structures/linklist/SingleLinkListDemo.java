package com.axon.java.stack.data.structures.linklist;

import cn.hutool.json.JSON;
import sun.java2d.opengl.CGLSurfaceData;

public class SingleLinkListDemo {

    public static void main(String[] args) {

        SingleLinkList list = new SingleLinkList();
        list.addHeroNodeByOrder(new HeroNode(30, "林冲"));
        list.addHeroNodeByOrder(new HeroNode(10, "鲁智深"));
        list.addHeroNodeByOrder(new HeroNode(50, "无用"));
        list.showList();
        System.out.println();

    }
}


class SingleLinkList {

    //这是头节点
    HeroNode heroHeadNode = new HeroNode(0, null);

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

    // 展示链表中的数据
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
