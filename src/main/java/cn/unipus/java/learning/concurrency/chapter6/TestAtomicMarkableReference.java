package cn.unipus.java.learning.concurrency.chapter6;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author： wuxinle
 * @date： 2020/11/30 23:02
 * @description： 使用AtomicMarkableReference测试引用是否被更改
 * @modifiedBy：
 * @version: 1.0
 */
public class TestAtomicMarkableReference {
    public static void main(String[] args) throws InterruptedException {
        GarbageBag bag = new GarbageBag("装满了垃圾");
        // 参数2 mark 可以看作一个标记，表示垃圾袋满了
        AtomicMarkableReference<GarbageBag> ref = new AtomicMarkableReference<>(bag, true);
        print("主线程 start...");
        GarbageBag prev = ref.getReference();
        print(prev.toString());
        new Thread(() -> {
            print("打扫卫生的线程 start...");
            bag.setDesc("空垃圾袋");
            while (!ref.compareAndSet(bag, bag, true, false)) {}
            print(bag.toString());
        }).start();
        Thread.sleep(1000);
        print("主线程想换一只新垃圾袋？");
        boolean success = ref.compareAndSet(prev, new GarbageBag("空垃圾袋"), true, false);
        print("换了么？" + success);
        print(ref.getReference().toString());
    }

    public static void print(String str) {
        System.out.println(Thread.currentThread().getName() + " : " + System.currentTimeMillis() + " : " + str);
    }
}

class GarbageBag {
    String desc;
    public GarbageBag(String desc) {
        this.desc = desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    @Override
    public String toString() {
        return super.toString() + " " + desc;
    }
}
