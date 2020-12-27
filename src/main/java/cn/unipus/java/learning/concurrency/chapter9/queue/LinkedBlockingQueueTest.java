package cn.unipus.java.learning.concurrency.chapter9.queue;

/**
 * @author： wuxinle
 * @date： 2020/12/21 21:52
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *   Java8中阻塞队列之LinkedBlockingQueue
 * */
public class LinkedBlockingQueueTest {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(1);

        Thread take = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    String message = queue.take();
                    System.out.println(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        take.start();

        Thread put = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    queue.offer("message_" + (i + 1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        put.start();
    }
}
