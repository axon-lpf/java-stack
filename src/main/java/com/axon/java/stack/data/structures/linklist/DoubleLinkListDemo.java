package com.axon.java.stack.data.structures.linklist;

public class DoubleLinkListDemo {

    public static void main(String[] args) {

        DoubleLinkList list = new DoubleLinkList();
        DoubleHeroNode heroNode1 = new DoubleHeroNode(30, "林冲");
        DoubleHeroNode heroNode2 = new DoubleHeroNode(10, "鲁智深");

        DoubleHeroNode heroNode3 = new DoubleHeroNode(50, "无用");

        DoubleHeroNode heroNode4 = new DoubleHeroNode(60, "武松");

        DoubleHeroNode heroNode5= new DoubleHeroNode(20, "松江");


        list.addHeroNode(heroNode1);
        list.addHeroNode(heroNode2);
        list.addHeroNode(heroNode3);
        list.addHeroNode(heroNode4);
        list.addHeroNode(heroNode5);

        System.out.println("遍历原始数据。。。。。。。");
        list.showList();

        //开始删除节点

        list.removeHeroNode(10);
        System.out.println("删除后的节点列表。。。。。。。");
        list.showList();

        list.updateHero(new DoubleHeroNode(50,"智多星"));

        System.out.println("修改后的节点列表");

        list.showList();

        System.out.println();

    }
}


class DoubleLinkList {

    //这是头节点
    DoubleHeroNode heroHeadNode = new DoubleHeroNode(0, null);

    /**
     * 添加英雄节点
     *
     * @param currentNode
     */
    public void addHeroNode(DoubleHeroNode currentNode) {
        //获取一个临时节点
        DoubleHeroNode temp = heroHeadNode;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        // 为下一个节点赋值
        temp.setNext(currentNode);
        //设置前节点
        currentNode.setPre(temp);
    }

    /**
     * 按照顺序添加有英雄节点
     *
     * @param currentNode
     */
    public void addHeroNodeByOrder(DoubleHeroNode currentNode) {
        //获取一个临时节点
        DoubleHeroNode temp = heroHeadNode;
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
            //当前节点的上一节点
            currentNode.setPre(temp);

            // 为下一个节点赋值
            temp.setNext(currentNode);

        }
    }


    /**
     *   移除系节点
     * @param no
     */
    public void removeHeroNode(int no) {

        if (no < 0) {
            System.out.println("编号异常， 无法删除");
            return;
        }
        DoubleHeroNode temp = heroHeadNode;

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
            temp.getNext().getNext().setPre(temp);
        } else {
            System.out.println("未找到对应的节点");
        }
    }

    /**
     *  更新英雄
     * @param heroNode
     */
    public  void updateHero(DoubleHeroNode heroNode) {
        DoubleHeroNode tempNode = heroHeadNode;
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
            System.out.println("yichang");
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
        DoubleHeroNode temp = heroHeadNode.getNext();
        while (temp != null) {
            // 输出节点的信息
            System.out.println(temp);
            temp = temp.getNext();
        }
    }

}


class DoubleHeroNode {

    public DoubleHeroNode getNext() {
        return next;
    }

    public void setNext(DoubleHeroNode next) {
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

    private DoubleHeroNode next;

    public DoubleHeroNode getPre() {
        return pre;
    }

    public void setPre(DoubleHeroNode pre) {
        this.pre = pre;
    }

    private DoubleHeroNode pre;


    public DoubleHeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "DoubleHeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}
