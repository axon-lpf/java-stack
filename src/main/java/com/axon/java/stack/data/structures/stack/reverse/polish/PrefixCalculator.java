package com.axon.java.stack.data.structures.stack.reverse.polish;


import java.util.Stack;

/**
 * 1.	生成前缀表达式：
 * •	将给定的中缀表达式转换为前缀表达式。这个步骤是通过扫描中缀表达式，并根据操作符优先级和括号的顺序进行转换。
 * 2.	将前缀表达式逆序遍历并逐个装入栈中：
 * •	因为前缀表达式是从右向左计算的，所以我们从表达式的最右端开始读取符号或数值。
 * •	将前缀表达式逆序放入数组或栈中，以便从最右边开始逐一处理。
 * 3.	逐一获取前缀表达式中的元素（从右往左）：
 * •	对于每个取出的元素，判断它是操作数（数字）还是操作符（如 +, -, *, /）。
 * 4.	如果取出的元素是操作数：
 * •	操作数直接压入栈。
 * 5.	如果取出的元素是操作符：
 * •	弹出栈中的两个操作数。
 * •	根据操作符的类型（+, -, *, /），对两个操作数进行相应的计算。
 * •	将计算结果压回栈中。
 * 6.	重复步骤 3-5，直到遍历完前缀表达式的所有元素。
 * 7.	最终栈中只剩下一个元素：
 * •	当遍历完所有的前缀表达式后，栈中只剩下一个元素，这个元素就是表达式的计算结果。
 */
public class PrefixCalculator {

    public static void main(String[] args) {
        String infixExpr = "30+(3+(4*50))/2";
        int extracted = extracted(infixExpr);
        System.out.println("最终的结果是" + extracted);
    }

    private static int extracted(String infixExpr) {
        // 这里获取前缀表达式
        String[] result = InfixConvertPrefix.infixToPrefixDirect(infixExpr);
        Stack<Integer> stack = new Stack<>();
        for (int i = result.length - 1; i >= 0; i--) {
            String token = result[i];

            if (InfixConvertPrefix.isOperatorSymbol(token)) {
                //如果是操作符，则取出两个数进行计算
                int operand1 = stack.pop();
                int operand2 = stack.pop();
                int stackResult = calculate(token, operand1, operand2);
                stack.push(stackResult);
            } else {
                //压入栈中
                stack.push(Integer.parseInt(token));
            }

        }
        return stack.pop();
    }


    // 计算两个操作数的结果
    private static int calculate(String operator, int operand1, int operand2) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("无效的操作符: " + operator);
        }
    }
}
