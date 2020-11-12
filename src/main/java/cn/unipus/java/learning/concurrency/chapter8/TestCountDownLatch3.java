package cn.unipus.java.learning.concurrency.chapter8;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author： wuxinle
 * @date： 2020/11/10 23:22
 * @description： 使用CountDownLatch模拟游戏过程玩家加载过程。
 * @modifiedBy：
 * @version: 1.0
 */
public class TestCountDownLatch3 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10);
        Random random = new Random();
        String[] all = new String[10];
        for (int j = 0; j < 10; j++) {
            int position = j;
            service.submit(() -> {
                for (int i = 1; i <= 100; i++) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    all[position] = i + "%";
                    System.out.print("\r" + Arrays.toString(all));
                }
                latch.countDown();
            });
        }

        latch.await();
        System.out.println("\n游戏开始");
        service.shutdown();
    }
}
