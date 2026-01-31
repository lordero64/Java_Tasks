package com.example.java_tasks;

import com.example.java_tasks.javaCoreTask1.MyStringBuilder;

public class TestMyStringBuilder {
    public static void main(String[] args) {
        System.out.println("=== Тест 1: Пустой StringBuilder ===");
        MyStringBuilder sb1 = new MyStringBuilder();
        System.out.println("Создан: \"" + sb1 + "\"");
        System.out.println("Доступно отмен: " + sb1.availableUndos()); // 0

        sb1.append("Hello");
        System.out.println("append('Hello'): \"" + sb1 + "\"");
        System.out.println("Доступно отмен: " + sb1.availableUndos()); // 1

        sb1.append(" World");
        System.out.println("append(' World'): \"" + sb1 + "\"");
        System.out.println("Доступно отмен: " + sb1.availableUndos()); // 2

        System.out.println("\n--- UNDO 1 ---");
        sb1.undo();
        System.out.println("После undo: \"" + sb1 + "\""); // "Hello"
        System.out.println("Доступно отмен: " + sb1.availableUndos()); // 1

        System.out.println("\n--- UNDO 2 ---");
        sb1.undo();
        System.out.println("После undo: \"" + sb1 + "\""); // ""
        System.out.println("Доступно отмен: " + sb1.availableUndos()); // 0

        System.out.println("\n--- UNDO 3 (не должно измениться) ---");
        sb1.undo();
        System.out.println("После попытки undo: \"" + sb1 + "\""); // ""

        System.out.println("\n=== Тест 2: StringBuilder с начальным значением ===");
        MyStringBuilder sb2 = new MyStringBuilder("Начало");
        System.out.println("Создан: \"" + sb2 + "\"");
        System.out.println("Доступно отмен: " + sb2.availableUndos()); // 0

        sb2.append(" -> продолжение");
        System.out.println("append(' -> продолжение'): \"" + sb2 + "\"");
        System.out.println("Доступно отмен: " + sb2.availableUndos()); // 1

        sb2.insert(6, "очень ");
        System.out.println("insert(6, 'очень '): \"" + sb2 + "\"");
        System.out.println("Доступно отмен: " + sb2.availableUndos()); // 2

        System.out.println("\n--- UNDO ---");
        sb2.undo();
        System.out.println("undo(): \"" + sb2 + "\""); // "Начало -> продолжение"

        sb2.undo();
        System.out.println("undo(): \"" + sb2 + "\""); // "Начало"

        sb2.undo();
        System.out.println("undo() (не должно измениться): \"" + sb2 + "\""); // "Начало"
    }
}
