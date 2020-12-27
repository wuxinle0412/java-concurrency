package cn.unipus.java.learning.concurrency.chapter4;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * @author： wuxinle
 * @date： 2020/12/3 22:12
 * @description： 测试偏向锁
 * @modifiedBy：
 * @version: 1.0
 */
/**
 *   -XX:BiasedLockingStartupDelay=0 参数设置偏向锁立即生效，默认偏向锁在JVM启动后4秒生效。
 * */
public class TestBiased {
    public static void main(String[] args) throws InterruptedException {
        Dog dog = new Dog();
        ClassLayout layout = ClassLayout.parseInstance(dog);
        new Thread(() -> {
            print(" synchronized 之前");
            System.out.println(layout.toPrintable());
            synchronized (dog) {
                print("synchronized...");
                System.out.println(layout.toPrintable());
            }
            print("synchronized后");
            System.out.println(layout.toPrintable());
        }, "t1").start();

    }

    public static void print(String str) {
        System.out.println(Thread.currentThread().getName() + " : " + System.currentTimeMillis() + " : " + str);
    }

    static class Dog {

    }
}

