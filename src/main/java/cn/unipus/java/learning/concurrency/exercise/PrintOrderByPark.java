package cn.unipus.java.learning.concurrency.exercise;

import java.util.concurrent.locks.LockSupport;

/**
 * @author： wuxinle
 * @date： 2020/11/25 22:30
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */
public class PrintOrderByPark {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + " print 1");
        }, "t1");

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " print 2");
            LockSupport.unpark(t1);
        }, "t2");

        t1.start();
        t2.start();
    }
}
