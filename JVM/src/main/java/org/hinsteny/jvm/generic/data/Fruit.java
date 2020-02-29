package org.hinsteny.jvm.generic.data;

/**
 * @author hinsteny
 * @version Fruit: 2020-02-29 13:17 All rights reserved.
 */
public abstract class Fruit<F> implements Comparable<F> {

  private String name;

  @Override
  public int compareTo(F o) {
    return 0;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
