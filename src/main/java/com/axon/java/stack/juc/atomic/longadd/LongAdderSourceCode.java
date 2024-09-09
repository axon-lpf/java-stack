package com.axon.java.stack.juc.atomic.longadd;

public class LongAdderSourceCode {


    /**
     *
     * public void accumulate(long x) {
     *         Cell[] as; long b, v, r; int m; Cell a;
     *         if ((as = cells) != null ||
     *             (r = function.applyAsLong(b = base, x)) != b && !casBase(b, r)) {
     *             boolean uncontended = true;
     *             if (as == null || (m = as.length - 1) < 0 ||
     *                 (a = as[getProbe() & m]) == null ||
     *                 !(uncontended =
     *                   (r = function.applyAsLong(v = a.value, x)) == v ||
     *                   a.cas(v, r)))
     *                 longAccumulate(x, function, uncontended);
     *         }
     *     }
     *
     * 代码：
     * Cell[] as; long b, v, r; int m; Cell a;
     *
     * 解析：
     *      as: 引用 cells 数组，cells 数组用于存放多个 Cell 实例，以减少线程竞争。
     * 	•	b: 临时变量，用于存储 base 的当前值（base 是 LongAccumulator 的初始值或之前的累加结果）。
     * 	•	v: 临时变量，用于存储 Cell 的当前值。
     * 	•	r: 临时变量，用于存储累加计算后的结果。
     * 	•	m: 临时变量，用于存储 cells 数组的长度减1（即数组最后一个索引值）。
     * 	•	a: 临时变量，用于存储当前线程要操作的 Cell 对象。
     *
     * 代码：
     * 	if ((as = cells) != null ||
     *     (r = function.applyAsLong(b = base, x)) != b && !casBase(b, r)) {
     *
     * 解析：
     *      (as = cells) != null: 检查 cells 数组是否已经初始化（非空），如果已初始化，则进入代码块。
     * 	•	(r = function.applyAsLong(b = base, x)) != b: 如果 cells 数组未初始化，计算 base 与 x 累加后的结果，并检查是否发生变化。function.applyAsLong 是累加函数。
     * 	•	!casBase(b, r): 如果 base 的值有变化，尝试使用 CAS（Compare-And-Swap）操作更新 base 的值，如果更新失败，也进入代码块。
     *
     *
     * boolean uncontended = true;
     * if (as == null || (m = as.length - 1) < 0 ||
     *     (a = as[getProbe() & m]) == null ||
     *     !(uncontended =
     *       (r = function.applyAsLong(v = a.value, x)) == v ||
     *       a.cas(v, r)))
     *
     * 	boolean uncontended = true;: 初始化一个布尔变量 uncontended 为 true，表示线程之间是否发生竞争。
     * 	•	if (as == null || ... || !uncontended): 通过多个条件检查，尝试在 cells 数组中找到一个合适的 Cell 进行累加。如果任一条件不满足，则进入 longAccumulate 方法处理：
     * 	•	as == null: 如果 cells 还未初始化。
     * 	•	(m = as.length - 1) < 0: 如果 cells 的长度为 0，表示数组未初始化或有问题。
     * 	•	(a = as[getProbe() & m]) == null: 通过 getProbe() 获取当前线程的哈希值并定位到数组中的某个 Cell，如果该 Cell 为空，表示该位置还未被初始化。
     * 	•	!(uncontended = ...): 尝试更新 Cell 的值，如果累加成功，则 uncontended 仍然为 true，否则更新失败，表示有竞争，设置 uncontended 为 false。
     *
     *
     * 	longAccumulate(x, function, uncontended);
     *
     * 	如果发生了竞争（uncontended 为 false），或者上述任何检查条件失败，则调用 longAccumulate 方法来处理竞争情况。longAccumulate 方法会进行进一步的操作，如初始化 cells 数组，或者重新尝试累加操作。
     *
     *
     *
     *
     * 	    final void longAccumulate(long x, LongBinaryOperator fn,
     *                               boolean wasUncontended) {
     *         int h;
     *         if ((h = getProbe()) == 0) {
     *             ThreadLocalRandom.current(); // force initialization
     *             h = getProbe();
     *             wasUncontended = true;
     *         }
     *         boolean collide = false;                // True if last slot nonempty
     *         for (;;) {
     *             Cell[] as; Cell a; int n; long v;
     *             if ((as = cells) != null && (n = as.length) > 0) {
     *                 if ((a = as[(n - 1) & h]) == null) {
     *                     if (cellsBusy == 0) {       // Try to attach new Cell
     *                         Cell r = new Cell(x);   // Optimistically create
     *                         if (cellsBusy == 0 && casCellsBusy()) {
     *                             boolean created = false;
     *                             try {               // Recheck under lock
     *                                 Cell[] rs; int m, j;
     *                                 if ((rs = cells) != null &&
     *                                     (m = rs.length) > 0 &&
     *                                     rs[j = (m - 1) & h] == null) {
     *                                     rs[j] = r;
     *                                     created = true;
     *                                 }
     *                             } finally {
     *                                 cellsBusy = 0;
     *                             }
     *                             if (created)
     *                                 break;
     *                             continue;           // Slot is now non-empty
     *                         }
     *                     }
     *                     collide = false;
     *                 }
     *                 else if (!wasUncontended)       // CAS already known to fail
     *                     wasUncontended = true;      // Continue after rehash
     *                 else if (a.cas(v = a.value, ((fn == null) ? v + x :
     *                                              fn.applyAsLong(v, x))))
     *                     break;
     *                 else if (n >= NCPU || cells != as)
     *                     collide = false;            // At max size or stale
     *                 else if (!collide)
     *                     collide = true;
     *                 else if (cellsBusy == 0 && casCellsBusy()) {
     *                     try {
     *                         if (cells == as) {      // Expand table unless stale
     *                             Cell[] rs = new Cell[n << 1];
     *                             for (int i = 0; i < n; ++i)
     *                                 rs[i] = as[i];
     *                             cells = rs;
     *                         }
     *                     } finally {
     *                         cellsBusy = 0;
     *                     }
     *                     collide = false;
     *                     continue;                   // Retry with expanded table
     *                 }
     *                 h = advanceProbe(h);
     *             }
     *             else if (cellsBusy == 0 && cells == as && casCellsBusy()) {
     *                 boolean init = false;
     *                 try {                           // Initialize table
     *                     if (cells == as) {
     *                         Cell[] rs = new Cell[2];
     *                         rs[h & 1] = new Cell(x);
     *                         cells = rs;
     *                         init = true;
     *                     }
     *                 } finally {
     *                     cellsBusy = 0;
     *                 }
     *                 if (init)
     *                     break;
     *             }
     *             else if (casBase(v = base, ((fn == null) ? v + x :
     *                                         fn.applyAsLong(v, x))))
     *                 break;                          // Fall back on using base
     *         }
     *     }
     *
     *
     *
     *
     * int h;
     * if ((h = getProbe()) == 0) {
     *     ThreadLocalRandom.current(); // force initialization
     *     h = getProbe();
     *     wasUncontended = true;
     * }
     *
     *
     * 	•	h = getProbe(): 获取当前线程的探测值 h，这个值用于确定线程在 cells 数组中的索引。
     * 	•	如果 h 为 0，表示该线程还没有初始化探测值，通过 ThreadLocalRandom.current() 强制初始化，然后重新获取探测值 h。
     *
     *
     * boolean collide = false; // True if last slot nonempty
     * collide: 表示在上一次尝试中是否发生了冲突，即对应的 Cell 位置是否已被占用。
     *
     *
     * for (;;) {
     *     Cell[] as; Cell a; int n; long v;
     *     if ((as = cells) != null && (n = as.length) > 0) {
     *
     * for (;;) 是一个无限循环，确保累加操作最终成功。
     * 	•	as = cells: 读取当前的 cells 数组引用。
     * 	•	n = as.length: 获取 cells 数组的长度。
     *
     *
     *
     *
     * if ((a = as[(n - 1) & h]) == null) {
     *     if (cellsBusy == 0) { // Try to attach new Cell
     *         Cell r = new Cell(x); // Optimistically create
     *         if (cellsBusy == 0 && casCellsBusy()) {
     *             boolean created = false;
     *             try { // Recheck under lock
     *                 Cell[] rs; int m, j;
     *                 if ((rs = cells) != null &&
     *                     (m = rs.length) > 0 &&
     *                     rs[j = (m - 1) & h] == null) {
     *                     rs[j] = r;
     *                     created = true;
     *                 }
     *             } finally {
     *                 cellsBusy = 0;
     *             }
     *             if (created)
     *                 break;
     *             continue; // Slot is now non-empty
     *         }
     *     }
     *     collide = false;
     * }
     *
     * 	a = as[(n - 1) & h]: 通过探测值 h 计算线程要操作的 Cell 索引 a。
     * 	•	如果该 Cell 为 null（表示这个位置没有线程占用），并且 cellsBusy == 0（表示数组没有被其他线程修改），则尝试创建一个新的 Cell 并放入该位置。
     * 	•	if (cellsBusy == 0 && casCellsBusy()): 通过 CAS 操作尝试获取 cells 数组的修改权限。如果成功，进入临界区，检查 Cell 数组是否已经被其他线程修改。如果没被修改，则将新的 Cell 放入数组，并将 created 标志设置为 true。
     * 	•	如果 Cell 被成功创建，退出循环；否则，重新尝试。
     *
     *
     *
     * else if (!wasUncontended) // CAS already known to fail
     *     wasUncontended = true; // Continue after rehash
     * else if (a.cas(v = a.value, ((fn == null) ? v + x :
     *                              fn.applyAsLong(v, x))))
     *     break;
     * else if (n >= NCPU || cells != as)
     *     collide = false; // At max size or stale
     * else if (!collide)
     *     collide = true;
     * else if (cellsBusy == 0 && casCellsBusy()) {
     *     try {
     *         if (cells == as) { // Expand table unless stale
     *             Cell[] rs = new Cell[n << 1];
     *             for (int i = 0; i < n; ++i)
     *                 rs[i] = as[i];
     *             cells = rs;
     *         }
     *     } finally {
     *         cellsBusy = 0;
     *     }
     *     collide = false;
     *     continue; // Retry with expanded table
     * }
     * h = advanceProbe(h);
     *
     *
     * 	else if (!wasUncontended): 如果先前的 CAS 操作已经失败，并且 wasUncontended 标志为 false，则表示竞争发生，设置 wasUncontended 为 true，并重新尝试。
     * 	•	else if (a.cas(...)): 如果先前没有竞争，尝试对 Cell 的值进行累加。如果成功，退出循环；否则，继续尝试。
     * 	•	else if (n >= NCPU || cells != as): 如果 cells 数组已经达到最大长度（等于 CPU 核心数），或者 cells 被其他线程修改，则不需要进一步处理冲突，设置 collide 为 false。
     * 	•	else if (!collide): 如果没有冲突，设置 collide 为 true，并重试操作。
     * 	•	else if (cellsBusy == 0 && casCellsBusy()): 如果发生冲突且 cellsBusy 为 0，尝试扩展 cells 数组的容量，将其扩展到原来的两倍，并将冲突标志设置为 false。然后重新尝试。
     * 	•	h = advanceProbe(h): 通过 advanceProbe 生成新的探测值 h，为下次尝试做准备。
     *
     *
     *
     *
     * else if (cellsBusy == 0 && cells == as && casCellsBusy()) {
     *     boolean init = false;
     *     try { // Initialize table
     *         if (cells == as) {
     *             Cell[] rs = new Cell[2];
     *             rs[h & 1] = new Cell(x);
     *             cells = rs;
     *             init = true;
     *         }
     *     } finally {
     *         cellsBusy = 0;
     *     }
     *     if (init)
     *         break;
     * }
     * else if (casBase(v = base, ((fn == null) ? v + x :
     *                             fn.applyAsLong(v, x))))
     *     break; // Fall back on using base
     *
     *
     * else if (cellsBusy == 0 && cells == as && casCellsBusy()): 如果 cells 还未初始化，并且没有其他线程在操作 cells 数组，则通过 CAS 操作尝试初始化 cells 数组。
     * 	•	if (cells == as): 再次检查 cells 是否已经被初始化。如果未被初始化，则创建一个新的 cells 数组，大小为 2，并将 x 的值存储在数组的一个位置上。
     * 	•	如果 cells 数组初始化成功，退出循环。
     * 	•	else if (casBase(...)): 如果 cells 数组初始化失败，则尝试通过 CAS 操作更新 base 值，作为最后的备选方案。如果成功，退出循环。
     *
     *
     * 作用: longAccumulate 方法处理并发累加操作时的竞争情况，通过使用 Cell 数组和基于探测值的哈希策略分散竞争，确保多个线程能高效地累加值。
     * 	•	关键策略:
     * 	•	通过 cells 数组分散竞争。
     * 	•	使用 CAS 操作确保线程安全。
     * 	•	当 cells 数组容量不足时自动扩展数组。
     * 	•	在竞争激烈时回退到基础值 base 上进行累加操作。
     *
     * 这种机制通过减少锁的争用，提高了多线程环境下累加操作的性能。
     *
     *
     *
     */
}
