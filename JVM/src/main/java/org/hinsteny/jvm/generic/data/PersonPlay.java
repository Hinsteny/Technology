package org.hinsteny.jvm.generic.data;

/**
 * @author hinsteny
 * @version PersonPlay: 2020-02-29 13:04 All rights reserved.
 */
public class PersonPlay implements PlayAble<Person> {

  @Override
  public void play(Person props) {
    System.out.println(props);
  }

}
