package cn.unipus.java.learning.concurrency.chapter5;

import java.util.concurrent.TimeUnit;

public class Test3 {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();

        TimeUnit.SECONDS.sleep(3500);
        System.out.println("主线程停止监控");
        tpt.stop();
    }
}

class TwoPhaseTermination {
    //监控线程
    private Thread monitorThread;
    private boolean stop = false;

    //启动监控线程
    public void start() {
        monitorThread = new Thread(() -> {
            while(true) {
                Thread current = Thread.currentThread();
                if (stop) {
                    System.out.println(Thread.currentThread().getName() + " 料理后事");
                    break;
                }
                try {
                     Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //sleep出现异常后，会清除打断标记
                    //需要重置打断标记
                    current.interrupt();
                }
            }
        }, "monitor");
        monitorThread.start();
    }

    //停止监控线程
    public void stop() {
        //interrupt打断正在运行时的线程，不会抛出异常，打断标记设未真
        //interrupt打断sleep的线程，会抛出异常，并清除打断标记
        stop = true;
        monitorThread.interrupt();
    }
}
