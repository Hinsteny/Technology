package org.hinsteny.jvm.algorithm;

/**
 * 二叉堆的java实现
 *
 * @author Hinsteny
 * @version BinaryHeap: BinaryHeap 2019-06-12 16:31 All rights reserved.$
 */
public class BinaryHeapTest {


    public static void main(String[] args) {
        Integer[] data = {3, 5, 9, 1, 10, 8, 2, 12, 7};
        BinaryHeap heap = new BinaryHeap<Integer>(true);
        for (int i = 0; i < data.length; i++) {
            heap.add(data[i]);
        }
        while (heap.size() > 0) {
            System.out.println(heap);
            heap.popTop();
        }
    }

}
