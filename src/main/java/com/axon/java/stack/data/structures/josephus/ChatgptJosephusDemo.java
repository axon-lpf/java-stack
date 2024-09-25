package com.axon.java.stack.data.structures.josephus;

/**
 * 约瑟夫问题
 * 场景：小朋友围成一圈，手绢的游戏
 */
public class ChatgptJosephusDemo {

    public static void main(String[] args) {

        ChatgptJosephusList josephusList = new ChatgptJosephusList();

        ChatgptBoyNode boyNode1 = new ChatgptBoyNode(1, "小明");
        ChatgptBoyNode boyNode2 = new ChatgptBoyNode(2, "小华");
        ChatgptBoyNode boyNode3 = new ChatgptBoyNode(3, "阿胖");
        ChatgptBoyNode boyNode4 = new ChatgptBoyNode(4, "胖猪");

        josephusList.add(boyNode1);
        josephusList.add(boyNode2);
        josephusList.add(boyNode3);
        josephusList.add(boyNode4);

        josephusList.showList();

        System.out.println("获取链表的总数量...");
        int totalCount = josephusList.totalCount();
        System.out.println("链表总数量：" + totalCount);

        // 从第2个小孩开始，每次报2个数，总共4个小孩
        josephusList.countBoy(2, 2, totalCount);
    }
}


class ChatgptJosephusList {

    private ChatgptBoyNode headChatgptBoyNode; // 头节点

    /**
     * 获取头节点
     */
    public ChatgptBoyNode getHeadChatgptBoyNode() {
        return headChatgptBoyNode;
    }

    /**
     * 初始化头节点为空
     */
    public ChatgptJosephusList() {
        headChatgptBoyNode = null; // 初始化为空，避免无效节点
    }

    /**
     * 添加节点，形成环形链表
     */
    public void add(ChatgptBoyNode boyNode) {
        if (boyNode == null) {
            System.out.println("添加的节点不能为空");
            return;
        }

        // 如果链表为空，直接让新节点成为头节点，自己指向自己形成环形
        if (headChatgptBoyNode == null) {
            headChatgptBoyNode = boyNode;
            boyNode.setNext(headChatgptBoyNode);
        } else {
            // 否则找到最后一个节点，将其 next 指向新的节点，新的节点再指向头节点
            ChatgptBoyNode tempChatgptBoyNode = headChatgptBoyNode;
            while (tempChatgptBoyNode.getNext() != headChatgptBoyNode) {
                tempChatgptBoyNode = tempChatgptBoyNode.getNext();
            }
            tempChatgptBoyNode.setNext(boyNode);
            boyNode.setNext(headChatgptBoyNode); // 新节点指向头节点，形成环
        }
    }

    /**
     * 显示链表中的所有节点
     */
    public void showList() {
        if (headChatgptBoyNode == null) {
            System.out.println("链表为空！");
            return;
        }

        ChatgptBoyNode tempChatgptBoyNode = headChatgptBoyNode;
        do {
            System.out.println("节点：" + tempChatgptBoyNode);
            tempChatgptBoyNode = tempChatgptBoyNode.getNext();
        } while (tempChatgptBoyNode != headChatgptBoyNode); // 判断是否回到头节点
    }

    /**
     * 获取链表的总数量
     */
    public int totalCount() {
        if (headChatgptBoyNode == null) {
            return 0;
        }

        int count = 0;
        ChatgptBoyNode tempChatgptBoyNode = headChatgptBoyNode;
        do {
            count++;
            tempChatgptBoyNode = tempChatgptBoyNode.getNext();
        } while (tempChatgptBoyNode != headChatgptBoyNode);
        return count;
    }

    /**
     * 约瑟夫算法
     *
     * @param startIndex  从哪个小孩开始数数（索引从1开始）
     * @param countNumber 数几下（即报多少个数）
     * @param totalCount  总共有多少小孩
     */
    public void countBoy(int startIndex, int countNumber, int totalCount) {
        if (headChatgptBoyNode == null || startIndex < 1 || startIndex > totalCount) {
            System.out.println("开始位置异常");
            return;
        }
        if (countNumber <= 0 || totalCount <= 1) {
            System.out.println("数数的数量异常");
            return;
        }

        // 辅助指针，帮助进行环形链表的遍历
        ChatgptBoyNode helperNode = headChatgptBoyNode;

        // 找到最后一个节点
        while (helperNode.getNext() != headChatgptBoyNode) {
            helperNode = helperNode.getNext();
        }

        // 把 firstNode 和 helperNode 移动到起始位置
        for (int i = 0; i < startIndex - 1; i++) {
            headChatgptBoyNode = headChatgptBoyNode.getNext();
            helperNode = helperNode.getNext();
        }

        // 开始报数，直到圈中只有一个节点
        while (helperNode != headChatgptBoyNode) {
            // 报数时，firstNode 和 helperNode 一起移动 countNumber - 1 次
            for (int i = 0; i < countNumber - 1; i++) {
                headChatgptBoyNode = headChatgptBoyNode.getNext();
                helperNode = helperNode.getNext();
            }

            // 小孩出圈
            System.out.println("小孩出队了：" + headChatgptBoyNode);

            // 删除 firstNode 节点
            headChatgptBoyNode = headChatgptBoyNode.getNext();
            helperNode.setNext(headChatgptBoyNode);
        }

        System.out.println("最后留在圈中的小孩编号是：" + headChatgptBoyNode);
    }
}

/**
 * 小朋友节点类
 */
class ChatgptBoyNode {

    private int no; // 小孩编号
    private String name; // 小孩名字
    private ChatgptBoyNode next; // 指向下一个节点

    public ChatgptBoyNode(int no, String name) {
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

    public ChatgptBoyNode getNext() {
        return next;
    }

    public void setNext(ChatgptBoyNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "ChatgptBoyNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}

