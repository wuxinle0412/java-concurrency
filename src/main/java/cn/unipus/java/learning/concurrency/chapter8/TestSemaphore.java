package cn.unipus.java.learning.concurrency.chapter8;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/*
*   Semaphore信号量，用来限制能同时访问共享资源的线程上限。
* */
public class TestSemaphore {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    //获取许可
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try{
                    System.out.println(Thread.currentThread().getName() + " is running...");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " run end...");
                } finally {
                    //释放许可
                    semaphore.release();
                }
            }, "t"+ (i + 1)).start();
        }
    }
}
