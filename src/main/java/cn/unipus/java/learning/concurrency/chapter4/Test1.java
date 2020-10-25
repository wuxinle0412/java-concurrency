package cn.unipus.java.learning.concurrency.chapter4;

/*
*   使用wait notify实现线程交替输出
* */
public class Test1 {
    public static void main(String[] args) {
        SyncWaitNotify syncWaitNotify = new SyncWaitNotify(1);

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    syncWaitNotify.print(1, 2, "a");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    syncWaitNotify.print(2, 3, "b");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    syncWaitNotify.print(3, 1, "c");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

class SyncWaitNotify {
    private int flag;

    public SyncWaitNotify(int flag) {
        this.flag = flag;
    }

    public void print(int waitFlag, int nextFlag, String str) throws InterruptedException {
        synchronized (this) {
            while (this.flag != waitFlag) {
                this.wait();
            }
            System.out.print(str + " ");
            flag = nextFlag;
            this.notifyAll();
        }
    }
}
