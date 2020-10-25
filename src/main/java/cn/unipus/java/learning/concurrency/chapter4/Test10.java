package cn.unipus.java.learning.concurrency.chapter4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/*
*   park 之后对应线程状态 wait
*   unpark可以在线程暂停前 暂停后调用
* */
public class Test10 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " park");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + " resume");
        }, "t1");

        t1.start();

        TimeUnit.SECONDS.sleep(5);
        System.out.println("main unpark t1");
        LockSupport.unpark(t1);
    }
}
