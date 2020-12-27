package cn.unipus.java.learning.concurrency.chapter9.queue;

/**
 * @author： wuxinle
 * @date： 2020/12/21 21:45
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *   Java8种阻塞队列之ArrayBlockingQueue
 *   先进先出原则，使用数组实现
 * */
public class ArrayBlockingQueueTest {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1, true);

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
                   queue.put("message_" + (i + 1));
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

           }
        });
        put.start();
    }
}
