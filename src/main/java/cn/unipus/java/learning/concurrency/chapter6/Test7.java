package cn.unipus.java.learning.concurrency.chapter6;


import sun.misc.Unsafe;

import java.lang.reflect.Field;

/*
*    使用Unsafe类实现一个原子整型类
* */

public class Test7 {
    public static void main(String[] args) {
        Account.demo(new Account() {

            AtomicData atomicData = new AtomicData(10000);

            @Override
            public Integer getBalance() {
                return atomicData.getData();
            }

            @Override
            public void withdraw(Integer amount) {
                atomicData.decrease(amount);
            }
        });
    }
}

class AtomicData {
    private volatile int data;
    private static final Unsafe unsafe;
    private static final long offset;

    static {
        unsafe = UnsafeAccessor.getUnsafe();
        try {
            offset = unsafe.objectFieldOffset(AtomicData.class.getDeclaredField("data"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public AtomicData(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void decrease(int amount) {
        while (true) {
            int prev = data;
            int next = data - amount;
            if (unsafe.compareAndSwapInt(this, offset, prev, next)) {
                return;
            }
        }
    }
}

class UnsafeAccessor {
    static Unsafe unsafe;
    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }
    static Unsafe getUnsafe() {
        return unsafe;
    }
}
