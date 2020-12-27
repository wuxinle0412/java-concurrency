package cn.unipus.java.learning.concurrency.chapter6;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 *   测试原子引用类
 * */

public class TestAtomicReference {
    public static void main(String[] args) {
        //线程不安全的实现
        DecimalAccountUnsafe acc1 = new DecimalAccountUnsafe(new BigDecimal("10000"));
        //线程安全的实现
        DecimalAccountCas acc2 = new DecimalAccountCas(new BigDecimal("10000"));
        DecimalAccount.demo(acc1);
        DecimalAccount.demo(acc2);
    }
}

class DecimalAccountUnsafe implements DecimalAccount {
    private BigDecimal decimal;

    public DecimalAccountUnsafe(BigDecimal decimal) {
        this.decimal = decimal;
    }


    @Override
    public BigDecimal getBalance() {
        return this.decimal;
    }

    @Override
    public void withdraw(BigDecimal amount) {
        BigDecimal newBigDecimal = getBalance();
        this.decimal = newBigDecimal.subtract(amount);
    }
}

class DecimalAccountCas implements DecimalAccount {

    private AtomicReference<BigDecimal> balance;

    public DecimalAccountCas(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return this.balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        while (true) {
            BigDecimal prev = this.balance.get();
            BigDecimal next = prev.subtract(amount);
            if (this.balance.compareAndSet(prev, next)) {
                break;
            }
        }
    }
}

interface DecimalAccount {
    // 获取余额
    BigDecimal getBalance();
    // 取款
    void withdraw(BigDecimal amount);
    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void demo(DecimalAccount account) {
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(BigDecimal.TEN);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(account.getBalance());
    }
}