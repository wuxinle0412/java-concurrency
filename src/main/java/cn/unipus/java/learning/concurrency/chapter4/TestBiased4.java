package cn.unipus.java.learning.concurrency.chapter4;

/**
 * @author： wuxinle
 * @date： 2020/12/3 22:46
 * @description： 批量重偏向
 * @modifiedBy：
 * @version: 1.0
 */

import org.openjdk.jol.info.ClassLayout;

import java.util.Vector;
import java.util.concurrent.locks.LockSupport;

/**
 *    如果线程虽然被多个线程访问，但是没有竞争，这时偏向线程t1的线程仍然有机会偏向t2，重偏向会重置对象的Thread ID。
 *    当撤销偏向锁阈值超过20次后，jvm会这样觉得，我是不是偏向错了呢，于是会在给这些对象加锁时重新偏向至加锁线程
 * */
public class TestBiased4 {
    public static void main(String[] args) throws InterruptedException {
        test4();
    }

    private static void test2() {
        Vector<Dog> list = new Vector<>();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                Dog d = new Dog();
                list.add(d);
                synchronized (d) {
                    System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
            }
            synchronized (list) {
                list.notify();
            }
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            synchronized (list) {
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            print("===============> ");
            for (int i = 0; i < 30; i++) {
                Dog d = list.get(i);
                System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                synchronized (d) {
                    System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
                System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
            }
        }, "t2");
        t2.start();
    }

    static Thread t1,t2,t3;
    private static void test4() throws InterruptedException {
        Vector<Dog> list = new Vector<>();
        int loopNumber = 39;
        t1 = new Thread(() -> {
            for (int i = 0; i < loopNumber; i++) {
                Dog d = new Dog();
                list.add(d);
                synchronized (d) {
                    System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
            }
            LockSupport.unpark(t2);
        }, "t1");
        t1.start();
        t2 = new Thread(() -> {
            LockSupport.park();
            System.out.println(" t2 ===============> ");
            for (int i = 0; i < loopNumber; i++) {
                Dog d = list.get(i);
                System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                synchronized (d) {
                    System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
                System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
            }
            LockSupport.unpark(t3);
        }, "t2");
        t2.start();
        t3 = new Thread(() -> {
            LockSupport.park();
            System.out.println("t3 ===============> ");
            for (int i = 0; i < loopNumber; i++) {
                Dog d = list.get(i);
                System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                synchronized (d) {
                    System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
                System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
            }
        }, "t3");
        t3.start();
        t3.join();
        System.out.println(ClassLayout.parseInstance(new Dog()).toPrintable());
    }


    public static void print(String str) {
        System.out.println(Thread.currentThread().getName() + " : " + System.currentTimeMillis() + " : " + str);
    }

    static class Dog {

    }
}
