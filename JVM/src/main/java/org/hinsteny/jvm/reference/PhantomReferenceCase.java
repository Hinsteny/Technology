package org.hinsteny.jvm.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用类型例子
 * @author Hinsteny
 * @version $ID: StrongReferenceCase 2018-06-13 11:12 All rights reserved.$
 */
public class PhantomReferenceCase {

    public static void main(String[] args) throws InterruptedException {
        final ReferenceQueue queue = new ReferenceQueue();
        PhantomReference<ReferenceClass> softReference = new PhantomReference<>(ReferenceClass.getReference(), queue);
        System.out.println(softReference.get());
        System.gc();
        queue.remove();
        System.out.println("Phantom reference deleted after");
        softReference.get();
    }
}
