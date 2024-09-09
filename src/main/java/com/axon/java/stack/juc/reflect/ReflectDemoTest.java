package com.axon.java.stack.juc.reflect;

import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/***
 *  反射api学习
 */
interface IReflecttest {
    void test();
}

@Data
class MyReflectDemo implements IReflecttest {

    public MyReflectDemo() {

    }

    public MyReflectDemo(String name) {
        this.name = name;
        System.out.println(name);
    }

    private Integer id;

    public String name;

    private Boolean flag;

    private MyReflectDemo myReflectDemo;


    @Override
    public void test() {

    }

    public String resultParam(String test) {
        System.out.println("测试有参的构造方法" + test);
        return test;
    }

    public String result() {
        System.out.println("我是测试结果方法result");
        return "测试结果";
    }

    private Integer resultInt() {
        System.out.println("我是result INIT方法resultInt");
        return 0;
    }

    public void voidTest() {
        System.out.println("我是测试voidTest");
    }
}

public class ReflectDemoTest {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {

        //1.获取类的信息
        Class<?> aClass = Class.forName("com.axon.java.stack.juc.reflect.MyReflectDemo");
        System.out.println("通过Class.forName获取类信息： " + aClass);

        //或者通过类字面量：
        Class<?> myReflectDemoClass = MyReflectDemo.class;
        System.out.println("通过类字面量获取类信息：" + myReflectDemoClass);
        //创建一个实例对象
        Object o3 = myReflectDemoClass.getDeclaredConstructor().newInstance();


        //或者通过对象的实例获取
        MyReflectDemo myReflectDemo = new MyReflectDemo();
        myReflectDemo.setName("测试");
        myReflectDemo.setId(2000);
        Class<?> demoClass = myReflectDemo.getClass();
        System.out.println("通过对象的实例获取类信息" + demoClass);

        //2.获取类的各种信息
        // 获取类的名称
        String name = demoClass.getName();
        System.out.println("获取类的名称：" + name);

        //获取父类
        Class<?> superclass = demoClass.getSuperclass();
        System.out.println("获取父类的信息：" + superclass);

        //获取接口
        Class<?>[] interfaces = demoClass.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            System.out.println("获取类的接口信息" + interfaces[i]);
        }

        //获取类加载器
        ClassLoader classLoader = demoClass.getClassLoader();
        System.out.println("获取类加载器：" + classLoader);

        //2创建对象的实例, newInstance() 已被弃用， 推荐使用Constructor
        Object o = demoClass.newInstance();
        System.out.println("通过newInstance()创建实例" + o);

        //通过Constructor 创建实例
        Constructor<?> constructor = demoClass.getConstructor();
        Object o1 = constructor.newInstance();
        System.out.println("通过Constructor()创建实例" + o1);

        //有参的构造函数处理
        Constructor<?> constructor2 = demoClass.getConstructor(String.class);
        Object o2 = constructor2.newInstance("测试");
        System.out.println("通过Constructor()创建有参数实例" + o2);



        //3. 获取字段信息

        //获取共有字段
        Field name1 = demoClass.getField("name");
        System.out.println("获取公有字段名称：" + name1);

        //获取私有字段
        Field id = demoClass.getDeclaredField("id");
        System.out.println("获取私有字段名称" + id);

        //设置字段可访问
        name1.setAccessible(true);
        //获取字段的值
        Object name1Value = name1.get(myReflectDemo);
        System.out.println("name1的Value：" + name1Value);

        id.setAccessible(true);
        Object idValue = id.get(myReflectDemo);
        System.out.println("idValue：" + idValue);

        // 修改字段的值

        name1.set(myReflectDemo, "test2222");
        System.out.println(name1.get(myReflectDemo));
        id.set(myReflectDemo, 1000);
        System.out.println(id.get(myReflectDemo));


        //4.调用方法
        // 获取公共方法， 无参数的方法
        Method result = demoClass.getMethod("result");
        System.out.println("获取公共方法结果" + result);

        // 获取公共方法， 有参数的方法
        Method resultParam = demoClass.getMethod("resultParam", String.class);
        System.out.println("获取公共方法结果" + resultParam);

        //获取私有方法， 无参数的方法
        Method resultInt = demoClass.getDeclaredMethod("resultInt");
        System.out.println("获取私有方法结果：" + resultInt);

        //5.调用方法
        result.invoke(myReflectDemo);

        resultParam.invoke(myReflectDemo, "阿西吧");

        resultInt.setAccessible(true); //设置有权限访问
        resultInt.invoke(myReflectDemo);


        //获取构造函数
        Constructor<?> demoClassConstructor = demoClass.getConstructor(String.class);
        //调用构造函数
        Object newInstance = demoClassConstructor.newInstance("测试调用构造函数");


        //7.注解处理
        // 获取方法的注解
        Annotation[] annotations = result.getAnnotations();
        //住区注解的集合
        for (Annotation annotation : demoClassConstructor.getAnnotations()) {

            if (annotation instanceof Object) {
                //逻辑处理
            }
        }

        // 获取的案例
        // PostMapping annotation = result.getAnnotation(PostMapping.class);


    }
}
