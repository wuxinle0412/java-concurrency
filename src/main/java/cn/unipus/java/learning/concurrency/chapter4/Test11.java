package cn.unipus.java.learning.concurrency.chapter4;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test11 {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(4);
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.execute(() -> {
            System.out.println("运行第一各任务");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            System.out.println("count: " + latch.getCount());
        });

        executorService.execute(() -> {
            System.out.println("运行第二个任务");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            System.out.println("count: " + latch.getCount());
        });

        executorService.execute(() -> {
            System.out.println("运行第三个任务");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            System.out.println("count: " + latch.getCount());
        });

        executorService.execute(() -> {
            System.out.println("等待任务结束");
            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务结束");
        });
    }
}
