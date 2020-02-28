package org.hinsteny.jvm.generic.data;

/**
 * @author hinsteny
 * @version NodeA: 2020-02-28 15:49 All rights reserved.
 */
public class NodeA {

  private Object obj;

  public Object get() {
    return obj;
  }

  public void set(Object obj) {
    this.obj = obj;
  }

  public static void main(String[] argv) {
    Student stu = new Student();
    NodeA node = new NodeA();
    node.set(stu);
    Student stu2 = (Student) node.get();
  }

}
