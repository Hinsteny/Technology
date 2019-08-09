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

## 简单但高明的解法

1. 给定指定数目的线程,让这些线程同时打印数字序列[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...],但是需要保证所有的线程按序轮询打印,具体的打印效果如下:
   ```
   # 假设指定线程数为3
   Thread--0: 0,
   Thread--1: 1,
   Thread--2: 2,
   Thread--0: 3,
   Thread--1: 4,
   Thread--2: 5,
   Thread--0: 6,
   Thread--1: 7,
   ...
   ```