package cn.unipus.java.learning.concurrency.chapter6;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author： wuxinle
 * @date： 2020/11/30 22:54
 * @description： 使用AtomicStampedReference解决ABA问题
 * @modifiedBy：
 * @version: 1.0
 */
public class SolutionABA {

    private static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);

    public static void main(String[] args) throws InterruptedException {
        print("main start");

        String prev = ref.getReference();
        int stamp = ref.getStamp();
        other();
        TimeUnit.SECONDS.sleep(2);
        print("change A->C, " + ref.compareAndSet(prev, "C", stamp, stamp + 1));

    }

    public static void other() throws InterruptedException {
        new Thread(() -> {
            print("change A->B, " + ref.compareAndSet(ref.getReference(), "B", ref.getStamp(), ref.getStamp() + 1));
        }, "t1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            print("change A->B, " + ref.compareAndSet(ref.getReference(), "A", ref.getStamp(), ref.getStamp() + 1));
        }, "t2").start();
    }

    public static void print(String str) {
        System.out.println(Thread.currentThread().getName() + " : " + System.currentTimeMillis() + " : " + str);
    }
}
