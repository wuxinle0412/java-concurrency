package cn.unipus.java.learning.concurrency.chapter4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * @author： wuxinle
 * @date： 2020/11/19 23:24
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */
public class ExerciseSell {
    public static void main(String[] args) throws InterruptedException {
        //模拟多人买票
        TicketWindow window = new TicketWindow(1000);
        List<Integer> list = new Vector<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            Thread thread = new Thread(() -> {
                list.add(window.sell(randomAmount()));
                try {
                    TimeUnit.MILLISECONDS.sleep(randomAmount());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        //统计卖出的票数和剩余的票数
        System.out.println("剩余票数: "+ window.getCount());
        System.out.println("卖出的票数: " + list.stream().mapToInt(i -> i).sum());
    }

    static Random random = new Random();

    public static int randomAmount() {
        return random.nextInt(5) + 1;
    }

}

class TicketWindow {
    private int count;

    public TicketWindow(int count) {
        this.count = count;
    }

    //获取余票数量
    public int getCount() {
        return count;
    }

    //售票
    public int sell(int amount) {
        //加锁保护共享变量
        synchronized (this) {
            if (this.count >= amount) {
                this.count -= amount;
                return amount;
            } else {
                return 0;
            }
        }
    }
}
