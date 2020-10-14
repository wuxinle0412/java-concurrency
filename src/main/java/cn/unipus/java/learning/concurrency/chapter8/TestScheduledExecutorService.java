package cn.unipus.java.learning.concurrency.chapter8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestScheduledExecutorService {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

        scheduledExecutorService.schedule(() -> {
            System.out.println(Thread.currentThread().getName() + " task1");
            int i = 1 / 0;
        }, 1, TimeUnit.SECONDS);

        scheduledExecutorService.schedule(() -> {
            System.out.println(Thread.currentThread().getName() + " task2");
        }, 1, TimeUnit.SECONDS);

        //以固定时间间隔执行任务，如果任务运行超时并且超过了间隔时间，会在任务运行完后重新提交下一次运行
        scheduledExecutorService.scheduleAtFixedRate(() ->{
            System.out.println(Thread.currentThread().getName() + " running task");
        }, 1, 1, TimeUnit.SECONDS);

        //两个任务运行之间的间隔时间固定上一个任务完后，间隔delay再提交下一次任务。
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            System.out.println(Thread.currentThread().getName() + " running task");
        }, 1, 1, TimeUnit.SECONDS);

    }
}
