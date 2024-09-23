package com.axon.java.stack.juc.oom;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 元数据空间溢出
 * @author lingxiao
 *
 * JVM参数
 * * -XX:Metaspacesize=8m -XX:MaxMetaspaceSize=8m
 * Java 8及之后的版本使用Metaspace来替代永久代。
 * *
 * * Metaspace是方法区在HotSpot中的实现，它与持久代最大的区别在于：Metaspace并不在處拟机内存中而是使用本地内存
 * *也即在java8中，Cldsse metadata（the virtual machines internal presentation of Java CLass），被存儲在叫做
 * * Metaspace linative memory
 * *
 * *永久代 java8后被原空阿Metaspace取代了）存放了以下信息：
 * *
 * * 虚拟机加载的类信息
 * * 常量池
 * * 静态变量
 * * 即时编译后的代码
 * *
 * * 模拟metaspace空间溢出，我们不断生成类往元空问灌，类占据的空间总是会超过metaspace指定的空间大小的
 *
 */
public class MeteDataSpaceOverflowTest {

    public static void main(String[] args) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            while (true) {
                // 动态生成一个新的类
                String className = "com.example.DynamicClass" + System.currentTimeMillis();
                Class<?> dynamicClass = generateClass(className);
                classes.add(dynamicClass);

                //打印生成的类名
                System.out.println("Generated class: " + className);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("Metaspace overflow occurred.");
        }

    }

    private static Class<?> generateClass(String className) throws Exception {
        // 使用Java反射生成一个新的类
        String classSource = "public class " + className + " { }";
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileObject fileObject = new SimpleJavaFileObject(
                URI.create("string:///" + className.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension),
                JavaFileObject.Kind.SOURCE
        ) {
            @Override
            public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                return classSource;
            }
        };

        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(fileObject);
        compiler.getTask(null, null, null, null, null, compilationUnits).call();

        // 使用Java反射加载新类
        return Class.forName(className);
    }

}
