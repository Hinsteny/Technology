package org.hinsteny.jvm.reference;

import java.lang.ref.SoftReference;

/**
 * 软引用类型例子
 *
 * @author Hinsteny
 * @version $ID: StrongReferenceCase 2018-06-13 11:12 All rights reserved.$
 */
public class SoftReferenceCase {

    public static void main(String[] args) {
        SoftReference<ReferenceClass> softReference = new SoftReference<>(ReferenceClass.getReference());
        System.gc();
        System.out.println(softReference.get());
    }
}
