package cn.unipus.java.learning.concurrency.chapter4;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author： wuxinle
 * @date： 2020/12/3 22:38
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */
public class TestBiased3 {
    public static void main(String[] args) {
        Dog d = new Dog();
        Thread t1 = new Thread(() -> {
            System.out.println("t1 : " + ClassLayout.parseInstance(d).toPrintable());
            synchronized (d) {
                System.out.println("t1 synchronized : " + ClassLayout.parseInstance(d).toPrintable());
                try {
                    d.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1 : " + ClassLayout.parseInstance(d).toPrintable());
            }
        }, "t1");

        t1.start();

        new Thread(() -> {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (d) {
                d.notify();
            }
        }, "t2").start();
    }

    public static void print(String str) {
        System.out.println(Thread.currentThread().getName() + " : " + System.currentTimeMillis() + " : " + str);
    }

    static class Dog {

    }
}
