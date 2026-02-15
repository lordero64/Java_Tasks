package com.example.java_tasks.ConcurrencyTask3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private static final AtomicLong idGenerator = new AtomicLong(0);
    private final long id;
    private BigDecimal balance;
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccount(BigDecimal startBalance) {
        this.id = idGenerator.incrementAndGet();
        this.balance = startBalance;
    }

    /**
     * Пополнение счета
     *
     * @param amount сумма пополнения
     */
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Пололнение должно быть положительным");
        }

        try {
            lock.lock();

            balance = balance.add(amount);

            System.out.printf("%s : Пополнение счета %d на %.2f. Баланс : %.2f%n",
                    Thread.currentThread().getName(),
                    id,
                    amount.setScale(2, RoundingMode.HALF_EVEN),
                    balance.setScale(2, RoundingMode.HALF_EVEN));
        } finally {
            lock.unlock();
        }
    }

    // Перегрузка для удобства с long
    public void deposit(long amount) {
        deposit(BigDecimal.valueOf(amount));
    }

    /**
     * Снятие со счета
     *
     * @param amount сумма к списанию
     * @return если успешно то true
     */
    public boolean withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Снятие должно быть положительным");
        }

        lock.lock();
        try {
            if (balance.compareTo(amount) < 0) {
                System.out.println("Недостаточно средств");
                return false;
            }

            balance = balance.subtract(amount);

            System.out.printf("%s : Снятие со счета %d на %.2f. Баланс : %.2f%n",
                    Thread.currentThread().getName(),
                    id,
                    amount.setScale(2, RoundingMode.HALF_EVEN),
                    balance.setScale(2, RoundingMode.HALF_EVEN));

        } finally {
            lock.unlock();
        }

        return true;
    }

    // Перегрузка для удобства с long
    public boolean withdraw(long amount) {
        return withdraw(BigDecimal.valueOf(amount));
    }

    /**
     * Получение баланса
     */
    public BigDecimal getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public long getId() {
        return id;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    @Override
    public String toString() {
        lock.lock();
        try {
            return String.format("Счет %d: баланс %.2f",
                    id,
                    balance.setScale(2, RoundingMode.HALF_EVEN));
        } finally {
            lock.unlock();
        }
    }
}
