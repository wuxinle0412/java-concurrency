package cn.unipus.java.learning.concurrency.chapter1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*
*    创建和运行线程
*    1 直接使用Thread
*    2 创建Runnable对象配合Thread
*    3.FutureTask配合Thread
*
*    Thread和Runnable的关系
*    class Thread implements Runnable {
*        ...
         private Runnable target;
     Thread有一个Runnable 对象, 在Thread的run中调用Runnable的run方法
     @Override
     public void run() {
        if (target != null) {
            target.run();
        }
     }

     两种方法的比较
     * 1.直接使用Thread的方法将线程与任务耦合在了一起，Runnable与Thread配合的方式将线程与任务分离。
     * 2.用Runnable更容易与线程池等高级API配合。
     * 3.用Runnable让任务类脱离Thread继承体系，更灵活。
* */
public class Test1 {
    public static void main(String[] args) {
        test1();
        test2();
        test3();
        System.out.println(Thread.currentThread().getName() + " running");
    }

    public static void test1() {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " running");
            }
        };
        t1.start();
    }

    public static void test2() {
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + " running");
        };

        Thread t2 = new Thread(runnable,"t2");
        t2.start();
    }

    public static void test3() {
        Callable<Integer> callable = () -> {
            System.out.println(Thread.currentThread().getName() + " running");
            return 10;
        };

        //FutureTask继承了Runnable接口 提供了Callable成员变量
        //提供了两个构造器
        //1 直接传入Callable
        //2 将传入的Runnable接口用适配成Callable接口
        FutureTask<Integer> task = new FutureTask<>(callable);

        Thread t3 = new Thread(task, "t3");
        t3.start();
        try {
            Integer integer = task.get();
            System.out.println(integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}


