package cn.unipus.java.learning.concurrency.chapter8;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/*
*    测试StampedLock
*    new Thread(() -> {
            try {
                dataContainerStamped.read(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        TimeUnit.MILLISECONDS.sleep(500);

        new Thread(() -> {
            try {
                dataContainerStamped.read(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
        两个读锁之间乐观读，不会加读锁，读读之间是并发的

*       读写  读获取stamp 写修改了stamp 验证stamp时失败 升级读锁。
* */
public class TestStampedLock {
    public static void main(String[] args) throws InterruptedException {
        DataContainerStamped dataContainerStamped = new DataContainerStamped(1);
        new Thread(() -> {
            try {
                dataContainerStamped.read(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        TimeUnit.MILLISECONDS.sleep(500);

        new Thread(() -> {
            try {
                dataContainerStamped.write(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}

class DataContainerStamped {
    private int data;
    private final StampedLock lock = new StampedLock();

    public DataContainerStamped(int data) {
        this.data = data;
    }

    public int read(int readTime) throws InterruptedException {
        long stamp = lock.tryOptimisticRead();
        TimeUnit.SECONDS.sleep(readTime);
        if (lock.validate(stamp)) {
            System.out.println("stamp: " + stamp + ", data: " + data);
            return data;
        }

        System.out.println("update to read lock..." + stamp);
        try {
            stamp = lock.readLock();
            System.out.println("read lock, " + stamp);
            TimeUnit.SECONDS.sleep(readTime);
            System.out.println("read finish, data: " + data + ", stamp: " + stamp);
            return data;
        } finally {
            System.out.println("read unlock, stamp: " + stamp);
            lock.unlockRead(stamp);
        }
    }

    public void write(int newData) throws InterruptedException {
        long stamp = lock.writeLock();
        System.out.println("write lock, stamp: " + stamp);
        try {
            TimeUnit.SECONDS.sleep(2);
            this.data = newData;
        } finally {
            System.out.println("write unlock, stamp: " + stamp);
            lock.unlockWrite(stamp);
        }
    }
}
