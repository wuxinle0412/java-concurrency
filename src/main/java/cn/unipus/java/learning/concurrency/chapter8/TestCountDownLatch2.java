package cn.unipus.java.learning.concurrency.chapter8;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author： wuxinle
 * @date： 2020/11/10 23:15
 * @description： CountDownLatch配合线程池使用
 * @modifiedBy：
 * @version: 1.0
 */
public class TestCountDownLatch2 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(1);
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            service.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " begin...");
                latch.countDown();
                System.out.println(Thread.currentThread().getName() + " end...");
            });
        }

        latch.await();
        System.out.println("任务执行结束");
        service.shutdown();
    }
}
