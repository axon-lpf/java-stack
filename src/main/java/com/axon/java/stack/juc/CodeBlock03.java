package com.axon.java.stack.juc;

class CodeZy {

    {
        System.out.println("我是CodeZy的构造块");
    }

    static {
        System.out.println("我是CodeZy的静态代码块。。。。。。。");
    }

    public CodeZy() {
        System.out.println("我是CodeZy的构造构造方法");
    }
}


public class CodeBlock03 {
    {
        System.out.println("CodeBlock03的构造块");
    }

    static {
        System.out.println("CodeBlock03静态代码块");
    }

    public CodeBlock03() {
        System.out.println("我是CodeBlock03的构造方法");
    }

    /**
     *  考察他们的运行顺序，原则静态先行=>构造块=>构造函数
     *  且静态加载一次。
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("我是美丽的分割线--------");
        new CodeZy();
        System.out.println("........");
        new CodeZy();
        System.out.println("...............");
        new CodeBlock03();

    }
}
