package org.hinsteny.jvm.generic.data;

/**
 * @author hinsteny
 * @version Driver: 2020-02-28 17:40 All rights reserved.
 */
public class Driver implements Person {

  private String sex = "women";

  private String name = "tony";

  public Driver() {
  }

  public Driver(String sex, String name) {
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
