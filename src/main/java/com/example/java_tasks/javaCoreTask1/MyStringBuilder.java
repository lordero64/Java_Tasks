package com.example.java_tasks.javaCoreTask1;

import java.util.Stack;

/**
 * Упрощенный StringBuilder с поддержкой возврата состояния (undo)
 */
public class MyStringBuilder {
    private StringBuilder value;
    private final Stack<SnapshotValue> saves;

    public MyStringBuilder() {
        this.value = new StringBuilder();
        this.saves = new Stack<>();
        saveCurrentState();
    }

    public MyStringBuilder(String string) {
        this.value = new StringBuilder(string);
        this.saves = new Stack<>();
        saveCurrentState();
    }

    /**
     * Внутриений класс для сохранения (Снимок)
     */
    private static class SnapshotValue {
        private final String value;

        public SnapshotValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Сохраняет текущее состояние
     */
    private void saveCurrentState() {
        SnapshotValue snapshotValue = new SnapshotValue(value.toString());
        saves.push(snapshotValue);

    }

    public void undo() {
        if (saves.size() > 1) { // ← ИЗМЕНИТЕ УСЛОВИЕ
            saves.pop();

            SnapshotValue previous = saves.peek();
            value = new StringBuilder(previous.getValue());
        }
    }

    /**
     * Показывает доступные отмены
     *
     * @return количество доступных отмен
     */
    public int availableUndos() {
        return Math.max(saves.size() - 1, 0);
    }

    // Основные методы StringBuilder
    public MyStringBuilder append(String str) {
        value.append(str);
        saveCurrentState();
        return this;
    }

    public MyStringBuilder append(Object obj) {
        return append(String.valueOf(obj));
    }

    public MyStringBuilder append(int i) {
        return append(String.valueOf(i));
    }

    public MyStringBuilder insert(int offset, String str) {
        value.insert(offset, str);
        saveCurrentState();
        return this;
    }

    public MyStringBuilder delete(int start, int end) {
        value.delete(start, end);
        saveCurrentState();
        return this;
    }

    public MyStringBuilder reverse() {
        value.reverse();
        saveCurrentState();
        return this;
    }

    public MyStringBuilder replace(int start, int end, String str) {
        value.replace(start, end, str);
        saveCurrentState();
        return this;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public int length() {
        return value.length();
    }
}
