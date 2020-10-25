package cn.unipus.java.learning.concurrency.chapter4;

import java.util.concurrent.TimeUnit;

/*
*    定位死锁:
*    jstack: jstack 进程号
*
*    jconsole:图形化界面查看
*
*
* */
public class TestDeadLock {
    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (a) {
                System.out.println(Thread.currentThread().getName() + "获得锁a.");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "尝试获取锁b.");
                synchronized (b) {
                    System.out.println(Thread.currentThread().getName() + "正在操作锁b.");
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
           synchronized (b) {
               System.out.println(Thread.currentThread().getName() + "获得锁b.");
               try {
                   TimeUnit.SECONDS.sleep(1);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println(Thread.currentThread().getName() + "尝试获取锁a.");
               synchronized (a) {
                   System.out.println(Thread.currentThread().getName() + "正在操作锁a.");
               }
           }
        }, "t2");

        t1.start();
        t2.start();

    }
}
