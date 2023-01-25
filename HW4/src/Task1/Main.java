package Task1;

import java.util.*;
import java.util.stream.Collectors;

/*
Task1
    Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени.
 */

public class Main {

    static class Person {
        final int id;
        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Main.Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    private static Main.Person[] RAW_DATA = new Main.Person[]{
            new Main.Person(0, "Harry"),
            new Main.Person(0, "Harry"), // дубликат
            new Main.Person(1, "Harry"), // тёзка
            new Main.Person(2, "Harry"),
            new Main.Person(3, "Emily"),
            new Main.Person(4, "Jack"),
            new Main.Person(4, "Jack"),
            new Main.Person(5, "Amelia"),
            new Main.Person(5, "Amelia"),
            new Main.Person(6, "Amelia"),
            new Main.Person(7, "Amelia"),
            new Main.Person(8, "Amelia"),
    };
        /*  Raw data:
        0 - Harry
        0 - Harry
        1 - Harry
        2 - Harry
        3 - Emily
        4 - Jack
        4 - Jack
        5 - Amelia
        5 - Amelia
        6 - Amelia
        7 - Amelia
        8 - Amelia
     */

    public static void main(String[] args) {
        System.out.println("Raw data:");
        System.out.println();

        for (Main.Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");
        System.out.println();

        /*
            Что должно получиться:
                Key: Amelia
                Value:4
                Key: Emily
                Value:1
                Key: Harry
                Value:3
                Key: Jack
                Value:1
         */

        printSortedPerson();
    }

    public static void printSortedPerson() {
        if (RAW_DATA == null) {
            return;
        }
        Map<String, Long> map = Arrays.stream(RAW_DATA)
                .filter(Objects::nonNull)
                .distinct()
                .sorted(Comparator.comparing(Main.Person::getId).thenComparing(Main.Person::getName))
                .collect(Collectors.groupingBy(Main.Person::getName, Collectors.counting()));

        map.forEach((key, value) -> System.out.println("Key: " + key + "\n" + "Value: " + value));
    }
}