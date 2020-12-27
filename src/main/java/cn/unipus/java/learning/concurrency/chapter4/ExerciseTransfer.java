package cn.unipus.java.learning.concurrency.chapter4;

import java.util.Random;

/**
 * @author： wuxinle
 * @date： 2020/11/22 22:45
 * @description： 转账类型
 * @modifiedBy：
 * @version: 1.0
 */
public class ExerciseTransfer {
    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(1000);
        Account b = new Account(1000);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                a.transfer(b, randomAmount());
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                b.transfer(a, randomAmount());
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("总金额: " + (a.getMoney() + b.getMoney()));
    }

    // Random 为线程安全
    static Random random = new Random();
    // 随机 1~100
    public static int randomAmount() {
        return random.nextInt(100) +1;
    }
}

class Account {
    private int money;

    public Account(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void transfer(Account target, int amount) {
        //这里有两个共享变量，需要放大锁的范围
        synchronized (Account.class) {
            if (this.money > amount) {
                setMoney(getMoney() - amount);
                target.setMoney(target.getMoney() + amount);
            }
        }
    }
}
