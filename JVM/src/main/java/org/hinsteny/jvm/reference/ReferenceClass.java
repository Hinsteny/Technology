package org.hinsteny.jvm.reference;

/**
 * 引用对象
 * @author Hinsteny
 * @version $ID: ReferenceClass 2018-06-13 11:34 All rights reserved.$
 */
public class ReferenceClass {

    public static final String classField = "classFieldValue";

    private String field = "fieldVale";

    public String getField() {
        return field;
    }

    public static ReferenceClass getReference() {
        return new ReferenceClass();
    }

    @Override
    public String toString() {
        return "ReferenceClass{" +
                "field='" + field + '\'' +
                '}';
    }
}
