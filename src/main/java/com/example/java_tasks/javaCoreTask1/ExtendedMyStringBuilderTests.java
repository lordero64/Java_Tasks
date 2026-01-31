package com.example.java_tasks.javaCoreTask1;

public class ExtendedMyStringBuilderTests {
    public static void main(String[] args) {
        System.out.println("=== РАСШИРЕННЫЕ ТЕСТЫ MyStringBuilder ===\n");

        test1_DeleteOperations();
        test2_ReverseOperations();
        test3_MixedOperations();
        test4_EdgeCases();
    }

    /**
     * Тест 1: Операции удаления (delete)
     */
    private static void test1_DeleteOperations() {
        System.out.println("=== Тест 1: Операции УДАЛЕНИЯ (delete) ===\n");

        MyStringBuilder sb = new MyStringBuilder("Hello Beautiful World");
        System.out.println("1. Исходная строка: \"" + sb + "\"");
        System.out.println("   availableUndos: " + sb.availableUndos());

        System.out.println("\n2. Удаляем слово 'Beautiful' (позиции 6-16):");
        sb.delete(6, 16); // Удаляем "Beautiful "
        System.out.println("   После delete(6, 16): \"" + sb + "\"");
        System.out.println("   availableUndos: " + sb.availableUndos());

        System.out.println("\n3. Удаляем последнее слово:");
        sb.delete(sb.toString().indexOf("World"), sb.length());
        System.out.println("   После удаления 'World': \"" + sb + "\"");
        System.out.println("   availableUndos: " + sb.availableUndos());

        System.out.println("\n4. Удаляем всё (clear):");
        sb.delete(0, sb.length());
        System.out.println("   После delete(0, length()): \"" + sb + "\"");
        System.out.println("   availableUndos: " + sb.availableUndos());

        System.out.println("\n--- Проверяем UNDO для delete ---");
        System.out.println("5. undo() - восстанавливаем предыдущее состояние:");
        sb.undo();
        System.out.println("   После undo: \"" + sb + "\"");

        System.out.println("\n6. Еще undo():");
        sb.undo();
        System.out.println("   После undo: \"" + sb + "\"");

        System.out.println("\n7. И еще undo():");
        sb.undo();
        System.out.println("   После undo: \"" + sb + "\"");

        System.out.println("\n8. Последний undo (возврат к исходному):");
        sb.undo();
        System.out.println("   После undo: \"" + sb + "\"");
        System.out.println("   availableUndos: " + sb.availableUndos());

        // Пытаемся отменить дальше - не должно измениться
        sb.undo();
        System.out.println("\n9. Попытка отменить дальше (не должно измениться):");
        System.out.println("   Результат: \"" + sb + "\"");
    }

    /**
     * Тест 2: Операции разворота (reverse)
     */
    private static void test2_ReverseOperations() {
        System.out.println("\n\n=== Тест 2: Операции РАЗВОРОТА (reverse) ===\n");

        MyStringBuilder sb = new MyStringBuilder("ABCDE");
        System.out.println("1. Исходная строка: \"" + sb + "\"");
        System.out.println("   availableUndos: " + sb.availableUndos());

        System.out.println("\n2. Первый reverse():");
        sb.reverse();
        System.out.println("   После reverse: \"" + sb + "\"");
        System.out.println("   availableUndos: " + sb.availableUndos());

        System.out.println("\n3. Добавляем текст и снова reverse:");
        sb.append("XYZ");
        System.out.println("   После append('XYZ'): \"" + sb + "\"");
        sb.reverse();
        System.out.println("   После reverse: \"" + sb + "\"");
        System.out.println("   availableUndos: " + sb.availableUndos());

        System.out.println("\n4. Еще один reverse (должен вернуть к предыдущему):");
        sb.reverse();
        System.out.println("   После reverse: \"" + sb + "\"");
        System.out.println("   availableUndos: " + sb.availableUndos());

        System.out.println("\n--- Проверяем цепочку UNDO для reverse ---");
        System.out.println("5. undo() - отменяем последний reverse:");
        sb.undo();
        System.out.println("   После undo: \"" + sb + "\"");

        System.out.println("\n6. undo() - отменяем append:");
        sb.undo();
        System.out.println("   После undo: \"" + sb + "\"");

        System.out.println("\n7. undo() - отменяем первый reverse:");
        sb.undo();
        System.out.println("   После undo: \"" + sb + "\"");

        System.out.println("\n8. undo() - возвращаемся к исходному:");
        sb.undo();
        System.out.println("   После undo: \"" + sb + "\"");
        System.out.println("   availableUndos: " + sb.availableUndos());
    }

    /**
     * Тест 3: Смешанные операции
     */
    private static void test3_MixedOperations() {
        System.out.println("\n\n=== Тест 3: СМЕШАННЫЕ ОПЕРАЦИИ ===\n");

        MyStringBuilder sb = new MyStringBuilder("Start");
        System.out.println("1. Исходная строка: \"" + sb + "\"");

        System.out.println("\n2. Цепочка операций:");
        sb.append(" Middle");
        System.out.println("   append(' Middle'): \"" + sb + "\"");

        sb.reverse();
        System.out.println("   reverse(): \"" + sb + "\"");

        sb.delete(3, 8);
        System.out.println("   delete(3, 8): \"" + sb + "\"");

        sb.append("END");
        System.out.println("   append('END'): \"" + sb + "\"");

        sb.insert(0, "PRE-");
        System.out.println("   insert(0, 'PRE-'): \"" + sb + "\"");

        System.out.println("\n   Итог: \"" + sb + "\"");
        System.out.println("   availableUndos: " + sb.availableUndos());

        System.out.println("\n--- Отмена ВСЕХ операций по одной ---");
        int totalUndos = sb.availableUndos();
        for (int i = 1; i <= totalUndos; i++) {
            sb.undo();
            System.out.printf("   undo %d: \"%s\" (осталось отмен: %d)%n",
                    i, sb, sb.availableUndos());
        }

        System.out.println("\n   Финальное состояние: \"" + sb + "\"");
    }

    /**
     * Тест 4: Граничные случаи
     */
    private static void test4_EdgeCases() {
        System.out.println("\n\n=== Тест 4: ГРАНИЧНЫЕ СЛУЧАИ ===\n");

        System.out.println("1. Тест с пустой строкой:");
        MyStringBuilder sb1 = new MyStringBuilder("");
        sb1.append("test").delete(0, 4);
        System.out.println("   После append+delete: \"" + sb1 + "\"");
        sb1.undo();
        System.out.println("   После undo: \"" + sb1 + "\"");
        sb1.undo();
        System.out.println("   Еще undo: \"" + sb1 + "\"");

        System.out.println("\n2. Тест с reverse пустой строки:");
        MyStringBuilder sb2 = new MyStringBuilder("");
        sb2.reverse();
        System.out.println("   reverse() пустой строки: \"" + sb2 + "\"");
        sb2.undo();
        System.out.println("   После undo: \"" + sb2 + "\"");

        System.out.println("\n3. Тест delete без изменений:");
        MyStringBuilder sb3 = new MyStringBuilder("Hello");
        sb3.delete(2, 2); // Ничего не удаляем (start == end)
        System.out.println("   delete(2, 2) - ничего не меняет: \"" + sb3 + "\"");
        System.out.println("   availableUndos: " + sb3.availableUndos());

        System.out.println("\n4. Тест множественных reverse:");
        MyStringBuilder sb4 = new MyStringBuilder("123");
        sb4.reverse(); // "321"
        sb4.reverse(); // "123" - обратно
        sb4.reverse(); // "321"
        System.out.println("   После 3х reverse: \"" + sb4 + "\"");

        System.out.println("\n   undo 1: ");
        sb4.undo();
        System.out.println("   Результат: \"" + sb4 + "\"");

        System.out.println("\n   undo 2: ");
        sb4.undo();
        System.out.println("   Результат: \"" + sb4 + "\"");

        System.out.println("\n   undo 3: ");
        sb4.undo();
        System.out.println("   Результат: \"" + sb4 + "\"");
    }
}
