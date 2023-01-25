package Task2;

/*›
Task2
    [3, 4, 2, 7], 10 -> [3, 7] - вывести пару именно в скобках, которые дают сумму - 10
 */

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] inputArray = {3, 4, 2, 7};
        int targetSum = 10;
        int[] resultArray = getSumTerms(inputArray, targetSum);
        if (resultArray != null) {
            System.out.println(Arrays.toString(resultArray));
        }
        assertGetSumTerms();
    }

    public static int[] getSumTerms(int[] array, int targetSum) {
        if (array == null || array.length < 2)
            return null;
        Arrays.sort(array);
        int firstIndex = 0;
        int lastIndex = array.length - 1;
        while (firstIndex < lastIndex) {
            if (array[firstIndex] + array[lastIndex] == targetSum) {
                return new int[]{array[firstIndex], array[lastIndex]};
            } else if (array[firstIndex] + array[lastIndex] > targetSum) {
                lastIndex--;
            } else {
                firstIndex++;
            }
        }
        return null;
    }

    public static void assertGetSumTerms() {
        final int[] array = {5, 7, 4, 9, 1};
        final int[] templateArray = {4, 9};
        final int targetSum = 13;
        final int[] resultArray = getSumTerms(array, targetSum);
        assert Arrays.equals(templateArray, resultArray) : "Incorrect array!";
    }
}