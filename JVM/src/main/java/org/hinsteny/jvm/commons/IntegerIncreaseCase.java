package org.hinsteny.jvm.commons;

/**
 * @author Hinsteny
 * @version $ID: IntegerIncreaseCase 2018-05-04 18:06 All rights reserved.$
 */
public class IntegerIncreaseCase {

    public static void main(String[] args) {
        increaseIntToNegative(Integer.MAX_VALUE - 100);
    }

    /**
     * 把数字增加为负数
     * @param number
     * @return
     */
    private static boolean increaseIntToNegative(int number) {
        while (number > 0) {
            number++;
            System.out.println(String.format("Number [%s] increase to [%s]", number, number));
        }
        return true;
    }
}
