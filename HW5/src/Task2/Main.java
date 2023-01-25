package Task2;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        int[] array = {5, 6, 3, 2, 5, 1, 4, 9};

        qSort(array);
        printArray(array);

        arrayAssert();
    }

    public static void qSort(int[] array) {
        recursionQSort(array, 0, array.length - 1);
    }

    public static void recursionQSort(int[] array, int min, int max) {
        if (array.length == 0) {
            return;
        }
        if (min >= max) {
            return;
        }
        int mid = min + (max - min) / 2;
        int midElem = array[mid];
        int x = min;
        int y = max;
        while (x <= y) {
            while (array[x] < midElem) {
                x++;
            }
            while (array[y] > midElem) {
                y--;
            }
            if (x <= y) {
                int temp = array[x];
                array[x] = array[y];
                array[y] = temp;
                x++;
                y--;
            }
        }
        if (min < y) {
            recursionQSort(array, min, y);
        }
        if (max > x) {
            recursionQSort(array, x, max);
        }
    }

    public static void printArray(int[] arr) {
        for (int y : arr) {
            System.out.print(y + " ");
        }
    }

    public static void arrayAssert() {
        int[] arrayNotSorted = {7, 6, 8, 3, 2, 5, 1, 5, 10, 4, 9, 8};
        int[] arraySorted = {1, 2, 3, 4, 5, 5, 6, 7, 8, 8, 9, 10};
        qSort(arrayNotSorted);
        assert Arrays.equals(arrayNotSorted, arraySorted) : "Incorrect sort!";
    }
}
