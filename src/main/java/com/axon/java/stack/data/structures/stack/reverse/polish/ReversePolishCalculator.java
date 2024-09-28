package com.axon.java.stack.data.structures.stack.reverse.polish;


import java.util.Stack;

/**
 * 逆波兰计算器
 */
public class ReversePolishCalculator {

    public static void main(String[] args) {
        String infixExpr = "30+(3+(4*50))/2";
        int extracted = extracted(infixExpr);
        System.out.println("最终的结果是" + extracted);
    }


    private static int extracted(String infixExpr) {
        // 这里获取前缀表达式
        String[] result = InfixConvertSuffix.infixToSuffixDirect(infixExpr);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < result.length; i++) {
            String token = result[i];

            if (InfixConvertSuffix.isOperatorSymbol(token)) {
                //如果是操作符，则取出两个数进行计算
                int operand1 = stack.pop();
                int operand2 = stack.pop();
                int stackResult = calculate(token, operand2, operand1);
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
