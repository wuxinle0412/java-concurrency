package cn.unipus.java.learning.concurrency.chapter9.queue;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author： wuxinle
 * @date： 2020/12/21 21:12
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */

/**
 *   公平模式下的模型(TransferQueue):
 *      队尾匹配，对头出队，先进先出，体现公平原则。
 *   非公平模式下的模型(TransferStack):
 *      后进先出原则
 *
 *
 * */
public class SynchronousQueueTest {
    public static void main(String[] args) throws InterruptedException {
        final BlockingQueue<String> queue = new SynchronousQueue<>(true);

        Thread put1 = new Thread(() -> {
            System.out.println("put1 thread start");
            try {
                queue.put("put1");
            } catch (InterruptedException e) {
            }
            System.out.println("put1 thread end");
        });
        put1.start();

        TimeUnit.SECONDS.sleep(1);

        Thread put2 = new Thread(() -> {
            System.out.println("put2 thread start");
            try {
                queue.put("put2");
            } catch (InterruptedException e) {
            }
            System.out.println("put2 thread end");
        });
        put2.start();

        TimeUnit.SECONDS.sleep(1);

        Thread take1 = new Thread(() -> {
            System.out.println("take1 thread start");
            try {
                queue.take();
            } catch (InterruptedException e) {
            }
            System.out.println("take1 thread end");
        });
        take1.start();

        TimeUnit.SECONDS.sleep(1);

        Thread take2 = new Thread(() -> {
            System.out.println("take2 thread start");
            try {
                queue.take();
            } catch (InterruptedException e) {
            }
            System.out.println("take2 thread end");
        });
        take2.start();
    }
}
