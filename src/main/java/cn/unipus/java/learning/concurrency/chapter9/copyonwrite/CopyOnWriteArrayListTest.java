package cn.unipus.java.learning.concurrency.chapter9.copyonwrite;

/**
 * @author： wuxinle
 * @date： 2020/12/20 21:43
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *   CopyOnWriteArrayList
 *   写时复制容器，用于读多写少的并发场景。比如白名单、黑名单、商品类目的访问和更新场景。
 * */
public class CopyOnWriteArrayListTest {
    public static void main(String[] args) throws InterruptedException {
        List<String> list = new CopyOnWriteArrayList<>();

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i < 100; i++) {
                        list.add(UUID.randomUUID().toString());
                    }
                }
            };
            threads[i] = new Thread(task);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("并发修改完成，list size: " + list.size());
    }
}
