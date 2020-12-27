package cn.unipus.java.learning.concurrency.chapter9.queue;

/**
 * @author： wuxinle
 * @date： 2020/12/21 22:01
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 *   PriorityBlockingQueue是一个支持优先级的无界阻塞队列，直到系统资源耗尽。
 *   默认情况下元素采用自然顺序升序排列。
 *   也可以自定义类实现compareTo()方法来指定元素排序规则，或者初始化PriorityBlockingQueue时，
 *   指定构造参数Comparator来对元素进行排序。但需要注意的是不能保证同优先级元素的顺序。
 * */
public class PriorityBlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Person> queue = new PriorityBlockingQueue<Person>();
        queue.add(new Person(3,"person3"));
        queue.add(new Person(2,"person2"));
        queue.add(new Person(1,"person1"));
        queue.add(new Person(4,"person4"));

        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
    }

    static class Person implements Comparable<Person> {
        private int id;
        private String name;
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Person(int id, String name) {
            super();
            this.id = id;
            this.name = name;
        }
        public Person() {
        }
        @Override
        public String toString() {
            return this.id + ":" + this.name;
        }
        @Override
        public int compareTo(Person person) {
            return this.id > person.getId() ? 1 : (this.id < person.getId() ? -1 : 0);
        }
    }
}
