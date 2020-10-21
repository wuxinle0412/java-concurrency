package cn.unipus.java.learning.concurrency.chapter6;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
    CAS锁
* */
public class Test5 {
    public static void main(String[] args) {
        LockCas lock = new LockCas();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            lock.lock();
            try {
                System.out.println("t1 sleep");
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }, "t1").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            System.out.println("t2 lock");
            lock.lock();
            System.out.println("t2 use lock");
            lock.unlock();
        }, "t2").start();
    }
}

class LockCas {
    private AtomicInteger state = new AtomicInteger(0);

    public void lock() {
        while (true) {
            if (state.compareAndSet(0, 1)) {
                break;
            }
        }
    }

    public void unlock() {
        state.set(0);
    }
}
