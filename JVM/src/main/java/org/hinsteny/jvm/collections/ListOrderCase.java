package org.hinsteny.jvm.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hinsteny
 * @version ListOrderCase: 2020-03-31 17:23 All rights reserved.
 */
public class ListOrderCase {

  public static void main(String[] args) {
    //ArrayList排序案例
    arrayListOrderCase();
    //长ArrayList排序案例
    langArrayListOrderCase();

  }

  /**
   * 1. 排序稳定
   * 2. 默认采用ComparableTimSort中的
   * 3. 所有List的子类比如ArrayList, LinkedList etc的排序最后都是调用到Arrays.sort方法
   */
  private static void arrayListOrderCase() {
//    List<Book> books = new ArrayList<>();
    List<Book> books = new LinkedList<>();
    books.add(new Book("Wuthering Heights", 2));
    books.add(new Book("War And Peace", 0));
    books.add(new Book("Childhood", 2));
    books.add(new Book("Notre-Dame de Paris", 3));
    books.add(new Book("David Copperfield", 1));

    System.out.println(books);
    // 排序;
    Collections.sort(books);
    System.out.println(books);
    // 排序;
    Collections.sort(books);
    System.out.println(books);

  }

  /**
   * 长数组排序
   */
  private static void langArrayListOrderCase() {
    List<Integer> numbers = Arrays.asList(15, 9, 8, 2, 1, 34, 21, 11, 20, 4, 7, 9, 12, 17, 13, 24, 50, 32, 3, 19, 3,
      5, 7, 15, 19, 18, 17, 0, 31, 29, 44, 27, 30, 19, 33, 20);
    System.out.println(numbers);
    // 排序;
    Collections.sort(numbers);
    System.out.println(numbers);
  }

  /**
   * Book
   */
  private static class Book implements Comparable<Book> {

    private String name;

    private int order;

    public Book(String name, int order) {
      this.name = name;
      this.order = order;
    }

    public String getName() {
      return name;
    }

    public int getOrder() {
      return order;
    }

    @Override
    public String toString() {
      return "Book{" +
        "name='" + name + '\'' +
        ", order=" + order +
        '}';
    }

    @Override
    public int compareTo(Book o) {
      return this.order - o.getOrder();
    }
  }

}
