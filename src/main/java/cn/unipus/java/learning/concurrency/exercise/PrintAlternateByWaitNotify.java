package cn.unipus.java.learning.concurrency.exercise;

/**
 * @author： wuxinle
 * @date： 2020/11/25 22:39
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */
public class PrintAlternateByWaitNotify {
    public static void main(String[] args) {
        WaitNotify waitNotify = new WaitNotify(1);

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    waitNotify.print(1, 2, "1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    waitNotify.print(2, 3, "2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    waitNotify.print(3, 1, "3");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t3").start();
    }
}

class WaitNotify {
    //等待标记
    private int flag;

    public WaitNotify(int flag) {
        this.flag = flag;
    }

    public void print(int waitFlag, int nextFlag, String str) throws InterruptedException {
        synchronized (this) {
            while (flag != waitFlag) {
                this.wait();
            }
            System.out.print(str + " ");
            flag = nextFlag;
            this.notifyAll();
        }
    }
}