package org.hinsteny.jvm.generic;

/**
 * @author hinsteny
 * @version GenericMethodDealWithArrays: 2020-02-28 15:57 All rights reserved.
 */
public class GenericMethodDealWithArrays {

  public static void main(String args[]) {
    // Create arrays of Integer, Double and Character
    Integer[] intArray = {1, 2, 3, 4, 5};
    Double[] doubleArray = {1.1, 2.2, 3.3, 4.4};
    Character[] charArray = {'H', 'E', 'L', 'L', 'O'};

    System.out.println("Array integerArray contains:");
    // pass an Integer array
    printArray(intArray);

    System.out.println("\nArray doubleArray contains:");
    // pass a Double array
    printArray(doubleArray);

    System.out.println("\nArray characterArray contains:");
    // pass a Character array
    printArray(charArray);

  }

  /**
   * generic method printArray
   * @param inputArray
   * @param <E>
   */
  private static <E> void printArray(E[] inputArray) {
    // Display array elements
    for (E element : inputArray) {
      System.out.printf("%s ", element);
    }
    System.out.println();
  }

}
