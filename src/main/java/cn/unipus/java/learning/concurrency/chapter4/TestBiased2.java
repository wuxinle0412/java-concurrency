package cn.unipus.java.learning.concurrency.chapter4;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.CountDownLatch;

/**
 * @author： wuxinle
 * @date： 2020/12/3 22:26
 * @description： 测试当其他线程使用对象时偏向锁升级为轻量级锁(顺序无竞争)
 * @modifiedBy：
 * @version: 1.0
 */
/**
 *   撤销偏向锁
 *   1.其他线程顺序使用，升级为轻量级锁
 *   2.调用对象的wait/notify方法，升级为重量级锁。
 * */
public class TestBiased2 {
    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        Dog dog = new Dog();
        ClassLayout layout = ClassLayout.parseInstance(dog);

        new Thread(() -> {
            synchronized (dog) {
                System.out.println(layout.toPrintable());
            }
            countDownLatch.countDown();
        }, "t1").start();

        new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            print("t2 synchronized 使用前");
            //偏向t1
            System.out.println(layout.toPrintable());
            synchronized (dog) {
                //升级为轻量级锁
                System.out.println(layout.toPrintable());
            }

            print("t2 synchronized 使用后");
            // 释放后，变为正常无偏向对象
            System.out.println(layout.toPrintable());
        }, "t2").start();

    }

    public static void print(String str) {
        System.out.println(Thread.currentThread().getName() + " : " + System.currentTimeMillis() + " : " + str);
    }

    static class Dog {

    }
}
