package com.axon.java.stack.data.structures.queue;

import java.util.Scanner;

/**
 * 添加 arraryListQueue
 */
public class ArraryListQueue {

    public static void main(String[] args) {
        ArraryListQueue queue = new ArraryListQueue(3);
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
     *  构造函数
     * @param maxSize
     */
    public ArraryListQueue(int maxSize) {
        this.maxSize = maxSize;
        queueList = new int[maxSize];
    }

    // 队列的头
    private int front = -1;

    // 队列的下标， 对应添加到什么位置了
    private int rear = -1;
    //队列的最大值大小
    private int maxSize;


    private int[] queueList;

    public void addQueue(int n) {
        if (this.isFull()) {
            throw new RuntimeException("队列已经满了");
        }
        this.rear++;
        queueList[rear] = n;
    }

    public int getQueue() {
        if (this.isEmpty()) {
            throw new RuntimeException("队列已经是空的了");
        }
        this.front++;

        return queueList[front];
    }

    public void showQueue() {

        for (int i = 0; i < queueList.length; i++) {
            System.out.println("queueList[" + i + "]的值是" + queueList[i]);
        }
    }


    public boolean isFull() {
        return maxSize == this.rear + 1;
    }

    private boolean isEmpty() {
        return this.rear == this.front;
    }

}
