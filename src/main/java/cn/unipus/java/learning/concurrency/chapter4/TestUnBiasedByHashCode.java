package cn.unipus.java.learning.concurrency.chapter4;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author： wuxinle
 * @date： 2020/12/3 22:22
 * @description： 测试调用对象hashcode方法会撤销偏向锁
 * @modifiedBy：
 * @version: 1.0
 */
public class TestUnBiasedByHashCode {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.hashCode();
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
