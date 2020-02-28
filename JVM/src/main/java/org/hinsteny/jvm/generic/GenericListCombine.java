package org.hinsteny.jvm.generic;

import java.util.ArrayList;
import java.util.List;
import org.hinsteny.jvm.generic.data.MathTeacher;
import org.hinsteny.jvm.generic.data.Teacher;

/**
 * @author hinsteny
 * @version GenericListComp: 2020-02-28 16:49 All rights reserved.
 */
public class GenericListCombine {

  public static void main(String[] args) {
    arraysExample();
    arraysGenericExample();
  }

  private static void arraysExample() {
    List<Teacher> teachers = new ArrayList<>();
    printArraysExample(teachers);
    List<MathTeacher> mathTeachers = new ArrayList<>();
    // 下面这个就编译不过
//    printArraysExample(mathTeachers);

  }

  private static void printArraysExample(List<Teacher> teachers) {
    for (Teacher teacher : teachers) {
      System.out.println(teacher.getName());
    }
  }

  private static void arraysGenericExample() {
    List<Teacher> teachers = new ArrayList<>();
    printArraysGenericExample(teachers);
    List<MathTeacher> mathTeachers = new ArrayList<>();
    // 下面这个就能编译
    printArraysGenericExample(mathTeachers);

  }

  private static void printArraysGenericExample(List<? extends Teacher> teachers) {
    for (Teacher teacher : teachers) {
      System.out.println(teacher.getName());
    }
  }

}
