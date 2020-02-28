package org.hinsteny.jvm.generic;

import org.hinsteny.jvm.generic.data.Person;
import org.hinsteny.jvm.generic.data.Student;
import org.hinsteny.jvm.generic.data.Teacher;

/**
 * @author hinsteny
 * @version GenericBoundaryCall: 2020-02-28 16:23 All rights reserved.
 */
public class GenericBoundaryCall {

  public static void main(String[] args) {
    Person student = new Student();
    printPersonName(student);
    Teacher teacher = new Teacher();
    printPersonName(teacher);
  }

  private static <T extends Person> void printPersonName(T person) {
    System.out.println(person.getName());
  }

}
