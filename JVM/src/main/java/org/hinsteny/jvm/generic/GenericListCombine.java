package org.hinsteny.jvm.generic;

import java.util.ArrayList;
import java.util.List;
import org.hinsteny.jvm.generic.data.ManMathTeacher;
import org.hinsteny.jvm.generic.data.MathTeacher;
import org.hinsteny.jvm.generic.data.Teacher;

/**
 * @author hinsteny
 * @version GenericListComp: 2020-02-28 16:49 All rights reserved.
 */
public class GenericListCombine {

  public static void main(String[] args) {
    // 取值
    getFromArraysExample();

    //存值
    putToArraysExample();
  }

  private static void getFromArraysExample() {
    List<Teacher> teachers = new ArrayList<>();
    printGetFromArraysExample(teachers);
    List<MathTeacher> mathTeachers = new ArrayList<>();
    // 下面这个就编译不过
//    printGetFromArraysExample(mathTeachers);
    // 下面这个就能编译
    printGetFromArraysGenericExample(mathTeachers);

  }

  private static void printGetFromArraysExample(List<Teacher> teachers) {
    for (Teacher teacher : teachers) {
      System.out.println(teacher.getName());
    }
  }

  private static void printGetFromArraysGenericExample(List<? extends Teacher> teachers) {
    for (Teacher teacher : teachers) {
      System.out.println(teacher.getName());
    }
  }

  private static void putToArraysExample() {
    List<MathTeacher> mathTeachers = new ArrayList<>();
    putToArraysExample(mathTeachers);
    List<Teacher> teachers = new ArrayList<>();
    // 下面这个就编译不过
//    putToArraysExample(teachers);
    // 下面这个就能编译
    putToArraysGenericExample(teachers);

  }

  private static void putToArraysExample(List<MathTeacher> teachers) {
    teachers.add(new MathTeacher());
    teachers.add(new ManMathTeacher());
  }

  private static void putToArraysGenericExample(List< ? super MathTeacher> teachers) {
    teachers.add(new MathTeacher());
    teachers.add(new ManMathTeacher());
  }

}
