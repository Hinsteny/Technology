package com.hisoka.jstorm.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Hinsteny
 * @version $ID: LimitQueueList 2018-03-29 15:41 All rights reserved.$
 */
public class LimitQueueList<E> implements Queue<E>, Serializable {

    /**
     * 队列长度，实例化类的时候指定
     */
    private int limit;

    Queue<E> queue = new LinkedList<E>();

    public LimitQueueList(int limit) {
        this.limit = limit;
    }

    /**
     * 获取限制大小
     *
     * @return
     * @author SHANHY
     * @date   2015年11月9日
     */
    public int getLimit(){
        return limit;
    }

    /**
     * 入队
     */
    @Override
    public boolean offer(E e){
        if(queue.size() >= limit){
            //如果超出长度,入队时,先出队
            queue.poll();
        }
        return queue.offer(e);
    }

    /**
     * 出队
     */
    @Override
    public E poll() {
        return queue.poll();
    }

    @Override
    public boolean add(E e) {
        return queue.add(e);
    }

    @Override
    public E element() {
        return queue.element();
    }

    @Override
    public E peek() {
        return queue.peek();
    }

    @Override
    public boolean isEmpty() {
        return queue.size() == 0 ? true : false;
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public E remove() {
        return queue.remove();
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public boolean contains(Object o) {
        return queue.contains(o);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return queue.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return queue.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return queue.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return queue.retainAll(c);
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }

    @Override
    public boolean remove(Object o) {
        return queue.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return queue.removeAll(c);
    }

    @Override
    public Object[] toArray() {
        return queue.toArray();
    }

}
