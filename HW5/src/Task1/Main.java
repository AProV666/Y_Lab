package Task1;

public class Main {
    public static void main(String[] args) {

        int[][] array = getArrayWithRandomNumbers(5, 6);

        System.out.println("max = " + getMax(array));
        System.out.println("min = " + getMin(array));
        System.out.println("avg = " + getAvg(array));

        maxAssert();
        minAssert();
        avgAssert();
    }

    public static int[][] getArrayWithRandomNumbers(int a, int b) {
        int[][] arr = new int[a][b];
        int randomNumber = firstNumber();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                randomNumber = random(randomNumber);
                arr[i][j] = randomNumber;
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        return arr;
    }

    public static int random(long seed) {
        int bits = 14;
        seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
        return (int) (seed >>> (48 - bits));
    }

    public static int firstNumber() {
        long rangeBegin = 0L;
        long rangeEnd = 100L;
        long currentTime = System.currentTimeMillis();
        long randomNumber = rangeBegin + currentTime % (rangeEnd - rangeBegin);
        return (int) randomNumber;
    }

    public static double getMax(int[][] arr) {
        double max = arr[0][0];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (max < arr[i][j]) {
                    max = arr[i][j];
                }
            }
        }
        return max;
    }

    public static double getMin(int[][] arr) {
        double min = arr[0][0];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (min > arr[i][j]) {
                    min = arr[i][j];
                }
            }
        }
        return min;
    }

    public static double getAvg(int[][] arr) {
        int arrayLength = arr.length * arr[0].length;
        double avg = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                avg += (double) arr[i][j] / arrayLength;
            }
        }
        return avg;
    }

    public static void maxAssert() {
        int[][] arr = {{0, 1, 2, 3},
                       {1, 2, 3, 4},
                       {2, 3, 4, 5}};
        assert getMax(arr) == 5 : "Incorrect max value!";
    }

    public static void minAssert() {
        int[][] arr = {{0, 1, 2, 3},
                       {1, 2, 3, 4},
                       {2, 3, 4, 5}};
        assert getMin(arr) == 0 : "Incorrect min value!";
    }

    public static void avgAssert() {
        int[][] arr = {{0, 1, 2, 3},
                       {1, 2, 3, 4},
                       {2, 3, 4, 5}};
        assert getAvg(arr) == 2.5 : "Incorrect avg value!";
    }
}