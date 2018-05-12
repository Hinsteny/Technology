# JVM源码分析之 LockSupport 完全解读

**Basic thread blocking primitives for creating locks and other synchronization classes!**

## Questions

- LockSupport 是个什么东西呢?
- LockSupport 实现锁的原理是怎样的呢?
- LockSupport 可以应用于哪些场景呢?
- 

### Answers

1.LockSupport 是实现锁原语的基础操作工具类;
2.通过调用UNSAFE类的park, unpark方法实现, 可以不需要一个指定的锁对象, 可以直接实现线程同步;
3.在多线程交互场景中, 实现信息交互;

