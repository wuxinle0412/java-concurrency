package cn.unipus.java.learning.concurrency.chapter4;

/**
 * @author： wuxinle
 * @date： 2020/11/23 20:08
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */
public class GuardedSuspension {
    public static void main(String[] args) {

    }
}

class GuardedObject<T> {
    private T data;

    public T get(long timeout) throws InterruptedException {
        synchronized (this) {
            //记录开始时间
            long begin = System.currentTimeMillis();
            long passedTime = 0L;
            while (data == null) {
                long waitTime = timeout - passedTime;
                if (waitTime <= 0) {
                    break;
                }
                this.wait(waitTime);  //不能等待固定的timeout，防止虚假唤醒。
                //每次唤醒完后，计算总共wait的时间。
                passedTime = System.currentTimeMillis() - begin;
            }
        }
        return data;
    }

    public void compute(T data) {
        synchronized (this) {
            this.data = data;
            this.notifyAll();
        }
    }
}
