package cn.unipus.java.learning.concurrency.chapter8;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/*
*   Timer类任务执行过程中延迟或者出现异常都会导致后面任务无法执行
* */

public class TestTimer {
    public static void main(String[] args) {
        Timer timer = new Timer();

        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " task1");
                try {
                    TimeUnit.SECONDS.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " task2");
            }
        };

        timer.schedule(task1, 1000);
        timer.schedule(task2, 1000);
    }
}
