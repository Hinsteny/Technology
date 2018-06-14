package org.hinsteny.jvm.reference;

import java.lang.ref.WeakReference;

/**
 * 软引用类型例子
 *
 * @author Hinsteny
 * @version $ID: StrongReferenceCase 2018-06-13 11:12 All rights reserved.$
 */
public class WeakReferenceCase {

    public static void main(String[] args) {
        WeakReference<ReferenceClass> weakReference = new WeakReference<>(ReferenceClass.getReference());
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get());
    }
}
