package com.example.java_tasks.javaCollectionsTask1;

import java.util.Arrays;

public class MainFilter {
    /**
     * Метод применяет функцию преобразования ко всем элементам массива
     *
     * @param array  массив подлежащий модифицированию
     * @param filter объект реализующий интерфейс Filter
     * @param <T> тип элементов массива
     * @return новый массив с преобразованными элементами
     */
    public static <T> T[] filter(T[] array, Filter<T> filter) {

        if (array == null || filter == null) {
            throw new IllegalArgumentException("Входные параметры не могут быть null");
        }

        // Использование массива налагает опредленные ограничения. Решил вместо рефлексии использовать этот вариант попроще
        T[] newArray = Arrays.copyOf(array, array.length);

        for (int i = 0; i < array.length; i++) {
            newArray[i] = filter.apply(array[i]);
        }

        return newArray;
    }

    public static void main(String[] args) {
        //Работа со стрингами

        String[] oldArray = {"JavaCode", "Evgeniy", "Success"};

        //Создаем анонимный класс реализующий интефейс Filter с типом String. Для наглядности сначало полностью
        Filter<String> toUpperCaseFilter = new Filter<String>() {
            @Override
            public String apply(String o) {
                return o.toUpperCase();
            }
        };

        String[] newArray = filter(oldArray, toUpperCaseFilter);

        for (int i = 0; i < newArray.length; i++) {
            System.out.println(newArray[i]);
        }

        //Работа с числами

        Integer[] numbers = {2, 3, 6, 8};

        Filter<Integer> squareNumbersFilter = o -> o * o;

        Integer[] newNumbers = filter(numbers, squareNumbersFilter);

        for (int i = 0; i < newNumbers.length; i++) {
            System.out.println(newNumbers[i]);
        }
    }
}
