package cn.unipus.java.learning.concurrency.exercise;

import java.util.concurrent.locks.LockSupport;

/**
 * @author： wuxinle
 * @date： 2020/11/25 23:03
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */
public class PrintAlternateByPark {

    static Thread t1, t2, t3;

    public static void main(String[] args) {
        ParkUnpark p1 = new ParkUnpark();

        t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                p1.print("a" , t2);
            }
        }, "t1");

        t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                p1.print("b" , t3);
            }
        }, "t1");

        t3 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                p1.print("c" , t1);
            }
        }, "t1");

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);
    }
}

class ParkUnpark {
    public void print(String str, Thread next) {
        LockSupport.park();
        System.out.print(str + " ");
        LockSupport.unpark(next);
    }
}