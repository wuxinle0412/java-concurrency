package cn.unipus.java.learning.concurrency.exercise;

/**
 * @author wuxinle
 * @version 1.0
 * @date 2020/11/24 17:09
 * Description: 使用wait notify线程按固定顺序执行
 */
public class PrintOrderByWaitNotify {

    static final Object lock = new Object();
    static boolean isRun = false;
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (!isRun) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " print 1");
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " print 2");
                isRun = true;
                lock.notify();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
