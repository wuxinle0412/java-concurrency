package cn.unipus.java.learning.concurrency.chapter4;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/*
*   活锁: 出现在两个线程互相改变对象的结束条件，最后导致两个线程都无法结束线程的运行
* */
public class Test5 {

    static volatile int count = 10;
    static final Object lock = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (count > 0) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count--;
                System.out.println(Thread.currentThread().getName() + " current count: "+ count);
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            while(count < 20) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                System.out.println(Thread.currentThread().getName() + " current count: " + count);
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
