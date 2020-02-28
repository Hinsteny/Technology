package org.hinsteny.jvm.generic.data;

/**
 * @author hinsteny
 * @version MathTeacher: 2020-02-28 23:18 All rights reserved.
 */
public class MathTeacher extends Teacher {

  private String course;

  public MathTeacher(String course) {
    this.course = course;
  }

  public String getCourse() {
    return course;
  }

  public void setCourse(String course) {
    this.course = course;
  }

}
