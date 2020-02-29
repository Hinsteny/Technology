package org.hinsteny.jvm.generic;

import org.hinsteny.jvm.generic.data.Apple;
import org.hinsteny.jvm.generic.data.Bread;
import org.hinsteny.jvm.generic.data.InstantNoodle;
import org.hinsteny.jvm.generic.data.ReadApple;
import org.hinsteny.jvm.generic.data.SoftBread;
import org.hinsteny.jvm.generic.data.Watermelon;

/**
 * @author hinsteny
 * @version GenericCompare: 2020-02-29 13:20 All rights reserved.
 */
public class GenericCompare {

  public static void main(String[] args) {
    normalCompare();
    genericCompare();
  }

  private static void normalCompare() {
    Apple apple = new Apple();
    Apple appleTwo = new Apple();
    ReadApple readApple = new ReadApple();
    Watermelon watermelon = new Watermelon();
    // 同类比较
    apple.compareTo(appleTwo);
    // 同子类比较
    apple.compareTo(readApple);
    // 不能同非自身比较
//    apple.compareTo(watermelon);
  }

  private static void genericCompare() {
    Bread bread = new Bread();
    Bread breadTwo = new Bread();
    SoftBread softBread = new SoftBread();
    InstantNoodle instantNoodle = new InstantNoodle();
    // 同类比较
    bread.compareTo(breadTwo);
    // 同子类比较
    bread.compareTo(softBread);
    // 不能同非自身比较
//    bread.compareTo(instantNoodle);
  }

}
