package cn.unipus.java.learning.concurrency.chapter5;

import java.util.concurrent.TimeUnit;

/*
*     可见性问题，测试线程是否会停下来。
* */
public class Test1 {

    static boolean run = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (run) {

            }
        });

        t.start();

        TimeUnit.SECONDS.sleep(1);

        run = false;
    }
}
