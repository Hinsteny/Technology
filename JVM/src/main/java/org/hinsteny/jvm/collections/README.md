# java内置的集合相关使用及源码分析

## 基础知识

java.util 包中有以下接口及工具类:

- ►**Collection**: 集合的根接口, 定义了判断集合是否为空, 集合的size, 集合包含某个元素, 集合迭代器, 添加元素, 移除原素, 及jdk1.8引入的stream;
- ►Collections: 提供诸多操作集合的静态方法, 比如排序, 搜索, 倒置, 交换等, 同时提供将集合包装为线程安全的集合;

- ►**List**: 
- ►ArrayList: 一个实现List接口且大小可变的数组. 允许装载包含null的所有元素; 但是它不是线程安全的;
- ►Vector: 和ArrayList功能相同, 但是Vector是线程安全的, 它的内部方法基本上都被synchronized关键字修饰着;
- ►LinkedList: 双向链表实现的列表. 允许null元素, 因为底层采用的数据结构是链表, 所以频繁的插入和删除不会涉及到元素的批量移动, 效率相较数组会比较好;

- ►**Map**: 
- ►HashMap: 对Map接口进行实现的Hash表, 允许null-key和null-value, 同时非线程安全;
- ►LinkedHashMap: 继承自HashMap, 主要在其上提供了以链表形式遍历HashMap的方式;
- ►Hashtable: 和HashMap功能相同, 但是它是线程安全的, 同时不支持null-key和null-value;
- ►TreeMap: 基于红黑树实现的有序Map, 内部根据key或者提供一个Comparator进行排序;

- ►**Set**: 
- ►HashSet: 对Set接口的实现, 底层使用一个HashMap进行数据的实际存储, 非线程安全, 允许null元素;
- ►LinkedHashSet: 继承自HashSet, 内部依赖LinkedHashMap实现, 提供链表遍历的形式;
- ►TreeSet: 类似HashSet于HashMap, 有序的Set;

- ►**Queue**: 
- ►PriorityQueue: 基于优先堆实现的一个无限队列, 不允许null元素;

## 常见问题答疑

1. HashMap在rehash的过程中是否可以进行并发的get获取元素? 

```
  HashMap非线程安全, 正常的容量扩容发生在插入一个元素后, 发现容器内元素总个数超过需要rehash的数值, 就进行rehash; 然后在rehash过程中, 先new一个扩容后的新数组, 然后将HashMap中的数组引用指向新数组, 
  所以这里并发访问虽然不会发生程序异常, 但是可能会查询不到一个实际存在于HashMap中的元素;
```
2. 常见排序算法比较

|排序方法|时间复杂度（平均）|时间复杂度（最坏）|时间复杂度（最好）|空间复杂度|稳定性|
|---|---|---|---|---|---|
|冒泡排序|O(n2)O(n2)|O(n2)O(n2)|O(n)O(n)|O(1)O(1)|稳定|
|选择排序|O(n2)O(n2)|O(n2)O(n2)|O(n2)O(n2)|O(1)O(1)|不稳定|
|插入排序|O(n2)O(n2)|O(n2)O(n2)|O(n)O(n)|O(1)O(1)|稳定|
|二分插入排序|O(nlog2n)|O(n2)|O(log2n)|O(n+k)|稳定|
|希尔排序|O(n1.3)O(n1.3)|O(n2)O(n2)|O(n)O(n)|O(1)O(1)|不稳定|
|快速排序|O(nlog2n)O(nlog2n)|O(n2)O(n2)|O(nlog2n)O(nlog2n)|O(nlog2n)O(nlog2n)|不稳定|
|归并排序|O(nlog2n)O(nlog2n)|O(nlog2n)O(nlog2n)|O(nlog2n)O(nlog2n)|O(n)O(n)|稳定|
|堆排序|O(nlog2n)O(nlog2n)|O(nlog2n)O(nlog2n)|O(nlog2n)O(nlog2n)|O(1)O(1)|不稳定|
|基数排序|O(n∗k)O(n∗k)|O(n∗k)O(n∗k)|O(n∗k)O(n∗k)|O(n+k)O(n+k)|稳定|


3.  List相关实现类的排序效果

| List  | 是否有序 | 排序方法| 排序是否稳定|
|---|---|---|---|
| ArrayList(Vector) | 是 |归并排序 | 是|
| LinkedList | 是 |归并排序 | 是|
| TreeSet | 是 | 二叉树| X|
| TreeMap | 是 | 二叉树| X|

