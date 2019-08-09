package org.hinsteny.jvm.collections;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Hinsteny
 * @version LinkedHashSetCase: LinkedHashSetCase 2019-07-22 18:06 All rights reserved.$
 */
public class LinkedHashSetCase {

    public static void main(String[] args) {
        LinkedHashSetIterate();
    }

    private static void LinkedHashSetIterate() {
        Set<String> set = new LinkedHashSet<>(8);
        set.add("one");
        set.add("two");
        set.add("three");
        System.out.println("============ LinkedHashSet for ============");
        for (String item : set) {
            System.out.println(item);
        }
        Iterator<String> iterator = set.iterator();
        System.out.println("============ LinkedHashSet Iterator ============");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        set = new HashSet<>(8);
        set.add("one");
        set.add("two");
        set.add("three");
        System.out.println("============ HashSet for ============");
        for (String item : set) {
            System.out.println(item);
        }
        iterator = set.iterator();
        System.out.println("============ HashSet Iterator ============");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}
