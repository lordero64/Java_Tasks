package com.example.java_tasks.ConcurrencyTask2;

import java.util.concurrent.atomic.AtomicInteger;

public class ComplexTask {
    private static final AtomicInteger taskCounter = new AtomicInteger(0);
    private final  int taskId;
    private String result;

    public ComplexTask() {
        this.taskId = taskCounter.incrementAndGet();
    }

    public String execute() {
        System.out.println(Thread.currentThread().getName() + "Начал выполнять сложную задачу - " + taskId);

        try {
            //Имитация работы
            Thread.sleep((long) (Math.random() *1000 + 500));

            result = "Результат задачи № " + taskId + " потока" + Thread.currentThread().getName();

            System.out.println("Поток " + Thread.currentThread().getName() + " завершил задачу № " + taskId);

            return result;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public int getTaskId() {
        return taskId;
    }

    public String getResult() {
        return result;
    }
}
