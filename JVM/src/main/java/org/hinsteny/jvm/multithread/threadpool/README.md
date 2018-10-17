# ThreadPoolExecutor 

## 基础知识

ThreadPoolExecutor: 用来自定义创建线程池的基础类, 在Executors里面创建各种线程池的方法内部也是调用的该类, 它具有以下构造参数;

- int corePoolSize: 核心线程数 -> 工作线程存活的个数, 默认空闲也不会被回收, 如果设置allowCoreThreadTimeOut=true, 那就会回收核心线程, 即核心线程数为0;
- int maximumPoolSize: 最大线程数 -> 最大允许创建的线程池中数目, 
- long keepAliveTime: 存活时间 -> 非核心的空闲线程在超过此存活时间都没有被安排执行新任务就会被回收; 
- TimeUnit unit: 存活时间单位 -> 
- BlockingQueue workQueue: 任务队列 -> 当任务提交到线程池后, 如果没有可用的核心线程, 就会被丢到此队列, 等核心线程空闲, 再从队列中拿出来执行;
- ThreadFactory threadFactory: 线程工厂 -> 创建新线程对象的工厂
- RejectedExecutionHandler handler: 拒绝策略 -> 当线程池不能接收处理新的task时, 所采用的拒绝策略;
 
 
### BlockingQueue 列表

- LinkedBlockingQueue: 阻塞链表队列 -> 可以指定大小的先进先出的链表队列, 尾部添加入队, 头部出队移出; 相比于数组队列, 链表队列拥有较高吞吐量, 但是在高并发环境下性能差一点;
- LinkedBlockingDeque: 阻塞链表双端队列 -> 可以指定大小的双端进出链表队列;
- ArrayBlockingQueue: 阻塞数组队列 -> 初始指定大小的先进先出队列, 尾部添加入队, 头部出队移出; 从对头遍历; 满队列插入或空队列取出都会阻塞;
- LinkedTransferQueue: 顺序链表队列 -> 不限大小, 先进先出的链表队列;
- PriorityBlockingQueue: 阻塞优先队列 -> 
- SynchronousQueue: 同步操作队列 -> 
- DelayQueue: 延迟队列 ->
- DelayedWorkQueue: 延迟工作队列 -> 

 
### ThreadFactory 列表

- DefaultThreadFactory: 
- PrivilegedThreadFactory: 


### RejectedExecutionHandler 列表

- AbortPolicy: 直接抛出异常**RejectedExecutionException** 
- CallerRunsPolicy: 如果线程池没关闭, 就直接同步调用run方法执行任务;
- DiscardOldestPolicy: 丢弃任务队列头部的一个老任务, 把新任务添加到任务队列中;
- DiscardPolicy: 直接丢弃新任务, 不报错;
