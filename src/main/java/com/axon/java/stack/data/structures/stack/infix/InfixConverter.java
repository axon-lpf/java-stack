package com.axon.java.stack.data.structures.stack.infix;

import java.util.*;

public class InfixConverter {

    public static void main(String[] args) {
        String infixExpression = "(3 + 4) * 5 - 6 / 2";

        System.out.println("中缀表达式: " + infixExpression);

        String postfixExpression = infixToPostfix(infixExpression);
        System.out.println("后缀表达式: " + postfixExpression);

        String prefixExpression = infixToPrefix(infixExpression);
        System.out.println("前缀表达式: " + prefixExpression);
    }

    // 中缀表达式转后缀表达式
    public static String infixToPostfix(String infix) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        Map<Character, Integer> precedence = getPrecedenceMap();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            // 如果字符是空格，跳过
            if (c == ' ') {
                continue;
            }

            // 如果是操作数（数字）
            if (Character.isDigit(c)) {
                result.append(c).append(' ');
            }
            // 左括号直接入栈
            else if (c == '(') {
                stack.push(c);
            }
            // 遇到右括号，将栈内元素弹出直到遇到左括号
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop()).append(' ');
                }
                stack.pop(); // 弹出 '('
            }
            // 如果是操作符
            else {
                while (!stack.isEmpty() && precedence.get(stack.peek()) >= precedence.get(c)) {
                    result.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }

        // 将栈中剩余的操作符弹出
        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(' ');
        }

        return result.toString().trim();
    }

    // 中缀表达式转前缀表达式
    public static String infixToPrefix(String infix) {
        // 1. 反转中缀表达式
        String reversedInfix = reverseExpression(infix);

        // 2. 转换为后缀表达式
        String postfix = infixToPostfix(reversedInfix);

        // 3. 反转后缀表达式，得到前缀表达式
        String prefix = reverseExpression(postfix);

        return prefix;
    }

    // 反转表达式，且替换括号
    private static String reverseExpression(String expression) {
        StringBuilder reversed = new StringBuilder();
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);

            // 反转括号
            if (c == '(') {
                reversed.append(')');
            } else if (c == ')') {
                reversed.append('(');
            } else {
                reversed.append(c);
            }
        }
        return reversed.toString();
    }

    // 操作符优先级定义
    private static Map<Character, Integer> getPrecedenceMap() {
        Map<Character, Integer> precedence = new HashMap<>();
        precedence.put('+', 1);
        precedence.put('-', 1);
        precedence.put('*', 2);
        precedence.put('/', 2);
        precedence.put('(', 0); // 左括号优先级最低
        return precedence;
    }
}