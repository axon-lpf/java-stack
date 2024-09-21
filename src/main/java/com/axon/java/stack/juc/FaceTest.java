package com.axon.java.stack.juc;

public class FaceTest {
    /**
     *  /**
     *      *  对称加密  :使用相同的密钥进行加密和解密。  速度快，适合大数据量的加密 ， 安全性，秘钥必须安全储存
     *      *  AES
     *      *  DES
     *      *
     *      *  非对称加密: 使用一对密钥（公钥和私钥），公钥加密，私钥解密。  速度慢，适合小数据量加密 ， 安全性，公钥可以公开，私钥必须保密，安全性依赖于密钥长度和算法复杂度。
     *      *  RSA
     *      *  DSA
     *      *
     *      *  哈希加密 :不使用密钥，仅用于数据完整性验证和数字签名，  速度非常快，适合数据的完整性验证。安全性依赖于抗碰撞性和不可逆性。
     *      *  MD5
     *      *
     *      *
     *      *  GC回收算法： 复制算法， 标记清楚算法、标记压缩算 ， 标记清除压缩算
     *      *
     *      *
     *      *  HashMap扩容原理， 负载因子是0.75， 默认是16，当超过这个值是会自动扩容至其的两倍。
     *      *  HashMap扩容主要是给数组扩容的，因为数组长度不可变，而链表是可变长度的。从HashMap的源码中可以看到HashMap在扩容时选择了位运算，向集合中添加元素时，会使用(n - 1) & hash的计算方法来得出该元素在集合中的位置
     *
     *      *  在jDK1.8中新增加了红黑树，即在数组长度大于64，同时链表长度大于8的情况下，链表将转化为红黑树。同时使用尾插法。当数据的长度退化成6时，红黑树转化为链表。
     *
     *      数组长度大于64时，同时链表大于8的情况下，链表会转换为红黑树。 当数据退化成6时，红黑树会转换为链表
     *
     *      1.	触发扩容条件：当 HashMap 中的元素数量超过负载因子（load factor）与当前容量（capacity）的乘积时，会触发扩容。默认的负载因子是 0.75，也就是说，当元素数量达到容量的 75% 时会进行扩容。
     *      2.	新建数组：扩容时，HashMap 会创建一个新的数组，新数组的容量是原数组容量的两倍。
     *      3.	重新计算索引：将旧数组中的每个元素重新计算哈希值，并将它们放到新数组中的正确位置上。由于数组容量增加，哈希值的计算结果可能会不同，因此元素的位置也可能发生变化。
     *      4.	重新分配元素：将旧数组中的元素一个个迁移到新数组中，这个过程称为 rehashing（重新散列）。每个元素都会根据新的数组容量重新计算其存储位置。
     *      5.	更新容量信息：更新 HashMap 的容量信息，以反映新的数组大小。
     *
     *      HashMap 通过这种方式来保持存储的元素均匀分布，减少哈希冲突，并提高查找效率。扩容的过程会消耗一些性能，特别是在元素数量较多时，所以在使用 HashMap 时需要权衡初始容量设置和负载因子，以减少不必要的扩容操作。*
     */
}