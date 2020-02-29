package org.hinsteny.jvm.generic;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author hinsteny
 * @version GenericBoundaryCallError: 2020-02-29 12:50 All rights reserved.
 */
public class GenericBoundaryCallError {

  public static void main(String[] args) {
    // 不报错
    Object[] attributes = pickTwo("Good", "Fast", "Cheap");
    System.out.println(Arrays.asList(attributes).toString());
    // 报错了. 原因是pickTwo方法返回的数组中的元素数据类型是Object, 而不是String, 因为泛型已经被擦除了
    String[] strAttributes = pickTwo("Good", "Fast", "Cheap");
    System.out.println(Arrays.asList(strAttributes).toString());
  }

  static <T> T[] pickTwo(T a, T b, T c) {
    switch (ThreadLocalRandom.current().nextInt(3)) {
      case 0:
        return toArray(a, b);
      case 1:
        return toArray(a, c);
      case 2:
        return toArray(b, c);
    }
    // Can't get here
    throw new AssertionError();
  }

  static <T> T[] toArray(T... args) {
    return args;
  }

}
