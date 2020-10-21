package cn.unipus.java.learning.concurrency.chapter6;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/*
*    测试原子引用类的ABA问题
* */
public class Test4 {

    static AtomicReference<String> ref = new AtomicReference<>("A");

    public static void main(String[] args) throws InterruptedException {
        String prev = ref.get();
        System.out.println("当前值: " + prev);
        other();
        TimeUnit.SECONDS.sleep(3);
        ref.compareAndSet(prev, "C");
        System.out.println("修改后的值: " + ref.get());
    }

    private static void other() throws InterruptedException {
        new Thread(() -> {
            ref.compareAndSet(ref.get(), "B");
            System.out.println(Thread.currentThread().getName() + " : " + ref.get());
        }, "t1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            ref.compareAndSet(ref.get(), "A");
            System.out.println(Thread.currentThread().getName() + " : " + ref.get());
        }, "t2").start();
    }
}
