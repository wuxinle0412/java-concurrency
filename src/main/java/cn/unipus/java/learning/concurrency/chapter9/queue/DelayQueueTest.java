package cn.unipus.java.learning.concurrency.chapter9.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author： wuxinle
 * @date： 2020/12/21 21:58
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */
public class DelayQueueTest {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<DelayTask> queue = new DelayQueue<>();
        for (int i = 0; i < 5; i++) {
            queue.put(new DelayTask("work_" + (i + 1), 200));
        }

        new DelayTaskConsumer(queue).start();
    }

    static class DelayTaskConsumer extends Thread {
        private final BlockingQueue<DelayTask> queue;

        public DelayTaskConsumer(BlockingQueue<DelayTask> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                DelayTask task = null;
                while (true) {
                    task = this.queue.take();
                    task.executeTask();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class DelayTask implements Delayed {
        private static long currentTime = System.currentTimeMillis();
        protected final String taskName;
        protected final int timeCost;
        protected final long scheduleTime;

        protected static final AtomicInteger taskCount = new AtomicInteger(0);

        public DelayTask(String taskName, int timeCost) {
            this.taskName = taskName;
            this.timeCost = timeCost;
            taskCount.incrementAndGet();
            currentTime += 1000 + (long) (Math.random() * 1000);
            this.scheduleTime = currentTime;
        }

        public void executeTask() {
            long startTime = System.currentTimeMillis();
            System.out.println("Task " + taskName + ": schedule_start_time=" + scheduleTime + ",real start time="
                    + startTime + ",delay=" + (startTime - scheduleTime));
            try {
                Thread.sleep(timeCost);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long expirationTime = scheduleTime - System.currentTimeMillis();
            return unit.convert(expirationTime, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.scheduleTime - ((DelayTask)o).scheduleTime);
        }
    }
}
