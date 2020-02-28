package org.hinsteny.jvm.commons;

import java.util.ArrayList;
import java.util.List;

/**
 * CME: ConcurrentModificationException
 * <p>集合在做 for-Itr 循环时不会发生CME的特殊情况.</p>
 * <p>正常情况我们都知道如果要在一个集合的循环处理中做删除操作，
 * 那就应该使用Iterator中的remove方法来实现，直接使用List.remove会发生CME;</p>
 *
 * <p>这里我们展示一种特殊情况, 直接使用for增强循环, 并用List.remove删除元素, 但是并不会报错;</p>
 * <p>看起来下面的代码删除了list的第一个元素不报错，实际情况是删除list的倒数第二个元素不会报错</p>
 * @author hinsteny
 * @version ListForRemoveUX: 2020-02-28 15:05 All rights reserved.
 */
public class ListForRemoveCME {

  public static void main(String[] args) {
    List<String> list = new ArrayList<>();
    list.add("1");
    list.add("2");
    for (String item : list) {
      if ("1".equals(item)) {
        list.remove(item);
      }
    }
    System.out.println("============");
  }

}
