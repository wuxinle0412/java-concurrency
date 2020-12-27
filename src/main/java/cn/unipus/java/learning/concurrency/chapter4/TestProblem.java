package cn.unipus.java.learning.concurrency.chapter4;

/**
 * @author： wuxinle
 * @date： 2020/11/17 22:10
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */
public class TestProblem {

    static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                counter++;
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                counter--;
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("current counter:" + counter);
    }
}
