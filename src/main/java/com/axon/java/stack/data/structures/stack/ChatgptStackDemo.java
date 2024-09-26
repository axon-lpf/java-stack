package com.axon.java.stack.data.structures.stack;


/**
 * 在这里注意  top++ 和 ++top的区别， 以及 --top 和top--的区别
 *
 * 1. top++ 和 ++top 的区别
 *
 * 	•	top++（后置自增）：
 * 	•	先返回变量 top 的当前值，再对 top 进行自增。
 * 	•	也就是说，表达式使用 top 的值之前，top 的值不变，执行完该表达式后才增加 1。
 * 示例：
 *  int top = 5;
 *  int value = top++;  // value = 5, top = 6
 *  在这里，value 被赋值为 5，而 top 在赋值后才变为 6。
 *
 * 	•	++top（前置自增）：
 * 	•	先对变量 top 进行自增，再返回 top 自增后的值。
 * 	•	也就是说，表达式使用 top 的值时，top 已经自增 1。
 * 示例：
 *  int top = 5;
 *  int value = ++top;  // value = 6, top = 6
 *  在这里，value 被赋值为 6，因为 top 先自增到 6，再进行赋值。
 *
 *  2. top-- 和 --top 的区别
 *
 * 	•	top--（后置自减）：
 * 	•	先返回变量 top 的当前值，再对 top 进行自减。
 * 	•	也就是说，表达式使用 top 的值之前，top 的值不变，执行完该表达式后才减少 1。
 * 示例：
 *  int top = 5;
 *  int value = top--;  // value = 5, top = 4
 *  在这里，value 被赋值为 5，而 top 在赋值后才变为 4。
 *
 * 	•	--top（前置自减）：
 * 	•	先对变量 top 进行自减，再返回 top 自减后的值。
 * 	•	也就是说，表达式使用 top 的值时，top 已经自减 1。
 * 示例：
 *  int top = 5;
 *  int value = --top;  // value = 4, top = 4
 *  在这里，value 被赋值为 4，因为 top 先自减到 4，再进行赋值。
 *
 *  总结
 *
 * 	•	top++ 和 top-- 是先使用后增/减，先返回原值再改变值。
 * 	•	++top 和 --top 是先增/减后使用，先改变值再返回新值。
 *
 * 在实际开发中，需要根据表达式对值的使用顺序来选择使用前置或后置的自增/自减。
 *
 *
 */
public class ChatgptStackDemo {


    public static void main(String[] args) {

        ChatgptCustomStack customStack = new ChatgptCustomStack(5);

        customStack.push(10);
        customStack.push(20);
        customStack.push(50);

        customStack.push(100);

        customStack.push(200);

        // 由于栈已满，自动扩容
        customStack.push(300);

        System.out.println("开始取出数据");

        int pop = customStack.pop();
        System.out.println("取出的当前结果: " + pop);

        pop = customStack.pop();
        System.out.println("取出的当前结果: " + pop);

        customStack.push(100);
        customStack.push(200);

        System.out.println("继续取出。。。。");

        pop = customStack.pop();
        System.out.println("取出的当前结果: " + pop);

        pop = customStack.pop();
        System.out.println("取出的当前结果: " + pop);
    }
}


/**
 * chatgpt自定义栈
 */
class ChatgptCustomStack {

    private int[] stack;
    private int top = -1;
    private int size;

    /**
     * 自定义栈
     *
     * @param size 栈的初始大小
     */
    public ChatgptCustomStack(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("栈的大小必须大于0");
        }
        this.size = size;
        this.stack = new int[size];
    }

    /**
     * 入栈操作
     *
     * @param x 要压入栈的值
     */
    public void push(int x) {
        // 如果栈已满，自动扩容
        if (top + 1 == size) {
            resize();
        }
        stack[++top] = x;
    }

    /**
     * 出栈操作
     *
     * @return 栈顶元素
     */
    public int pop() {
        if (top == -1) {
            throw new StackUnderflowException("栈为空，无法执行出栈操作");
        }
        return stack[top--];
    }

    /**
     * 栈的扩容操作，将栈的大小翻倍
     */
    private void resize() {
        size = size * 2;
        int[] newStack = new int[size];
        System.arraycopy(stack, 0, newStack, 0, stack.length);
        stack = newStack;
        System.out.println("栈已扩容，新的大小为: " + size);
    }
}

/**
 * 自定义的栈空异常
 */
class StackUnderflowException extends RuntimeException {
    public StackUnderflowException(String message) {
        super(message);
    }
}