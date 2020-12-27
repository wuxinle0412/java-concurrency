package cn.unipus.java.learning.concurrency.exercise;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author： wuxinle
 * @date： 2020/11/25 22:53
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */
public class PrintAlternateByLock {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition first = lock.newCondition();
        Condition second = lock.newCondition();
        Condition third = lock.newCondition();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                lock.lock();
                try {
                    first.await();
                    System.out.print("a ");
                    second.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

        }, "t1").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                lock.lock();
                try {
                    second.await();
                    System.out.print("b ");
                    third.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

        }, "t2").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                lock.lock();
                try {
                    third.await();
                    System.out.print("c ");
                    first.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

        }, "t3").start();

        lock.lock();
        try {
            first.signal();
        } finally {
            lock.unlock();
        }
    }
}
