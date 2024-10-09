package com.axon.java.stack.data.structures.stack.reverse.polish;

import java.util.Stack;

/**
 * 中缀表达式转换为后缀表达式
 * <p>
 * 30+(3+(4*50))/2
 * 转换为后缀表达式思路：
 * 操作符栈， 一个结果栈
 * <p>
 * 数栈： 3, 50 4 * +,, 3,30,
 * 操作符栈：/,+
 * <p>
 * 最终结果： 30 3 4 50 * + 2 / +
 */
public class InfixConvertSuffix {

    public static void main(String[] args) {
        String infixExpr = "30+(3+(4*50))/2";

        String[] result = infixToSuffixDirect(infixExpr);

        // 输出前缀表达式数组
        for (String s : result) {
            System.out.print(s + " ");
        }
    }

    public static String[] infixToSuffixDirect(String expr) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            //如果是数字则直接拼接
            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                // 处理多位数
                while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
                    // 使用append将数字添加到StringBuilder
                    sb.append(expr.charAt(i));
                    i++;  // 向前移动索引
                }
                i--; // 调整索引位置，回到最后一个数字的位置
                // 将数字添加到结果中并加空格
                result.append(sb).append(' ');

            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop()).append(' ');
                }
                // 弹出左边括号
                if (!stack.isEmpty()) {
                    stack.pop(); // 确保栈不空后弹出左括号
                }
            }
            //如果是操作符
            else if (isOperatorSymbol(String.valueOf(c))) {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    result.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(' ');
        }

        return result.toString().split(" ");
    }

    /**
     * 判断是不是操作符
     *
     * @param token
     * @return
     */
    public static boolean isOperatorSymbol(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }


    /**
     * 这里获取操作符的优先级
     *
     * @param operator
     * @return
     */
    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }
}
