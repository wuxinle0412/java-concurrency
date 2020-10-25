package cn.unipus.java.learning.concurrency.chapter4;

import java.util.concurrent.locks.LockSupport;

public class Test9 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            System.out.print("t1");
        });

        Thread t2 = new Thread(() -> {
            System.out.print("t2");
            LockSupport.unpark(t1);
        });

        t1.start();
        t2.start();
    }
}
