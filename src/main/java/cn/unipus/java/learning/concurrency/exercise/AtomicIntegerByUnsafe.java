package cn.unipus.java.learning.concurrency.exercise;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author： wuxinle
 * @date： 2020/11/24 23:15
 * @description： 使用Unsafe类实现原子整型类
 * @modifiedBy：
 * @version: 1.0
 */
public class AtomicIntegerByUnsafe {
    private static final Unsafe unsafe;
    private volatile int value;
    private static final long valueOffset;

    static {
        unsafe = UnsafeAccessor.getUnsafe();
        try {
            valueOffset = unsafe.objectFieldOffset(AtomicIntegerByUnsafe.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public int getValue() {
        return value;
    }

    public void decrement(int amount) {
        while (true) {
            int prev = getValue();
            int next = prev - amount;
            if (unsafe.compareAndSwapInt(this, valueOffset, prev, next)) {
                break;
            }
        }
    }
}

class UnsafeAccessor {
    private static final Unsafe unsafe;

    static {
        Field field = null;
        try {
            field = Unsafe.class.getDeclaredField("theUnsafe");
            unsafe = (Unsafe)field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        field.setAccessible(true);
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }
}
