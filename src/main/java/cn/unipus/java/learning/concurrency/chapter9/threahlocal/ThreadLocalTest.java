package cn.unipus.java.learning.concurrency.chapter9.threahlocal;

/**
 * @author： wuxinle
 * @date： 2020/12/22 22:55
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */

import java.util.concurrent.TimeUnit;

/**
 *   ThreadLocal，访问这个变量的每一个线程都会有这个变量的一个副本，在实际多线程操作的时候，操作的是
 *   自己本地内存中的变量，从而规避了线程安全问题。
 * */
public class ThreadLocalTest {
    public static void main(String[] args) {
        ThreadLocal<String> local = new ThreadLocal<>();

        for (int i = 0; i < 5; i++) {
            int k = i;
            new Thread(() -> {
                local.set("value_" + (k + 1) );
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + " : " + local.get());
            }).start();
        }
    }
}
