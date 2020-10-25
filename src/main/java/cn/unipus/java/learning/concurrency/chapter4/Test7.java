package cn.unipus.java.learning.concurrency.chapter4;

/*
*   固定顺序运行线程
* */
public class Test7 {

    static Object lock = new Object();
    //表示t2线程是否运行过了
    static boolean isOk = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (lock) {
                    while (!isOk) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("t1");
                    isOk = false;
                    lock.notify();
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (lock) {
                    while (isOk) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("t2");
                    isOk = true;
                    lock.notify();
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
