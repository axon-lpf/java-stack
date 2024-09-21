package com.axon.java.stack.data.structures.queue;

import java.util.Scanner;

/**
 * 添加 CircularListQueue  该队列是一个环形队列， 可以达到复用的目的
 */
public class CircularListQueue {

    public static void main(String[] args) {
        CircularListQueue queue = new CircularListQueue(5);
        Scanner cin = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("输入s展示队列的值");
            System.out.println("输入a添加队列的值");
            System.out.println("输入g获取队列的值");
            System.out.println("输入e退出");
            String nextLine = cin.nextLine();
            switch (nextLine) {
                case "s":
                    queue.showQueue();
                    break;
                case "a":
                    System.out.println("请输入一个数");
                    String line = cin.nextLine();
                    queue.addQueue(Integer.parseInt(line));
                    break;
                case "g":
                    int queue1 = queue.getQueue();
                    System.out.println("获取到的值是" + queue1);
                    break;
                case "e":
                    cin.close();
                    loop = false;
                    break;
                default:
                    throw new RuntimeException("输入值的值不正确");
            }
        }


    }

    /**
     * 构造函数
     *
     * @param maxSize
     */
    public CircularListQueue(int maxSize) {
        this.maxQueueSize = maxSize;
        queueList = new int[maxSize];
    }

    // 队列的头设置为0
    private int front = 0;

    // 队列的增长值为0
    private int rear = 0;
    //设置队列的最大长度
    private int maxQueueSize;

    //队列的总长度的公式

    // (0+rear)+(maxSize-front)= 队列的总长度
    // 即 队列实际有效的长度=rear-front+maxSize%maxQueueSize


    private int[] queueList;

    public void addQueue(int n) {
        if (this.isFull()) {
            throw new RuntimeException("队列已经满了");
        }
        queueList[rear] = n;

        this.rear = (this.rear + 1) % maxQueueSize;
    }

    public int getQueue() {
        if (this.isEmpty()) {
            throw new RuntimeException("队列已经是空的了");
        }
        int result = queueList[front];
        this.front = (this.front + 1) % maxQueueSize;
        return result;
    }

    public void showQueue() {
        if (queueList.length == 0) {
            System.out.println("当前的队列值为空");
            return;
        }
        for (int i = 0; i < queueList.length; i++) {
            System.out.println("queueList[" + i + "]的值是" + queueList[i]);
        }
    }


    /**
     * 判断队列满的条件
     *
     * @return
     */
    public boolean isFull() {
        return (this.rear + 1) % maxQueueSize == this.front;
    }

    private boolean isEmpty() {
        return this.rear == this.front;
    }

}
