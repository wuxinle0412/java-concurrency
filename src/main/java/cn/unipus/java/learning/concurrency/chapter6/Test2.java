package cn.unipus.java.learning.concurrency.chapter6;

import java.util.concurrent.atomic.AtomicInteger;

/*
*   测试AtomicInteger常用方法
* */
public class Test2 {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(0);
        //自增并获取，类似于++i 1
        System.out.println(i.incrementAndGet());
        //获取并自增，类似于i++ 1
        System.out.println(i.getAndIncrement());
        //相当于--i 1
        System.out.println(i.decrementAndGet());
        //相当于i-- 1
        System.out.println(i.getAndDecrement());
        // 0
        System.out.println(i.get());

        //加值并获取 5
        System.out.println(i.addAndGet(5));

        //获取并加值 5
        System.out.println(i.getAndAdd(5));

        // 10
        System.out.println(i.get());

        //获取并更新值
        System.out.println(i.updateAndGet(x -> x * 10));
        // 100
        System.out.println(i.get());

        System.out.println(i.accumulateAndGet(10, (p, x) -> p + x));
        //110
        System.out.println(i.get());
    }
}
