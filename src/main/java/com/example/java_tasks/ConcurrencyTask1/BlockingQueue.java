package com.example.java_tasks.ConcurrencyTask1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Простейшая блокирующая очередь реализованная через монитор
 *
 * @param <T> тип параметра метода
 */
public class BlockingQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;
    private final Object lock = new Object();

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void enqueue(T item) throws InterruptedException {
        synchronized (lock) {
            if (queue.size() >= capacity) {
                System.out.println(Thread.currentThread().getName() + " : Достигнут предел местимости" + size() + "/" + capacity + ". Ожидайте");
                lock.wait();
            }

            queue.add(item);
            System.out.println(Thread.currentThread().getName() + " : Добавлен эелемент" + item.toString() + "! Вместимость = " + size() + "/" + capacity);
            lock.notifyAll();
        }
    }

    public T dequeue() throws InterruptedException {
        synchronized (lock) {
            if (queue.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + ": Очередь пуста! Ожидайте!");
                lock.wait();
            }

            T item = queue.poll();
            System.out.println(Thread.currentThread().getName() + ": Извлекает элемент! Очередь = " + size() + "/" + capacity);
            lock.notifyAll();
            return item;
        }
    }

    public int size() {
        synchronized (lock) {
            return queue.size();
        }
    }

    public boolean isEmpty() {
        synchronized (lock) {
            return queue.isEmpty();
        }
    }

    // Для удобства сразу пробуем реализацию в этом же классе
    public static void main(String[] args) {
        BlockingQueue<String> queue = new BlockingQueue<>(3);

        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        Random random = new Random();

        //Запуск производителей
        for (int i = 0; i <= 3; i++) {
            final int producerId = i;

            threadPool.submit(() -> {
                try {
                    for (int j = 0; j < 5; j++) {
                        String task = "Задача -" + producerId + "-" + j;
                        queue.enqueue(task);

                        //имитация производства
                        Thread.sleep(random.nextInt(1000) + 500);
                    }

                    System.out.println("Производитель " + producerId + " прекратил работу");

                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    System.out.println("Производитель " + producerId + " прерван");
                }
            });
        }

        //Запуск потребителей
        for (int i = 0; i <= 3; i++) {
            final int consumerId = i;
            threadPool.submit(() -> {
                try {
                    for (int j = 0; j < 5; j++) {
                        String task = queue.dequeue();

                        //имитация обработки задачи
                        Thread.sleep(random.nextInt(800) + 200);
                    }
                    System.out.println("Потребитель " + consumerId + " завершил работу!");
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    System.out.println("Потребитель " + consumerId + " прерван");
                }
            });
        }

        threadPool.shutdown();
        try {
            if (threadPool.awaitTermination(30, TimeUnit.SECONDS)) {
                System.out.println("Все задачи успешно завершены");
            } else {
                threadPool.shutdownNow();
                System.out.println("Принудительное завершение работы");
            }
        } catch (InterruptedException exception) {
            threadPool.shutdownNow();
        }

        // Финальная статистика
        System.out.println(" \n Осталось элементов в очереди: " + queue.size());
    }
}
