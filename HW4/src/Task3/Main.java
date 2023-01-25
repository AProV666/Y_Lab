package Task3;

/*
Task3
    Реализовать функцию нечеткого поиска

            fuzzySearch("car", "ca6$$#_rtwheel"); // true
            fuzzySearch("cwhl", "cartwheel"); // true
            fuzzySearch("cwhee", "cartwheel"); // true
            fuzzySearch("cartwheel", "cartwheel"); // true
            fuzzySearch("cwheeel", "cartwheel"); // false
            fuzzySearch("lw", "cartwheel"); // false
 */

public class Main {
    public static void main(String[] args) {
        System.out.println(fuzzySearch("car", "ca6$$#_rtwheel"));
        System.out.println(fuzzySearch("cwhl", "cartwheel"));
        System.out.println(fuzzySearch("cwhee", "cartwheel"));
        System.out.println(fuzzySearch("cartwheel", "cartwheel"));
        System.out.println(fuzzySearch("cwheeel", "cartwheel"));
        System.out.println(fuzzySearch("lw", "cartwheel"));
        assertFuzzySearch();
    }

    public static boolean fuzzySearch(String needle, String haystack) {
        if(needle == null || haystack == null) {
            return false;
        }
        String[] charArray = needle.split("");
        int[] indexArray = new int[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            if (i == 0) {
                indexArray[i] = haystack.indexOf(charArray[i]);
            } else {
                indexArray[i] = haystack.indexOf(charArray[i], indexArray[i - 1] + 1);
            }
        }
        int count = 1;
        for (int i = 0; i < indexArray.length - 1; i++) {
                if (indexArray[i] < indexArray[i + 1]) {
                    count++;
            }
        }
        return count == indexArray.length;
    }

    public static void assertFuzzySearch() {
        String searchText1 = "Alice";
        String searchText2 = "bmx";
        String searchText3 = "foxirubAfbklt643i@#m&ctfjwn1";
        String text = "foxirubAfbklt643i@#m&ctfjwne";
        assert fuzzySearch(searchText1, text) : "Incorrect search!";
        assert !fuzzySearch(searchText2, text) : "Incorrect search!";
        assert !fuzzySearch(searchText3, text) : "Incorrect search!";
    }
}