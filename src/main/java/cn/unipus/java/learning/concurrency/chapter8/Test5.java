package cn.unipus.java.learning.concurrency.chapter8;

import java.util.concurrent.atomic.AtomicIntegerArray;

/*
*   使用Semaphore限制连接数
* */
public class Test5 {
    public static void main(String[] args) {

    }
}

class Connection {

}

class Pool {
    //1.连接池大小
    private final int poolSize;

    //2.连接对象数组
    private Connection[] connections;

    //3.连接状态数组 0表示空闲 1表示繁忙
    private AtomicIntegerArray states;

    public Pool(int poolSize) {
        this.poolSize = poolSize;
        this.connections = new Connection[this.poolSize];
        this.states = new AtomicIntegerArray(new int[this.poolSize]);
    }
}
