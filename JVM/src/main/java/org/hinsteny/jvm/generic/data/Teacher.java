package org.hinsteny.jvm.generic.data;

/**
 * @author hinsteny
 * @version Teacher: 2020-02-28 16:29 All rights reserved.
 */
public class Teacher implements Person {

  private String sex = "women";

  private String name = "alisa";

  public Teacher() {
  }

  public Teacher(String sex, String name) {
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
