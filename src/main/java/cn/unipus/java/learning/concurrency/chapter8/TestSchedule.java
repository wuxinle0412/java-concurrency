package cn.unipus.java.learning.concurrency.chapter8;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
*  每周四 18:00定时执行任务
* */
public class TestSchedule {
    public static void main(String[] args) {

        LocalDateTime now = LocalDateTime.now();
        //获取周四的时间
        LocalDateTime time = now.withHour(18).withMinute(0).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);

        //当前时间大于周四
        if (now.compareTo(time) > 0) {
            time.plusWeeks(1);
        }
        long initialDelay = Duration.between(now, time).toMillis();

        //每周间隔
        long period = 1000 * 60 * 60 * 24 * 7;

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("running...");
        }, initialDelay, period, TimeUnit.MILLISECONDS);
    }
}
