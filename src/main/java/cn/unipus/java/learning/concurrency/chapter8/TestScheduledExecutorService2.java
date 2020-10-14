package cn.unipus.java.learning.concurrency.chapter8;

import java.util.concurrent.*;

/*
*   处理线程池异常
*   1. try catch捕捉异常
*   2.使用Callable接口，配合Future接口抛出异常
* */
public class TestScheduledExecutorService2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        Future<String> f = scheduledExecutorService.schedule(() -> {
            int i = 1 / 0;
            return "ok";
        }, 0, TimeUnit.SECONDS);

        System.out.println(f.get());

        scheduledExecutorService.shutdown();
    }
}
