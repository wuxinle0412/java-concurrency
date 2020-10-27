package cn.unipus.java.learning.concurrency.chapter8;

import java.util.concurrent.*;

/*
*    CyclicBarrier 循环栅栏
* */
public class TestCyclicBarrier {
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            System.out.println("task1 task2 finish.");
        });
        ExecutorService service = Executors.newFixedThreadPool(2);

        for (int i =0; i< 3; i++) {
            service.submit(() -> {
                System.out.println( Thread.currentThread().getName() + " running task1");
                try {
                    TimeUnit.SECONDS.sleep(1);
                    cyclicBarrier.await();  //2-1 = 1 阻塞直到为0
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

            service.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " running task2");
                try {
                    TimeUnit.SECONDS.sleep(1);
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }

        TimeUnit.SECONDS.sleep(10);
        service.shutdown();
    }
}
