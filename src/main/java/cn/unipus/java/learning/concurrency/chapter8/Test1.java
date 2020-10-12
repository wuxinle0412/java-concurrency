package cn.unipus.java.learning.concurrency.chapter8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/*
*    newFixedThreadPool
*    特点
*    1.核心线程数=最大线程数(没有救急线程被创建)，因此也无需超时时间。
*    2.阻塞队列是无界的，可以放任意数量的任务。
*    适用于任务量已知，相对耗时的任务。
* */
public class Test1 {
    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ExecutorService executorService = Executors.newFixedThreadPool(2, new ThreadFactory() {

            AtomicInteger atomicInteger = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "thread-" + atomicInteger.getAndIncrement());
            }
        });
        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " running task t1");
        });

        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " running task t2");
        });

        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " running task t3");
        });
    }
}
