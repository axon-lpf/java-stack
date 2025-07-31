# 🔍 **`String s3=x+"b"` 是否会放入常量池？**

## ❌ **答案：不会自动放入常量池**

```java
private static String x = "a";

public static void demo04(){
    String s3 = x + "b";  // ❌ 不会自动进入常量池
}
```

## 🎯 **详细解释**

### **1. 编译器处理过程**

```java
// 源代码
String s3 = x + "b";

// 编译器转换为（字节码等效）
String s3 = new StringBuilder()
    .append(x)
    .append("b")
    .toString();
```

### **2. StringBuilder.toString() 源码分析**

```java
// StringBuilder.toString() 方法实现
@Override
public String toString() {
    // Create a copy, don't share the array
    return new String(value, 0, count);  // 🔑 关键：直接new String()
}
```

## 🗺️ **内存分配过程图**

```
┌─────────────────────────────────────────────────────────────┐
│                    执行 x + "b" 的内存变化                    │
├─────────────────────────────────────────────────────────────┤
│                         堆内存 (Heap)                        │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │  Step 1: 创建 StringBuilder 对象                        │ │
│  │  ┌─────────────────────────────────────────────────────┤ │
│  │  │  StringBuilder@3001                                │ │
│  │  │  value: char[16]                                   │ │
│  │  └─────────────────────────────────────────────────────┤ │
│  │                                                         │ │
│  │  Step 2: append(x) → append("a")                      │ │
│  │  ┌─────────────────────────────────────────────────────┤ │
│  │  │  StringBuilder@3001                                │ │
│  │  │  value: ['a', '\0', '\0', ...]                     │ │
│  │  └─────────────────────────────────────────────────────┤ │
│  │                                                         │ │
│  │  Step 3: append("b")                                  │ │
│  │  ┌─────────────────────────────────────────────────────┤ │
│  │  │  StringBuilder@3001                                │ │
│  │  │  value: ['a', 'b', '\0', ...]                      │ │
│  │  └─────────────────────────────────────────────────────┤ │
│  │                                                         │ │
│  │  Step 4: toString() → new String()                    │ │
│  │  ┌─────────────────────────────────────────────────────┤ │
│  │  │  String@3002 ←──── s3 指向这里                     │ │
│  │  │  value: ['a', 'b']  ⚠️ 在堆中，不在常量池          │ │
│  │  └─────────────────────────────────────────────────────┤ │
│  └─────────────────────────────────────────────────────────┘ │
│                                                             │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │              字符串常量池 (StringTable)                  │ │
│  │  ┌─────────────────────────────────────────────────────┤ │
│  │  │  "a" → String@3003 (x变量指向)                     │ │
│  │  │  "b" → String@3004 (字面量)                        │ │
│  │  │  ❌ 没有 "ab" 的记录                                │ │
│  │  └─────────────────────────────────────────────────────┤ │
│  └─────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

## 📊 **对比不同的字符串创建方式**

| 创建方式 | 是否进常量池 | 内存位置 | 示例 |
|----------|--------------|----------|------|
| 字面量 | ✅ 自动进入 | 常量池 | `String s = "ab";` |
| 编译期常量拼接 | ✅ 自动进入 | 常量池 | `final String a="a"; String s=a+"b";` |
| 运行期变量拼接 | ❌ 不进入 | 堆内存 | `String a="a"; String s=a+"b";` |
| new String() | ❌ 不进入 | 堆内存 | `String s = new String("ab");` |

## 🧪 **验证代码**

```java
public class StringPoolTest {
    private static String x = "a";
    
    public static void main(String[] args) {
        // 运行期拼接，不进常量池
        String s1 = x + "b";        // 堆中创建
        
        // 字面量，自动进常量池
        String s2 = "ab";           // 常量池获取
        
        // 验证是否为同一对象
        System.out.println("s1 == s2: " + (s1 == s2));  // false
        System.out.println("s1.equals(s2): " + (s1.equals(s2)));  // true
        
        // 🔑 通过 intern() 将 s1 加入常量池
        String s1Intern = s1.intern();
        System.out.println("s1.intern() == s2: " + (s1Intern == s2));  // true
        
        // 验证内存地址
        System.out.println("s1 hashCode: " + System.identityHashCode(s1));
        System.out.println("s2 hashCode: " + System.identityHashCode(s2));
        System.out.println("s1.intern() hashCode: " + System.identityHashCode(s1Intern));
    }
}
```

## 🔄 **如何将运行期创建的字符串放入常量池？**

```java
private static String x = "a";

public static void demo() {
    // Step 1: 运行期拼接，创建堆对象
    String s3 = x + "b";           // 堆中@3002
    
    // Step 2: 显式调用 intern() 放入常量池
    String s3Intern = s3.intern(); // 常量池中存储 "ab"
    
    // Step 3: 后续字面量获取常量池引用
    String s4 = "ab";              // 从常量池获取
    
    System.out.println(s3 == s4);        // false (s3在堆，s4在常量池)
    System.out.println(s3Intern == s4);  // true  (都指向常量池)
}
```

## 📈 **性能影响**

```java
// ❌ 低效：每次都创建新对象
for (int i = 0; i < 1000; i++) {
    String s = x + "suffix";  // 1000个不同的堆对象
}

// ✅ 高效：复用StringBuilder
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.setLength(0);  // 重置
    sb.append(x).append("suffix");
    String s = sb.toString();
}

// ✅ 最高效：如果是已知字符串，直接使用常量
final String X = "a";  // 编译期常量
String s = X + "suffix";  // 编译期优化为 "asuffix"
```

## 🎯 **核心总结**

1. **`String s3 = x + "b"` 不会自动进入常量池**
2. **StringBuilder.toString() 总是在堆中创建新对象**
3. **只有字面量和编译期常量拼接才会自动进入常量池**
4. **要将运行期创建的字符串放入常量池，需要显式调用 `intern()` 方法**

**记住：只有编译器能确定的字符串值才会自动进入常量池！**