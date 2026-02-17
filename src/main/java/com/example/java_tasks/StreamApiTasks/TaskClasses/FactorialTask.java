package com.example.java_tasks.StreamApiTasks.TaskClasses;

import java.util.concurrent.RecursiveTask;

public class FactorialTask extends RecursiveTask<Long> {
    private final int start;
    private final int end;

    public FactorialTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Конструктор для вычисления факториала числа n
     *
     * @param n число, факториал которого нужно вычислить
     */
    public FactorialTask(int n) {
        this.start = 1;
        this.end = n;
    }

    @Override
    protected Long compute() {
        if (start == end) {
            return (long) start;
        }

        int middle = start + (end - start) / 2;

        //Создаем подзадачи
        FactorialTask leftTask = new FactorialTask(start, middle);
        FactorialTask rightTask = new FactorialTask(middle + 1, end);

        rightTask.fork();

        long leftResult = leftTask.compute();
        long rightResult = rightTask.join();

        return leftResult * rightResult;
    }
}
