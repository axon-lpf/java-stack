package com.axon.java.stack.data.structures.linklist;

/**
 * 单向环形链表
 */
public class SingleSparseLinkListDemo {

    public static void main(String[] args) {

        // 创建单向环形链表
        SingleSparseLinkList linkList = new SingleSparseLinkList();

        // 添加节点
        linkList.addHeroNode(new SparseHeroNode(1, "Hero 1"));
        linkList.addHeroNode(new SparseHeroNode(2, "Hero 2"));
        linkList.addHeroNode(new SparseHeroNode(3, "Hero 3"));

        // 显示链表
        linkList.showList();

        // 删除节点
        linkList.removeHeroNode(2);
        System.out.println("删除编号为2的节点后：");
        linkList.showList();

        // 更新节点
        linkList.updateHero(new SparseHeroNode(1, "Updated Hero 1"));
        System.out.println("更新编号为1的节点后：");
        linkList.showList();

    }
}


class  SingleSparseLinkList {


    //这是头节点
    SparseHeroNode heroHeadNode = new SparseHeroNode(0, null);


    /**
     * 获取头节点
     *
     * @return
     */
    public SparseHeroNode getHeroHeadNode() {
        return this.heroHeadNode;
    }


    /**
     * 添加英雄节点
     *
     * @param currentNode
     */
    public void addHeroNode(SparseHeroNode currentNode) {
        //获取一个临时节点
        SparseHeroNode temp = heroHeadNode;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        // 为下一个节点赋值
        temp.setNext(currentNode);
        currentNode.setNext(heroHeadNode);
    }

    /**
     * 按照顺序添加有英雄节点
     *
     * @param currentNode
     */
    public void addHeroNodeByOrder(SparseHeroNode currentNode) {
        //获取一个临时节点
        SparseHeroNode temp = heroHeadNode;
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
            if (currentNode.getNext() == null) {
                currentNode.setNext(heroHeadNode);
            }
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
        SparseHeroNode temp = heroHeadNode;

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
    public void updateHero(SparseHeroNode heroNode) {
        SparseHeroNode tempNode = heroHeadNode;
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
        SparseHeroNode temp = heroHeadNode.getNext();
        while (temp != null) {
            // 输出节点的信息
            System.out.println(temp);
            temp = temp.getNext();
        }
    }

}

class SparseHeroNode {

    public SparseHeroNode getNext() {
        return next;
    }

    public void setNext(SparseHeroNode next) {
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

    private SparseHeroNode next;


    public SparseHeroNode(int no, String name) {
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
