package cn.unipus.java.learning.concurrency.chapter8;

/*
*   public static ExecutorService newCachedThreadPool() {
       return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                     60L, TimeUnit.SECONDS,
                                     new SynchronousQueue<Runnable>());
       }


*   特点
*   核心线程数是0，最大线程数是Integer.MAX_VALUE，救急线程的空闲生存时间是60s，意味着
*     全部都是救急线程
*     救急线程可以无限创建
*   队列采用了SynchronousQueue，实现特点是，它没有容量，没有线程来取取是放不进去的(一手交钱，一手交货)
*
*   整个线程池表现为线程数会根据任务量不断增长，没有上限，当任务执行完毕，空闲一分钟后释放线程。
*   适合任务数比较密集，但每个任务执行时间较短的情况。
* */
public class Test2 {
}
