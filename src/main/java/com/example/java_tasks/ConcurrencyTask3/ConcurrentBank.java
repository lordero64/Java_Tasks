package com.example.java_tasks.ConcurrencyTask3;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentBank {
    private final Map<Long, BankAccount> accounts = new ConcurrentHashMap<>();

    public BankAccount createAccount(BigDecimal startedBalance) {

        BankAccount bankAccount = new BankAccount(startedBalance);
        accounts.put(bankAccount.getId(), bankAccount);

        System.out.printf(" Создан счет #%d с балансом %s%n",
                bankAccount.getId(), startedBalance.toString());

        return bankAccount;
    }

    // Удобный метод с long
    public BankAccount createAccount(long startedBalance) {
        return createAccount(BigDecimal.valueOf(startedBalance));
    }

    /**
     * Перевод между счетами
     * @param from счет снятия
     * @param to счет пополнения
     * @param amount сумма перевода
     */
    public void transfer(BankAccount from, BankAccount to, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной");
        }

        if (from == to) throw new IllegalArgumentException("нельзя перевести на тот же счет");

        //Упорядоченная блокировка по ID для предотвращения deadlock
        BankAccount firstLock = from.getId() < to.getId() ? from : to;
        BankAccount secondLock = firstLock == from ? to : from;

        firstLock.getLock().lock();
        try {
            secondLock.getLock().lock();
            try {
                if (from.getBalance().compareTo(amount) < 0) {
                    System.out.printf("Недостаточно средств на счете #%d для перевода %s%n",
                            from.getId(), amount.toString());
                    return;
                }

                // Выполняем перевод
                from.withdraw(amount);
                to.deposit(amount);

                System.out.printf("Перевод %s со счета #%d на счет #%d выполнен%n",
                        amount.toString(), from.getId(), to.getId());

            } finally {
                secondLock.getLock().unlock();
            }
        } finally {
            firstLock.getLock().unlock();
        }
    }

    /**
     * Перегрузка для long
     */
    public void transfer(BankAccount from, BankAccount to, long amount) {
        transfer(from, to, BigDecimal.valueOf(amount));
    }

    public BigDecimal getTotalBalance() {
        BigDecimal total = BigDecimal.ZERO;

        for (BankAccount account : accounts.values()) {
            total = total.add(account.getBalance());
        }

        return total;
    }
}
