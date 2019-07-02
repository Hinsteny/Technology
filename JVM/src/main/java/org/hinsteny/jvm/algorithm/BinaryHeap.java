package org.hinsteny.jvm.algorithm;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * 二叉堆的java实现
 *
 * @author Hinsteny
 * @version BinaryHeap: BinaryHeap 2019-06-12 20:25 All rights reserved.$
 */
public class BinaryHeap<E extends Comparable> implements Cloneable, java.io.Serializable {

    private static final long serialVersionUID = 7683452581122892189L;

    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Shared empty array instance used for default sized empty instances.
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * define is min-heap or max-heap. By default is min-heap
     */
    private boolean isMaxHeap;

    /**
     * The array buffer into which the elements of the BinaryHeap are stored. The capacity of the ArrayList is the length of this array buffer. Any empty ArrayList with heaps ==
     * EMPTY_ELEMENTDATA will be expanded to DEFAULT_CAPACITY when the first element is added.
     */
    transient Object[] heaps;

    /**
     * The size of the BinaryHeap (the number of elements it contains).
     *
     * @serial
     */
    private int size;

    /**
     * 用于在堆的操作过程中实现同步, 保证线程安全
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * The maximum size of array to allocate (unless necessary). Some VMs reserve some header words in an array. Attempts to allocate larger arrays may result in OutOfMemoryError: Requested array size
     * exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public BinaryHeap() {
        this(false);
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     *
     * @param isMaxHeap the initial capacity of the list
     */
    public BinaryHeap(boolean isMaxHeap) {
        this(isMaxHeap, DEFAULT_CAPACITY);
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param isMaxHeap the initial capacity of the list
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity is negative
     */
    public BinaryHeap(boolean isMaxHeap, int initialCapacity) {
        this.isMaxHeap = isMaxHeap;
        if (initialCapacity > 0) {
            this.heaps = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.heaps = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    /**
     * Returns a shallow copy of this {@code ArrayList} instance.  (The elements themselves are not copied.)
     *
     * @return a clone of this {@code ArrayList} instance
     */
    @Override
    public Object clone() {
        try {
            BinaryHeap<?> v = (BinaryHeap<?>) super.clone();
            v.heaps = Arrays.copyOf(heaps, size);
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }


    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if this list contains no elements.
     *
     * @return {@code true} if this list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns {@code true} if this list contains the specified element. More formally, returns {@code true} if and only if this list contains at least one element {@code e} such that {@code
     * Objects.equals(o, e)}.
     *
     * @param o element whose presence in this list is to be tested
     * @return {@code true} if this list contains the specified element
     */
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element. More formally, returns the lowest index {@code i} such that
     * {@code Objects.equals(o, get(i))}, or -1 if there is no such index.
     */
    public int indexOf(Object o) {
        return indexOfRange(o, 0, size);
    }

    private int indexOfRange(Object o, int start, int end) {
        Object[] es = heaps;
        if (o != null) {
            for (int i = start; i < end; i++) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the element at the top of heap.
     *
     * @return the element at the top of heap
     */
    public E getTop() {
        return size > 0 ? (E) heaps[0] : null;
    }

    public boolean add(E element) {
        Objects.requireNonNull(element);
        return addToHeap(element);
    }

    private boolean addToHeap(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (this.size == heaps.length) {
                heaps = grow();
            }
            siftUp(size++, e);
            return true;
        } finally {
            lock.unlock();
        }
    }


    /**
     * Sifts element added at bottom up to its heap-ordered spot.
     */
    private void siftUp(int k, E item) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            E e = (E) heaps[parent];
            if (compareTo(item, e) >= 0) {
                break;
            }
            heaps[k] = e;
            k = parent;
        }
        heaps[k] = item;
    }

    /**
     * pop the top of element, shrink heap
     * @return the top of element
     */
    public E popTop() {
        E top = getTop();
        return remove(top) ? top : null;
    }

    /**
     * remove element from heap
     */
    public boolean remove(E x) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int i = indexOf(x);
            if (i < 0) {
                return false;
            }
            int s = --size;
            E replacement = (E) heaps[s];
            heaps[s] = null;
            if (s != i) {
                siftDown(i, replacement);
                if (heaps[i] == replacement) {
                    siftUp(i, replacement);
                }
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Sifts element added at top down to its heap-ordered spot. Call only when holding lock.
     */
    private void siftDown(int k, E item) {
        int half = size >>> 1;
        while (k < half) {
            int child = (k << 1) + 1;
            E c = (E) heaps[child];
            int right = child + 1;
            if (right < size && compareTo(c, (E) heaps[right]) > 0) {
                c = (E) heaps[child = right];
            }
            if (compareTo(item, c) <= 0) {
                break;
            }
            heaps[k] = c;
            k = child;
        }
        heaps[k] = item;
    }

    /**
     * do compare for two element
     *
     * @param former thr former
     * @param latter the latter
     */
    private int compareTo(E former, E latter) {
        int flag = former.compareTo(latter);
        return isMaxHeap ? -flag : flag;
    }

    /**
     * grow up the heap array
     *
     * @return new heap array
     */
    private Object[] grow() {
        return grow(size + 1);
    }

    /**
     * Increases the capacity to ensure that it can hold at least the number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     * @throws OutOfMemoryError if minCapacity is less than zero
     */
    private Object[] grow(int minCapacity) {
        return heaps = Arrays.copyOf(heaps, newCapacity(minCapacity));
    }

    /**
     * Returns a capacity at least as large as the given minimum capacity. Returns the current capacity increased by 50% if that suffices. Will not return a capacity greater than MAX_ARRAY_SIZE unless
     * the given minimum capacity is greater than MAX_ARRAY_SIZE.
     *
     * @param minCapacity the desired minimum capacity
     * @throws OutOfMemoryError if minCapacity is less than zero
     */
    private int newCapacity(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = heaps.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity <= 0) {
            if (heaps == EMPTY_ELEMENTDATA) {
                return Math.max(DEFAULT_CAPACITY, minCapacity);
            }
            if (minCapacity < 0) {
                throw new OutOfMemoryError();
            }
            return minCapacity;
        }
        return (newCapacity - MAX_ARRAY_SIZE <= 0) ? newCapacity : hugeCapacity(minCapacity);
    }

    /**
     * the huge capacity of this heap
     * @param minCapacity minCapacity
     * @return
     */
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) {
            /* overflow */
            throw new OutOfMemoryError();
        }
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    @Override
    public String toString() {
        return "BinaryHeap{" +
            "heaps=" + Arrays.stream(heaps).filter(Objects::nonNull).map(item -> item.toString()).collect(Collectors.joining(", ", "[", "]")) +
            '}';
    }
}
