package cn.unipus.java.learning.concurrency.exercise;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author： wuxinle
 * @date： 2020/11/25 21:20
 * @description： 使用ReentrantLock解决哲学家就餐问题
 * @modifiedBy：
 * @version: 1.0
 */
public class Philosopher {

    private String name;
    private Chopstick left;
    private Chopstick right;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        this.name = name;
        this.left = left;
        this.right = right;
    }

    public String getName() {
        return name;
    }

    public Chopstick getLeft() {
        return left;
    }

    public Chopstick getRight() {
        return right;
    }

    public static void main(String[] args) {
        Chopstick c1 = new Chopstick("c1");
        Chopstick c2 = new Chopstick("c2");
        Chopstick c3 = new Chopstick("c3");
        Chopstick c4 = new Chopstick("c4");
        Chopstick c5 = new Chopstick("c5");

        Philosopher p1 = new Philosopher("苏格拉底", c1, c2);
        Philosopher p2 = new Philosopher("柏拉图", c2, c3);
        Philosopher p3 = new Philosopher("亚里士多德", c3, c4);
        Philosopher p4 = new Philosopher("赫拉克利特", c4, c5);
        Philosopher p5 = new Philosopher("阿基米德", c5, c1);

        Task t1 = new Task(p1);
        Task t2 = new Task(p2);
        Task t3 = new Task(p3);
        Task t4 = new Task(p4);
        Task t5 = new Task(p5);

        new Thread(t1, t1.getName()).start();
        new Thread(t2, t2.getName()).start();
        new Thread(t3, t3.getName()).start();
        new Thread(t4, t4.getName()).start();
        new Thread(t5, t5.getName()).start();
    }
}

class Task implements Runnable {

    private Philosopher philosopher;

    public Task(Philosopher philosopher) {
        this.philosopher = philosopher;
    }

    public String getName() {
        return this.philosopher.getName();
    }

    @Override
    public void run() {
        while (true) {
            //尝试获取左边筷子
            if (philosopher.getLeft().tryLock()) {
                try {
                    if (philosopher.getRight().tryLock()) {
                        try {
                            System.out.println(philosopher.getName() + " 正在吃饭");
                        } finally {
                            philosopher.getRight().unlock();
                        }
                    }
                } finally {
                    philosopher.getLeft().unlock();
                }
            }
        }
    }
}

class Chopstick extends ReentrantLock {
    private String name;

    public Chopstick(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
