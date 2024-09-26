package com.axon.java.stack.data.structures.stack;


/**
 * 简易的栈结构
 */
public class StackDemo {
    public static void main(String[] args) {


        CustomStack customStack = new CustomStack(5);

        customStack.push(10);
        customStack.push(20);
        customStack.push(50);

        customStack.push(100);

        customStack.push(200);

        customStack.push(300);

        System.out.println("开始取出数据");

        int pop = customStack.pop();
        System.out.println("取出的当前结果" + pop);

        pop = customStack.pop();
        System.out.println("取出的当前结果" + pop);

        customStack.push(100);

        customStack.push(200);

        System.out.println("继续取出。。。。");

        pop = customStack.pop();
        System.out.println("取出的当前结果" + pop);

        pop = customStack.pop();
        System.out.println("取出的当前结果" + pop);


    }


}

/**
 * 自定义栈
 */
class CustomStack {

    int[] stack;

    private int top = -1;

    private int size;

    /**
     * 自定义栈
     *
     * @param size
     */
    public CustomStack(int size) {
        //TODO 这里没有判断size的值是否正确？ 没有处理小于1的
        this.size = size;
        this.stack = new int[size];
    }


    public void push(int x) {

        if (top + 1 == size) {
            //TODO 这里需要进行的是栈的扩容机制
            System.out.println(" 栈已满，无法继续添加");
            return;
        }
        //TODO 可与下面的合并为一行
        top++;
        this.stack[top] = x;
    }


    public int pop() {
        if (top == -1) {
            System.out.println("当前栈为空");
            return -1;
        }
        //TODO可与下面的合并一行
        int result = stack[top];
        top--;
        return result;
    }
}
