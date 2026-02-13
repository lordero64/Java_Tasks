package com.example.java_tasks.ConcurrencyTask2;

import java.util.List;
import java.util.concurrent.*;

public class ComplexTaskExecutor {
    private final int numberOfTasks;
    private final List<String> results;

    public ComplexTaskExecutor(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
        results = new CopyOnWriteArrayList<>();
    }

    public void executeTasks(int numberOfTasks) {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfTasks);

        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks, this::mergeResults);

        System.out.println("Запуск " + numberOfTasks + " задач");

        for (int i = 0; i < numberOfTasks; i++) {

            executorService.submit(() -> {
                try {
                    //Создаем задачу
                    ComplexTask task = new ComplexTask();
                    // Выполняем и получаем результат
                    String result = task.execute();
                    //Сохраняем
                    results.add(result);

                    System.out.println(Thread.currentThread().getName() + " Достиг барьера и ожидает остальные потоки");
                    barrier.await();

                    System.out.println(Thread.currentThread().getName() + "Прошел барьер и завершил работу");
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
        }

        System.out.println("\n Все задачи потоком " + Thread.currentThread().getName() + " выполнены !");
    }

    /**
     * Метод для объединения результатов всех задач
     * Выполняется при достижении барьера всеми потоками
     */
    private void mergeResults() {
        System.out.println("Все потоки собраны! Время подвести итоги! \n Результаты:");
        results.forEach(r -> System.out.println(" " + r));
        results.clear();
    }
}
