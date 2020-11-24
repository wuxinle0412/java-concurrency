package cn.unipus.java.learning.concurrency.exercise;

import java.util.LinkedList;

/**
 * @author： wuxinle
 * @date： 2020/11/24 21:41
 * @description： 使用生产者消费者模式实现一个消息队列
 * @modifiedBy：
 * @version: 1.0
 */
public class MessageQueue {

    private LinkedList<Message> list = new LinkedList<>();
    private int capacity;

    public MessageQueue() {
        this(10);
    }

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(1);

        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                try {
                    queue.put(new Message(id, "value" + (id + 1)));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, "t" + (i + 1)).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    Message message = queue.take();
                    System.out.println(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }, "t" + (i + 1)).start();
        }
    }

    //存入消息
    public void put(Message message) throws InterruptedException {
        synchronized (list) {
            while (list.size() == capacity) {
                print("生产者线程等待消费者线程消费消息");
                list.wait();
            }
            print("生产者线程放入消息");
            list.addLast(message);
            list.notifyAll();
        }
    }

    public Message take() throws InterruptedException {
        synchronized (list) {
            while (list.isEmpty()) {
                print("消费者线程等待生产者线程生产消息");
                list.wait();
            }
            print("消费者线程取出消息");
            Message message = list.removeFirst();
            list.notifyAll();
            return message;
        }
    }

    public static void print(String str) {
        System.out.println(Thread.currentThread().getName() + " , time: " + System.currentTimeMillis() + ", " + str);
    }

}

final class Message {
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
