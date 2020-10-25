package cn.unipus.java.learning.concurrency.chapter4;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test12 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        ExecutorService service = Executors.newFixedThreadPool(10);
        String[] all = new String[10];
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            final int position = i;
            service.submit(() -> {
                for (int j = 0; j <= 100; j++) {
                    all[position] = j + "%";
                    try {
                        TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("\r" + Arrays.toString(all));
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("\n" + "玩家数据加载完毕，游戏开始");

        service.shutdown();
    }
}
