package com.axon.java.stack.data.structures.linklist;

import java.util.Stack;

public class ChatgptSingleLinkListDemo {

    public static void main(String[] args) {
        ChatgptSingleLinkList list = new ChatgptSingleLinkList();
        ChatgptHeroNode heroNode1 = new ChatgptHeroNode(30, "林冲");
        ChatgptHeroNode heroNode2 = new ChatgptHeroNode(10, "鲁智深");
        ChatgptHeroNode heroNode3 = new ChatgptHeroNode(50, "智多星");
        ChatgptHeroNode heroNode4 = new ChatgptHeroNode(60, "武松");
        ChatgptHeroNode heroNode5 = new ChatgptHeroNode(20, "松江");

        // 添加英雄节点，按照编号顺序
        list.addHeroNodeByOrder(heroNode1);
        list.addHeroNodeByOrder(heroNode2);
        list.addHeroNodeByOrder(heroNode3);
        list.addHeroNodeByOrder(heroNode4);
        list.addHeroNodeByOrder(heroNode5);

        System.out.println("遍历原始链表...");
        list.showList();

        // 删除节点
        list.removeHeroNode(10);
        System.out.println("删除编号10后的链表...");
        list.showList();

        // 更新节点
        list.updateHero(new ChatgptHeroNode(50, "智多星"));
        System.out.println("更新编号50的英雄...");
        list.showList();

        // 获取有效节点数
        int linkCount = list.getLinkCount(list.getHeadNode());
        System.out.println("有效节点的个数: " + linkCount);

        // 查找倒数第k个节点
        System.out.println("获取倒数第1个节点...");
        list.queryKNode(list.getHeadNode(), 1);

        // 反转链表
        System.out.println("反转链表...");
        list.reverseList(list.getHeadNode());
        list.showList();

        // 从尾到头打印
        System.out.println("从尾到头打印链表...");
        list.printReverse(list.getHeadNode());
    }
}

class ChatgptSingleLinkList {

    private ChatgptHeroNode headNode = new ChatgptHeroNode(0, "");  // 虚拟头节点

    public ChatgptHeroNode getHeadNode() {
        return this.headNode;
    }

    /**
     * 按顺序添加节点
     */
    public void addHeroNodeByOrder(ChatgptHeroNode newNode) {
        ChatgptHeroNode temp = headNode;
        boolean exists = false;
        while (temp.getNext() != null && temp.getNext().getNo() < newNode.getNo()) {
            temp = temp.getNext();
        }
        if (temp.getNext() != null && temp.getNext().getNo() == newNode.getNo()) {
            exists = true;
        }
        if (exists) {
            System.out.println("编号 " + newNode.getNo() + " 已存在，无法添加");
        } else {
            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
        }
    }

    /**
     * 删除节点
     */
    public void removeHeroNode(int no) {
        ChatgptHeroNode temp = headNode;
        boolean found = false;
        while (temp.getNext() != null) {
            if (temp.getNext().getNo() == no) {
                found = true;
                break;
            }
            temp = temp.getNext();
        }
        if (found) {
            temp.setNext(temp.getNext().getNext());
        } else {
            System.out.println("编号 " + no + " 不存在");
        }
    }

    /**
     * 更新节点
     */
    public void updateHero(ChatgptHeroNode updatedNode) {
        ChatgptHeroNode temp = headNode.getNext();
        boolean found = false;
        while (temp != null) {
            if (temp.getNo() == updatedNode.getNo()) {
                temp.setName(updatedNode.getName());
                found = true;
                break;
            }
            temp = temp.getNext();
        }
        if (!found) {
            System.out.println("编号 " + updatedNode.getNo() + " 不存在");
        }
    }

    /**
     * 获取链表中的有效节点数
     */
    public int getLinkCount(ChatgptHeroNode head) {
        int count = 0;
        ChatgptHeroNode temp = head.getNext();
        while (temp != null) {
            count++;
            temp = temp.getNext();
        }
        return count;
    }

    /**
     * 查找倒数第K个节点
     */
    public void queryKNode(ChatgptHeroNode head, int k) {
        int size = getLinkCount(head);
        if (k <= 0 || k > size) {
            System.out.println("非法的k值");
            return;
        }
        ChatgptHeroNode temp = head.getNext();
        for (int i = 0; i < size - k; i++) {
            temp = temp.getNext();
        }
        System.out.println("倒数第" + k + "个节点: " + temp);
    }

    /**
     * 反转链表
     */
    public void reverseList(ChatgptHeroNode head) {
        if (head.getNext() == null || head.getNext().getNext() == null) {
            return;  // 空链表或只有一个节点，无需反转
        }
        ChatgptHeroNode prev = null;
        ChatgptHeroNode curr = head.getNext();
        ChatgptHeroNode next;
        while (curr != null) {
            next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        }
        head.setNext(prev);
    }

    /**
     * 从尾到头打印链表
     */
    public void printReverse(ChatgptHeroNode head) {
        if (head.getNext() == null) {
            return;
        }
        Stack<ChatgptHeroNode> stack = new Stack<>();
        ChatgptHeroNode temp = head.getNext();
        while (temp != null) {
            stack.push(temp);
            temp = temp.getNext();
        }
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    /**
     * 展示链表中的数据
     */
    public void showList() {
        if (headNode.getNext() == null) {
            System.out.println("链表为空");
            return;
        }
        ChatgptHeroNode temp = headNode.getNext();
        while (temp != null) {
            System.out.println(temp);
            temp = temp.getNext();
        }
    }
}

class ChatgptHeroNode {
    private int no;
    private String name;
    private ChatgptHeroNode next;

    public ChatgptHeroNode(int no, String name) {
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

    public ChatgptHeroNode getNext() {
        return next;
    }

    public void setNext(ChatgptHeroNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}