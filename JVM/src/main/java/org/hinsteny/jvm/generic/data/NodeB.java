package org.hinsteny.jvm.generic.data;

/**
 * @author hinsteny
 * @version NodeB: 2020-02-28 15:50 All rights reserved.
 */
public class NodeB<T> {

  private T obj;

  public T get() {
    return obj;
  }

  public void set(T obj) {
    this.obj = obj;
  }

  public static void main(String[] argv) {
    Student stu = new Student();
    NodeB<Student> node = new NodeB<>();
    node.set(stu);
    Student stu2 = node.get();
  }

}
