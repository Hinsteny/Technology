package org.hinsteny.jvm.generic.data;

/**
 * @author hinsteny
 * @version Food: 2020-02-29 13:23 All rights reserved.
 */
public abstract class Food<F extends Food<F>> implements Comparable<F> {

  private String name;

  @Override
  public int compareTo(F o) {
    return this.getName().compareTo(o.getName());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
