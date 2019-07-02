# JVM源码分析之 ReentrantLock 完全解读

**A reentrant mutual exclusion Lock with the same basic behavior and semantics as the implicit monitor lock accessed using synchronized methods and statements, 
but with extended capabilities!**

## Questions

- ReentrantLock 是一个什么样的锁呢?
- ReentrantLock 实现锁的原理如何?
- ReentrantLock 可以应用于哪些场景呢?
- 

### Answers

1. 底层基于AQS(AbstractQueuedSynchronizer)框架, 自定义实现公平/非公平(默认配置非公平)两种锁模式的排他锁, 公平指按照请求锁的顺序依次获得锁;
2. 在它里面实现公平/非公平, 是通过判断锁等待队列中最早进入队列并没有放弃获取锁的节点实现;
3. 任何需要使用排他锁的地方都可以用它替换;

