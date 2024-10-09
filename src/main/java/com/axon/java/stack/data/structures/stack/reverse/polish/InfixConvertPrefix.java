package com.axon.java.stack.data.structures.stack.reverse.polish;

import java.util.Stack;

/**
 * 中缀表达式转换为前缀表达式的具体思路
 * 1.	反向遍历中缀表达式（从右向左扫描字符）：
 * •	先读取最右边的字符，逐个向左扫描。
 * •	遇到操作数时，直接加入操作数栈。
 * •	遇到操作符时，检查操作符栈中的优先级情况，优先级较低的操作符先出栈并与操作数组合，再将当前操作符压入操作符栈。
 * •	遇到右括号时，压入操作符栈；遇到左括号时，开始弹出栈内的操作符，直到遇到右括号为止。
 * 2.	构建前缀表达式：
 * •	操作数和操作符按优先级处理，最终将生成的操作符和操作数组合为前缀表达式。
 * <p>
 * 具体的步骤例子：
 * <p>
 * 假设有一个中缀表达式：(3 + (4 * 5) - 2)
 * <p>
 * 步骤：
 * <p>
 * 1.	从右向左遍历：
 * •	遇到 2，将 2 压入操作数栈。
 * •	遇到 -，将 - 压入操作符栈。
 * •	遇到 )，压入操作符栈。
 * •	遇到 5，将 5 压入操作数栈。
 * •	遇到 *，将 * 压入操作符栈。
 * •	遇到 4，将 4 压入操作数栈。
 * •	遇到 (，开始弹出操作符栈，直到遇到 ) 为止。弹出 *，将 4 * 5 组合成 * 4 5，并压入操作数栈。
 * •	遇到 +，将 + 压入操作符栈。
 * •	遇到 3，将 3 压入操作数栈。
 * •	遇到 (，开始弹出操作符栈，直到遇到 ) 为止。弹出 +，将 3 + * 4 5 组合成 + 3 * 4 5。
 * •	最后弹出 -，将 + 3 * 4 5 - 2 组合成 - + 3 * 4 5 2。
 * 2.	前缀表达式结果：- + 3 * 4 5 2
 * <p>
 * 注意：
 * <p>
 * •	遇到括号时，左右括号的作用是控制表达式的优先级。
 * •	在中缀转前缀的过程中，操作符的优先级决定操作顺序，需要通过栈来管理操作符的弹出和压入操作。
 * <p>
 * 小结：
 * <p>
 * 中缀表达式转换为前缀表达式的思路与后缀表达式类似，主要区别是：
 * <p>
 * •	遍历方向从右向左，而不是从左向右。
 * •	操作符在操作数的前面组合，而不是在操作数的后面。
 * <p>
 * 30+(3+(4*50))/2
 * <p>
 * 操作数：+ 30,/, + 3, * 4 50, 2,
 * 操作符：
 * <p>
 * 结果： + 30 / + 3 * 4 50 2
 */
public class InfixConvertPrefix {


    public static void main(String[] args) {

        String infixExpr = "30+(3+(4*50))/2";

        String[] result = infixToPrefixDirect(infixExpr);

        // 输出前缀表达式数组
        for (String s : result) {
            System.out.print(s + " ");
        }

    }


    public static String[] infixToPrefixDirect(String expr) {
        //这是操作符栈
        Stack<Character> operatorSymbol = new Stack<>();
        // 这是操作数栈
        Stack<String> operandNumber = new Stack<>();

        // 这是从右向左边扫描
        for (int i = expr.length() - 1; i >= 0; i--) {
            char c = expr.charAt(i);

            //如果是操作数，则直接入栈
            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i >= 0 && Character.isDigit(expr.charAt(i))) {
                    sb.insert(0, expr.charAt(i));
                    i--;
                }
                i++;  // 纠正索引位置
                operandNumber.push(sb.toString());
            }
            //如果是右边的括号，则入栈
            else if (c == ')') {
                operatorSymbol.push(c);
            }
            //如果是左边的括号，则处理括号里边的数据
            else if (c == '(') {
                // 循环取出操作符括号里面的数据进行计算，然后入栈
                while (!operatorSymbol.isEmpty() && operatorSymbol.peek() != ')') {
                    String operand1 = operandNumber.pop();
                    String operand2 = operandNumber.pop();
                    Character operator = operatorSymbol.pop();
                    String combinde = operator + " " + operand1 + " " + operand2;
                    operandNumber.push(combinde);
                }
                //这里需要弹出右边的括号
                operatorSymbol.pop();
            }
            //如果是操作符
            else if (isOperatorSymbol(String.valueOf(c))) {
                //如果操作符不为空， 则判断操作符的优先级, 这里是多位数的操作
                while (!operatorSymbol.isEmpty() && precedence(operatorSymbol.peek()) >= precedence(c)) {
                    String operand1 = operandNumber.pop();
                    String operand2 = operandNumber.pop();
                    Character operator = operatorSymbol.pop();
                    String combined = operator + " " + operand1 + " " + operand2;
                    operandNumber.push(combined);
                }
                operatorSymbol.push(c);
            }
        }
        //把栈中剩余的操作符和操作数组合起来
        while (!operatorSymbol.isEmpty()) {
            String operand1 = operandNumber.pop();
            String operand2 = operandNumber.pop();
            Character operator = operatorSymbol.pop();
            String combined = operator + " " + operand1 + " " + operand2;
            operandNumber.push(combined);
        }
        return operandNumber.pop().split(" ");
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
