package cn.unipus.java.learning.concurrency.exercise;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wuxinle
 * @version 1.0
 * @date 2020/11/18 12:20
 */
public class LoopBlockingQueue<T> {
    private Object[] queue;
    private int maxSize;
    private int front;
    private int rear;

    private ReentrantLock lock = new ReentrantLock();
    private Condition full = lock.newCondition();
    private Condition empty = lock.newCondition();

    public LoopBlockingQueue(int maxSize) {
        this.front = 0;
        this.rear = 0;
        this.maxSize = maxSize;
        queue = new Object[this.maxSize];
    }

    public void push(T element) throws InterruptedException {
        lock.lock();
        try {
            while ((rear + 1) % maxSize == front) {
                System.out.println("queue is full");
                full.await();
            }
            queue[rear] = element;
            rear = (rear + 1) % maxSize;
            empty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T pop() throws InterruptedException {
        lock.lock();
        try {
            while (rear == front) {
                System.out.println("queue is empty");
                empty.await();
            }
            Object obj = queue[front];
            queue[front] = null;
            front = (front + 1) % maxSize;
            full.signal();
            return (T) obj;
        } finally {
            lock.unlock();
        }
    }
}
