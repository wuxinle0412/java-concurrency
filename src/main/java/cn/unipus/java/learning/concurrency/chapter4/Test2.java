package cn.unipus.java.learning.concurrency.chapter4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
*    使用Lock接口实现线程交替打印
* */
public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        final Lock lock = new ReentrantLock();
        Condition first = lock.newCondition();
        Condition second = lock.newCondition();
        Condition third = lock.newCondition();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                lock.lock();
                try {
                    first.await();
                    System.out.print("a");
                    second.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                lock.lock();
                try {
                    second.await();
                    System.out.print("b");
                    third.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                lock.lock();
                try {
                    third.await();
                    System.out.print("c");
                    first.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        TimeUnit.SECONDS.sleep(2);

        lock.lock();
        try {
            first.signal();
        } finally {
            lock.unlock();
        }
    }
}

