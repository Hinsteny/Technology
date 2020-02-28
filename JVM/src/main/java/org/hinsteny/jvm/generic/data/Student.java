package org.hinsteny.jvm.generic.data;

/**
 * @author hinsteny
 * @version Student: 2020-02-28 15:49 All rights reserved.
 */
public class Student implements Person {

  private String sex = "women";

  private String name = "tony";

  public Student() {
  }

  public Student(String sex, String name) {
    this.sex = sex;
    this.name = name;
  }

  @Override
  public String getSex() {
    return sex;
  }

  @Override
  public String getName() {
    return name;
  }

}
