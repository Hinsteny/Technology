# JVM源码分析之Object.wait/notify(All)完全解读

**The Object class in java contains three final methods that allows threads to communicate about the lock status of a resource. 
These methods are wait(), notify() and notifyAll()!**

## Questions

- Object 的三个方法wait, notify, notifyAll主要可以解决什么问题呢？
- 为什么要加synchronized锁呢？
- wait方法执行后未退出同步块，其他线程如何进入同步块？
- 为什么wait方法可能抛出InterruptedException异常？
- 被notify(All)的线程有规律吗？
- notify执行之后立马唤醒线程吗？
- notifyAll是怎么实现全唤起的呢？
- wait的线程是否会影响load吗？

### Answers

1. Java采用的共享内存模型, 多个线程需要以隐式的方式进行消息通讯, 使用Object的wait, notify可以实现多线程间的协同处理;
2. 要实现多线程间的协同处理, 就需要有一种同步排序执行的机制, 所以这里就正好使用了synchronzied(monitorenter/monitorexit是jvm规范里要求要去实现的)来实现咯(一种猜测)
3. 在wait处理的过程中会临时释放锁, 不过在其他线程调用notify方法唤醒此线程时, 在wait方法退出之前又会重新获得对象锁, 只有这样整个wait方法就会被monitorenter和monitorexit包围起来, 实现锁的获取必定有与之对应的释放动作;
4. 中断(Interrupter)是一种协作机制, 当一个线程中断(Interrupter)另一个线程时, 会设置一个中断标志, 即是告诉这个线程需要终止当前任务了, 当然具体怎么处理中断信号量还是线程自己决定; 这里wait实现了对中断信号量的响应处理机制,
   会抛出中断异常, 以供调用者处理对应的中断时的后续处理;
5. Notify的唤醒规律是FIFO, 第一个进入wait的线程优先被唤醒; NotifyAll则是LIFO, 按照最后一个进入的优先被唤醒规则执行;
6. 线程被Notify后需要Notify它的线程退出同步快才会被唤醒, Notify动作的同步块会全程持有锁, 这期间不能有新线程进入wait, 当然也不能有线程退出wait;
7. 这是一个策略问题, 线程要进入Notify快对其他线程进行唤醒, 需要获得对象锁, 因此在它执行完Notify块释放锁的时候, 就把最后一个进入wait(最后一个获取锁)的线程唤醒, 等最后一个进入线程被唤醒时, 同样有一个获取锁释放锁的过程,
   依次类推释放所有进入wait的线程;
8. wait/nofity是通过jvm里的park/unpark机制来实现的，在linux下这种机制又是通过pthread_cond_wait/pthread_cond_signal来玩的，因此当线程进入到wait状态的时候其实是会放弃cpu的，也就是说这类线程是不会占用cpu资源。




