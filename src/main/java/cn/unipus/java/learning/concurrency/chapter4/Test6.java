package cn.unipus.java.learning.concurrency.chapter4;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test6 {

    static boolean hasCigarette = false;
    static boolean hasTakeout = false;
    static ReentrantLock lock = new ReentrantLock();
    static Condition waitCigaretteSet = lock.newCondition();
    static Condition waitTakeoutSet = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            lock.lock();
            try {
                while (!hasCigarette) {
                    System.out.println(Thread.currentThread().getName() + "询问，有烟没，" + hasCigarette);
                    try {
                        waitCigaretteSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " 可以开始干活了");
            } finally {
                lock.unlock();
            }
        }, "小南").start();

        new Thread(() -> {
            lock.lock();
            try {
                while (!hasTakeout) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "询问，外卖到了吗，" + hasTakeout);
                        waitTakeoutSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " 可以开始干活了");
            } finally {
                lock.unlock();
            }
        }, "小北").start();

        TimeUnit.SECONDS.sleep(5);

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("送外卖的到了");
                hasTakeout = true;
                waitTakeoutSet.signal();
            } finally {
                lock.unlock();
            }
        }, "送外卖").start();

        TimeUnit.SECONDS.sleep(2);

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("送烟的到了");
                hasCigarette = true;
                waitCigaretteSet.signal();
            } finally {
                lock.unlock();
            }
        }, "送烟").start();
    }

}
