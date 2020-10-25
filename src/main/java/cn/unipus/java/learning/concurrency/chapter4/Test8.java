package cn.unipus.java.learning.concurrency.chapter4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
*   使用ReentrantLock实现线程按固定顺序打印
* */
public class Test8 {
    static ReentrantLock lock = new ReentrantLock();
    static Condition cond1 = lock.newCondition();
    static boolean isRun = false;

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                lock.lock();
                try {
                    while (!isRun) {
                        try {
                            cond1.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    isRun = false;
                    System.out.print("t1");
                    cond1.signal();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                lock.lock();
                try {
                    while (isRun) {
                        try {
                            cond1.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("t2");
                    isRun = true;
                    cond1.signal();
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }
}
