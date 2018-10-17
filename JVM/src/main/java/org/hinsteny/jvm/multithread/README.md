# 多线程处理常用示例

## 基础知识

java.util.concurrent 包中有以下接口及工具类:
- ►Executor: 执行任务的简单接口
- ►ExecutorService: 一个较复杂的接口, 包含额外方法来管理任务和 executor 本身
- ►ScheduledExecutorService: 扩展自 ExecutorService, 增加了执行任务的调度方法
- ►Executors: 提供可以直接创建实现以上接口实例的工具方法

## 示例列表

- Executors: 直接使用Executors里面的方法生成线程池对象, 然后再提交任务;
- ThreadPoolExecutor: 自定义参数生成线程池对象, 提交任务;
- ForkJoinPool: 基于任务窃取的机制, 当我们提交的任务裂变出子任务后, 就可以被其他的工作线程获取执行, 充分利用操作系统的多核并发计算资源;

