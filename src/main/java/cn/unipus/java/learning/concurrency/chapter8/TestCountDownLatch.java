package cn.unipus.java.learning.concurrency.chapter8;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author： wuxinle
 * @date： 2020/11/10 23:07
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */
public class TestCountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "begin...");
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "end...");
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "begin...");
            try {
                TimeUnit.SECONDS.sleep(2);
                latch.countDown();
                System.out.println(Thread.currentThread().getName() + "end...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "begin...");
            try {
                TimeUnit.SECONDS.sleep(3);
                latch.countDown();
                System.out.println(Thread.currentThread().getName() + "end...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t3").start();

        System.out.println("main waiting...");
        latch.await();
        System.out.println("main end...");
    }
}
