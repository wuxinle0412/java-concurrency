package cn.unipus.java.learning.concurrency.chapter5;

import java.util.concurrent.TimeUnit;

/*
*   volatile和synchronized保证可见性
* */

public class Test2 {

    volatile static boolean run = true;

    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        test2();

        TimeUnit.SECONDS.sleep(1);

        run = false;
    }

    public static void test1() {
        Thread t = new Thread(() -> {
            while (run) {

            }
        });
        t.start();
    }

    public static void test2() {
        Thread t = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (run) {
                        break;
                    }
                }
            }
        });
        t.start();
    }
}
