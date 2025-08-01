# 🔄 **G1 垃圾收集器去重操作深度解析**

## 🎯 **G1 去重操作概述**

G1的去重操作主要指 **String去重 (String Deduplication)**，这是G1垃圾收集器的一个重要优化特性，用于**减少堆内存中重复字符串对象的内存占用**。

## 📊 **字符串重复问题的现状**

```java
// 典型的字符串重复场景
public class StringDuplicationProblem {
    
    public static void main(String[] args) {
        // 模拟真实应用中的字符串重复情况
        demonstrateStringDuplication();
    }
    
    private static void demonstrateStringDuplication() {
        System.out.println("=== 字符串重复问题演示 ===");
        
        // 场景1: JSON解析产生的重复字符串
        List<User> users = parseUsersFromJson();
        
        // 场景2: 配置文件读取产生的重复
        Properties config = loadConfiguration();
        
        // 场景3: 数据库查询结果的重复字段
        List<Order> orders = queryOrdersFromDatabase();
        
        // 💡 在这些场景中，大量字符串内容相同但对象不同
        analyzeStringDuplication(users, config, orders);
    }
    
    private static void analyzeStringDuplication(List<User> users, 
                                               Properties config, 
                                               List<Order> orders) {
        
        Map<String, Integer> duplicateCount = new HashMap<>();
        
        // 统计重复的字符串
        for (User user : users) {
            countDuplicate(duplicateCount, user.getStatus());    // "ACTIVE", "INACTIVE"
            countDuplicate(duplicateCount, user.getDepartment()); // "IT", "SALES", "HR"
            countDuplicate(duplicateCount, user.getCity());      // "Beijing", "Shanghai"
        }
        
        System.out.println("字符串重复统计:");
        duplicateCount.forEach((str, count) -> {
            if (count > 1) {
                System.out.println("'" + str + "' 重复了 " + count + " 次");
                System.out.println("  浪费内存: " + (count - 1) * estimateStringSize(str) + " bytes");
            }
        });
        
        // 💡 结果显示：大量内存被重复字符串浪费
        System.out.println("✅ G1 String去重可以解决这个问题！");
    }
    
    private static void countDuplicate(Map<String, Integer> map, String str) {
        map.merge(str, 1, Integer::sum);
    }
    
    private static int estimateStringSize(String str) {
        // String对象头 + char[]数组 + 字符数据
        return 16 + 16 + str.length() * 2;
    }
}
```

## 🏗️ **G1 String去重架构图**

```
┌─────────────────────────────────────────────────────────────┐
│                    G1 String 去重完整流程                    │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │              1. 对象分配阶段                             │ │
│  │                                                         │ │
│  │  String s1 = new String("Hello");  ┌─────────────────┐   │ │
│  │  String s2 = new String("Hello");  │  堆内存中       │   │ │
│  │  String s3 = new String("World");  │                 │   │ │
│  │                                    │ ┌─────────────┐ │   │ │
│  │                                    │ │String@1001  │ │   │ │
│  │                                    │ │value="Hello"│ │   │ │
│  │                                    │ └─────────────┘ │   │ │
│  │                                    │ ┌─────────────┐ │   │ │
│  │                                    │ │String@1002  │ │   │ │
│  │                                    │ │value="Hello"│ │   │ │
│  │                                    │ └─────────────┘ │   │ │
│  │                                    │ ┌─────────────┐ │   │ │
│  │                                    │ │String@1003  │ │   │ │
│  │                                    │ │value="World"│ │   │ │
│  │                                    │ └─────────────┘ │   │ │
│  │                                    └─────────────────┘   │ │
│  └─────────────────────────────────────────────────────────┘ │
│                              ↓                             │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │              2. G1 GC 扫描阶段                          │ │
│  │                                                         │ │
│  │  G1 在GC过程中扫描String对象：                          │ │
│  │                                                         │ │
│  │  ┌─────────────────────────────────────────────────────┐ │ │
│  │  │          去重候选对象识别                            │ │ │
│  │  │                                                   │ │ │
│  │  │  ✅ String@1001: age >= threshold (默认3)          │ │ │
│  │  │  ✅ String@1002: age >= threshold                  │ │ │
│  │  │  ❌ String@1003: 刚创建，age < threshold            │ │ │
│  │  │                                                   │ │ │
│  │  │  筛选条件:                                         │ │ │
│  │  │  • 对象年龄 >= StringDeduplicationAgeThreshold    │ │ │
│  │  │  • 字符串长度合理（不是极短或极长）                │ │ │
│  │  │  • 不在字符串常量池中                              │ │ │
│  │  └─────────────────────────────────────────────────────┘ │ │
│  └─────────────────────────────────────────────────────────┘ │
│                              ↓                             │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │           3. 去重哈希表构建                             │ │
│  │                                                         │ │
│  │  ┌─────────────────────────────────────────────────────┐ │ │
│  │  │            Deduplication Table                      │ │ │
│  │  │                                                   │ │ │
│  │  │  Hash("Hello") → char[]{'H','e','l','l','o'} ✅   │ │ │
│  │  │  Hash("World") → char[]{'W','o','r','l','d'}      │ │ │
│  │  │  Hash("Java")  → char[]{'J','a','v','a'}          │ │ │
│  │  │  ...                                              │ │ │
│  │  │                                                   │ │ │
│  │  │  特点：                                            │ │ │
│  │  │  • 弱引用存储char[]数组                            │ │ │
│  │  │  • 基于内容哈希的快速查找                          │ │ │
│  │  │  • 自动清理未使用的条目                            │ │ │
│  │  └─────────────────────────────────────────────────────┘ │ │
│  └─────────────────────────────────────────────────────────┘ │
│                              ↓                             │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │              4. 去重执行阶段                            │ │
│  │                                                         │ │
│  │  对于String@1001和String@1002 (都是"Hello"):            │ │
│  │                                                         │ │
│  │  Before去重:                After去重:                  │ │
│  │  ┌─────────────┐              ┌─────────────┐            │ │
│  │  │String@1001  │              │String@1001  │            │ │
│  │  │value ──────→┼────┐         │value ──────→┼──────┐     │ │
│  │  └─────────────┘    │         └─────────────┘      │     │ │
│  │  ┌─────────────┐    ↓         ┌─────────────┐      │     │ │
│  │  │String@1002  │ ┌────────┐   │String@1002  │      │     │ │
│  │  │value ──────→┼→│char[]  │   │value ──────→┼──────┤     │ │
│  │  └─────────────┘ │"Hello" │   └─────────────┘      │     │ │
│  │                  └────────┘                        ↓     │ │
│  │                  ┌────────┐                    ┌────────┐ │ │
│  │                  │char[]  │                    │char[]  │ │ │
│  │                  │"Hello" │← 重复,被GC回收      │"Hello" │ │ │
│  │                  └────────┘                    └────────┘ │ │
│  │                                                         │ │
│  │  💡 结果: 两个String对象共享同一个char[]数组              │ │
│  └─────────────────────────────────────────────────────────┘ │
│                              ↓                             │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │               5. 内存回收阶段                           │ │
│  │                                                         │ │
│  │  重复的char[]数组在下次GC时被回收:                       │ │
│  │                                                         │ │
│  │  • 节省内存空间                                         │ │
│  │  • 提高缓存局部性                                       │ │
│  │  • 减少GC压力                                          │ │
│  │                                                         │ │
│  │  统计信息更新:                                          │ │
│  │  ✅ Total Deduplicated: 1                              │ │
│  │  ✅ Bytes Saved: 10 bytes                              │ │
│  │  ✅ Success Rate: 50%                                  │ │
│  └─────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

## ⚙️ **G1 String去重的核心实现机制**

### **1. 去重触发条件**

```java
// G1 去重触发的条件判断伪代码
public class G1StringDeduplicationTrigger {
    
    private static final int DEFAULT_AGE_THRESHOLD = 3;
    private static final int MIN_STRING_LENGTH = 8;
    private static final int MAX_STRING_LENGTH = 1024;
    
    /**
     * 判断String对象是否适合去重
     */
    public static boolean isDeduplicationCandidate(StringObject str) {
        // 1. 对象年龄检查 - 避免对新创建的对象去重
        if (str.getAge() < DEFAULT_AGE_THRESHOLD) {
            return false;  // 太新，可能很快就被回收
        }
        
        // 2. 字符串长度检查 - 避免处理过短或过长的字符串
        int length = str.getValue().length;
        if (length < MIN_STRING_LENGTH || length > MAX_STRING_LENGTH) {
            return false;  // 效果不明显或成本太高
        }
        
        // 3. 常量池检查 - 常量池中的字符串不需要去重
        if (str.isInStringPool()) {
            return false;  // 常量池字符串已经是去重的
        }
        
        // 4. 已去重检查 - 避免重复处理
        if (str.isAlreadyDeduplicated()) {
            return false;  // 已经被去重过了
        }
        
        return true;
    }
    
    /**
     * G1 GC过程中的去重调度
     */
    public static void scheduleDeduplication() {
        System.out.println("=== G1 去重调度逻辑 ===");
        
        // 在以下GC阶段执行去重:
        // 1. Young GC后的并发标记阶段
        // 2. Mixed GC过程中
        // 3. Full GC过程中
        
        if (isDeduplicationEnabled() && shouldRunDeduplication()) {
            executeStringDeduplication();
        }
    }
    
    private static boolean isDeduplicationEnabled() {
        // -XX:+UseStringDeduplication
        return System.getProperty("UseStringDeduplication", "false").equals("true");
    }
    
    private static boolean shouldRunDeduplication() {
        // 基于堆使用率、去重效果等决定
        double heapUsageRatio = getCurrentHeapUsage();
        double lastDeduplicationEffectiveness = getLastDeduplicationStats();
        
        return heapUsageRatio > 0.6 && lastDeduplicationEffectiveness > 0.1;
    }
}
```

### **2. 去重哈希表的实现**

```java
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

// G1 去重哈希表的核心实现
public class StringDeduplicationTable {
    
    // 使用弱引用存储char[]数组，允许GC自动清理
    ```java
    private ConcurrentHashMap<Integer, WeakReference<char[]>> deduplicationTable;
    
    public StringDeduplicationTable() {
        this.deduplicationTable = new ConcurrentHashMap<>();
    }
    
    /**
     * 尝试去重字符串
     */
    public char[] deduplicate(String str) {
        char[] value = str.getValue();
        int hashCode = calculateHash(value);
        
        // 1. 查找已存在的相同内容
        WeakReference<char[]> existingRef = deduplicationTable.get(hashCode);
        if (existingRef != null) {
            char[] existing = existingRef.get();
            if (existing != null && arraysEqual(value, existing)) {
                // ✅ 找到相同内容，返回已存在的数组
                return existing;
            } else {
                // 弱引用已被回收，移除条目
                deduplicationTable.remove(hashCode);
            }
        }
        
        // 2. 首次遇到此内容，加入哈希表
        deduplicationTable.put(hashCode, new WeakReference<>(value));
        return value;
    }
    
    private int calculateHash(char[] array) {
        // 基于内容的哈希计算
        int hash = 0;
        for (char c : array) {
            hash = 31 * hash + c;
        }
        return hash;
    }
}
```

## 🎛️ **G1 去重相关JVM参数**

```bash
# 启用G1 String去重
-XX:+UseG1GC
-XX:+UseStringDeduplication

# 去重相关调优参数
-XX:StringDeduplicationAgeThreshold=3     # 对象年龄阈值(默认3)
-XX:+PrintStringDeduplicationStatistics  # 打印去重统计信息
-XX:StringDeduplicationTableSizeLimit=1000000  # 哈希表大小限制
```

## 📈 **去重效果监控示例**

```java
// 监控去重效果
public class DeduplicationMonitor {
    public static void printStats() {
        System.out.println("=== G1 String去重统计 ===");
        System.out.println("Total Inspected: 1,000,000 strings");
        System.out.println("Total Deduplicated: 250,000 strings");  
        System.out.println("Memory Saved: 15.2 MB");
        System.out.println("Success Rate: 25%");
        System.out.println("✅ 显著减少了内存占用");
    }
}
```

## 🎯 **核心总结**

### **✅ G1去重的优势**
- **自动化**: 无需代码修改，JVM自动处理
- **智能化**: 基于对象年龄和使用模式的智能选择
- **安全性**: 使用弱引用，不影响正常GC
- **透明性**: 对应用程序完全透明

### **⚠️ 注意事项**
- **CPU开销**: 去重过程消耗额外CPU
- **适用场景**: 主要适合有大量重复字符串的应用
- **效果依赖**: 重复度越高效果越明显

**G1的去重操作通过智能识别重复字符串内容，让多个String对象共享同一个char[]数组，从而显著减少内存使用，是现代JVM内存优化的重要技术！**