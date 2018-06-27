package org.hinsteny.jvm.reference;

/**
 * 强引用类型例子
 *
 * @author Hinsteny
 * @version $ID: StrongReferenceCase 2018-06-13 11:12 All rights reserved.$
 */
public class StrongReferenceCase {

    public static void main(String[] args) {
        String name = new String("hinsteny".toCharArray());
        ReferenceClass referenceCase = ReferenceClass.getReference();
        System.gc();
        System.out.println(referenceCase);
    }
}
