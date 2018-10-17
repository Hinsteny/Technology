# ForkJoinPool 
又一个线程池实现类, 实现了ExecutorService接口, 出生于java7, 也是java7中fork/join框架的重要组件

## 基础知识

fork/join: fork/join 框架基于“工作窃取算法”。简而言之，意思就是执行完任务的线程可以从其他运行中的线程“窃取”工作。

ForkJoinPool 适用于任务创建子任务的情况，或者外部客户端创建大量小任务到线程池。

这种线程池的工作流程如下：

- ►创建 ForkJoinPool 线程池对象
- ►构建 ForkJoinTask 子类实例
- ►提交任务到线程池
- ►任务执行过程中依据某种条件进行裂变产生新的子任务, 然后父任务需要join子任务的结果
- ►将任务结果合并
- ►执行完成关闭线程池
 
