package cn.unipus.java.learning.concurrency.exercise;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wuxinle
 * @version 1.0
 * @date 2020/11/24 17:10
 * Description: 使用ReentrantLock实现线程间交替打印
 */
public class PrintOrderByLock {
    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        Condition cond1 = lock.newCondition();

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                cond1.await();
                System.out.println(Thread.currentThread().getName() + " print 1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " print 2");
                cond1.signal();
            } finally {
                lock.unlock();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
