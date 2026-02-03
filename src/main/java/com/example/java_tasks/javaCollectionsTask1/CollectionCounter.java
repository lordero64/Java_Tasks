package com.example.java_tasks.javaCollectionsTask1;

import java.util.HashMap;
import java.util.Map;

public class CollectionCounter {
    /**
     * Подсчитывает количество вхождений каждого элемента в массиве
     *
     * @param elements массив элементов для подсчета
     * @param <T>      тип элементов массива
     * @return Map, где ключи - элементы, значения - количество их вхождений
     */
    public static <T> Map<T, Integer> countElements(T[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException("Массив не может быть null");
        }

        Map<T, Integer> resultMap = new HashMap<>();

        for (T element : elements) {
            int count = resultMap.getOrDefault(element, 0);

            resultMap.put(element, count + 1);
        }

        return resultMap;
    }

    public static <T> void printCounts(Map<T, Integer> counts) {
        System.out.println("Результат подсчета:");
        for (Map.Entry<T, Integer> entry : counts.entrySet()) {
            System.out.printf("Элемент '%s' встречается %d раз(а)%n", entry.getKey(), entry.getValue());
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Тест 1: Строки
        System.out.println("=== Тест 1: Строки ===");
        String[] words = {"яблоко", "банан", "яблоко", "апельсин", "банан", "яблоко"};
        Map<String, Integer> wordCounts = countElements(words);
        printCounts(wordCounts);

        // Тест 2: Числа
        System.out.println("=== Тест 2: Числа ===");
        Integer[] numbers = {1, 2, 3, 2, 1, 1, 4, 5, 4, 1};
        Map<Integer, Integer> numberCounts = countElements(numbers);
        printCounts(numberCounts);

        // Тест 3: Символы
        System.out.println("=== Тест 3: Символы ===");
        Character[] chars = {'a', 'b', 'c', 'a', 'b', 'a', 'd'};
        Map<Character, Integer> charCounts = countElements(chars);
        printCounts(charCounts);

        // Тест 4: Пустой массив
        System.out.println("=== Тест 4: Пустой массив ===");
        String[] empty = {};
        Map<String, Integer> emptyCounts = countElements(empty);
        System.out.println("Пустой массив: " + emptyCounts);
        System.out.println();

        // Тест 5: Собственные объекты
        System.out.println("=== Тест 5: Пользовательские объекты ===");
        Person[] people = {
                new Person("Иван"),
                new Person("Мария"),
                new Person("Иван"),
                new Person("Петр")
        };
        Map<Person, Integer> personCounts = countElements(people);
        printCounts(personCounts);
    }

    // Для проверки объектов я использовал Record
    record Person(String name) {
    }
}
