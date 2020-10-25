package cn.unipus.java.learning.concurrency.chapter4;

import java.util.concurrent.locks.LockSupport;

/*
*   使用park unpark实现线程交替打印
* */
public class Test3 {
    static Thread t1, t2, t3;
    public static void main(String[] args) {
        ParkUnpark pu = new ParkUnpark();

        t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                pu.print("a", t2);

            }
        });

        t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                pu.print("b", t3);
            }
        });

        t3 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                pu.print("c", t1);
            }
        });

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);
    }
}

class ParkUnpark {
    public void print(String str, Thread next) {
        LockSupport.park();
        System.out.print(str);
        LockSupport.unpark(next);
    }
}
