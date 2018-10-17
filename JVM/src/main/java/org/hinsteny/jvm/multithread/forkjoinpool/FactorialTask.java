package org.hinsteny.jvm.multithread.forkjoinpool;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * @author Hinsteny
 * @version $ID: FactorialTask 2018-08-02 21:37 All rights reserved.$
 */
public class FactorialTask extends RecursiveTask<BigInteger> {

    private int start;

    private int end;

    private int segment = 20;

    public FactorialTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public FactorialTask(int start, int end, int segment) {
        this.start = start;
        this.end = end;
        this.segment = segment;
    }

    @Override
    protected BigInteger compute() {
        if ((end - start) >= segment) {
            return ForkJoinTask.invokeAll(createSubTasks())
                .stream()
                .map(ForkJoinTask::join)
                .reduce(BigInteger.ONE, BigInteger::add);
        } else {
            return calculate(start, end);
        }
    }

    /**
     * 计算一段数字区间内所有整数的和
     *
     * @param start
     * @param end
     * @return
     */
    private BigInteger calculate(int start, int end) {
        BigInteger reduce = IntStream.rangeClosed(start, end)
            .mapToObj(BigInteger::valueOf)
            .reduce(BigInteger.ONE, BigInteger::add);
        System.out.println(String.format("calculate start: %d to end: %d, then result is: %d", start, end, reduce));
        return reduce;
    }

    /**
     *
     * @return
     */
    private Collection<FactorialTask> createSubTasks() {
        List<FactorialTask> dividedTasks = new ArrayList<>();
        int mid = (start + end) / 2;
        dividedTasks.add(new FactorialTask(start, mid));
        dividedTasks.add(new FactorialTask(mid + 1, end));
        return dividedTasks;
    }

}
