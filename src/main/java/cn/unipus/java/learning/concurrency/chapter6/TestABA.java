package cn.unipus.java.learning.concurrency.chapter6;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author： wuxinle
 * @date： 2020/11/30 22:08
 * @description： 测试AtomicReference原子引用的ABA问题
 * @modifiedBy：
 * @version: 1.0
 */
public class TestABA {

    private static AtomicReference<String> ref = new AtomicReference<>("A");

    public static void main(String[] args) throws InterruptedException {
        print("main start");
        String prev = ref.get();
        other();
        TimeUnit.MILLISECONDS.sleep(2000);
        //尝试将A->C
        print("change A->C, " + ref.compareAndSet("A", "C"));
    }

    public static void other() throws InterruptedException {
        new Thread(() -> {
            print("change A->B," + ref.compareAndSet("A", "B"));
        }, "t1").start();

        TimeUnit.MILLISECONDS.sleep(500);

        new Thread(() -> {
            print("change B->A, " + ref.compareAndSet("B", "A"));
        }, "t2").start();
    }

    public static void print(String str) {
        System.out.println(Thread.currentThread().getName() + " : " + System.currentTimeMillis() + " : " + str);
    }
}
