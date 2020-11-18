package cn.unipus.java.learning.concurrency.exercise;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wuxinle
 * @version 1.0
 * @date 2020/11/18 9:41
 * Description: 手动实现一个阻塞队列
 */
public class BlockingQueue<T> {
    private int size;
    private Object[] queue;

    private ReentrantLock lock = new ReentrantLock();
    private Condition full = lock.newCondition();
    private Condition empty = lock.newCondition();

    private int index;
    private int removeIndex;
    private int currLen;

    public BlockingQueue() {
        this(10);
    }

    public BlockingQueue(int size) {
        this.index = 0;
        this.removeIndex = 0;
        this.currLen = 0;
        this.size = size;
        queue = new Object[size];
    }

    public void push(T element) throws InterruptedException {
        lock.lock();
        try {
            while (currLen == size) {
                System.out.println("队列满，push线程阻塞");
                full.await();
            }
            System.out.println("入队...");
            queue[index] = element;
            if (++index == size) {
                index = 0;
            }
            currLen++;
            empty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T pop() throws InterruptedException {
        lock.lock();
        try {
            while (currLen == 0) {
                System.out.println("队列空，pop线程阻塞");
                empty.await();
            }
            System.out.println("出队...");
            Object obj = queue[removeIndex];
            if (++removeIndex == size) {
                removeIndex = 0;
            }
            currLen--;
            full.signal();
            return (T) obj;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> blockQueue = new BlockingQueue<Integer>(1);

        new Thread(() -> {
            try {
                blockQueue.push(1);
                blockQueue.push(2);
                blockQueue.push(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "t1").start();

        new Thread(() -> {
            try {
                blockQueue.pop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
