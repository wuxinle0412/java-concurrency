package cn.unipus.java.learning.concurrency.chapter8;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author： wuxinle
 * @date： 2020/11/11 22:38
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */
public class TestReentrantReadWriteLock {
    public static void main(String[] args) throws InterruptedException {
        //读读并发 读写 写写互斥
        DataContainer dataContainer = new DataContainer();
        new Thread(() -> {
            dataContainer.read();
        }, "t1").start();

        TimeUnit.MILLISECONDS.sleep(100);

        new Thread(() -> {
            dataContainer.write("new data");
        }, "t2").start();
    }
}

class DataContainer {
    private Object data;
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock r = rw.readLock();
    private ReentrantReadWriteLock.WriteLock w = rw.writeLock();

    public DataContainer() {
        this.data = "data";
    }

    public Object read() {
        System.out.println(Thread.currentThread().getName() + " 获取读锁");
        r.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 读取");
            TimeUnit.SECONDS.sleep(1);
            return data;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " 释放读锁");
            r.unlock();
        }
        return null;
    }

    public void write(Object data) {
        System.out.println(Thread.currentThread().getName() + " 获取写锁");
        w.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 写入");
            TimeUnit.SECONDS.sleep(1);
            this.data = data;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " 释放写锁");
            w.unlock();
        }
    }
}