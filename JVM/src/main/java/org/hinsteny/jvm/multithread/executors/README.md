# Executors 使用示例

### 基础知识

Executors 类里的工厂方法可以创建很多类型的线程池:

- ►newSingleThreadExecutor(): 包含单个线程和无界队列(Integer的最大值: 0x7fffffff)的线程池，同一时间只能执行一个任务
- ►newSingleThreadScheduledExecutor(): 创建一个可以执行定时触发任务的线程池
- ►newFixedThreadPool(): 包含固定数量(核心线程数和最大线程数相同)线程并共享无界队列的线程池；当所有线程处于工作状态，有新任务提交时，任务在队列中等待，直到一个线程变为可用状态
- ►newCachedThreadPool(): 只有需要时创建新线程的线程池(有空闲线程就重用, 没有就创建新线程)
- ►newWorkStealingPool(): 基于工作窃取（ForkJoinPool）算法的线程池, 默认创建与操作系统处理器单元相同数目的线程池, 并行运算加快处理效率(java8引入的stream就用到了ForkJoinPool);

### 用户示例见代码 **ExecutorsExample**