package cn.unipus.java.learning.concurrency.chapter9.copyonwrite;

/**
 * @author： wuxinle
 * @date： 2020/12/22 22:45
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *   CopyOnWriteArraySet具有以下特征:
 *   1.Set大小通常保持很小，只读操作远多于写操作，需要在遍历期间防止线程间的冲突。
 *   2.它是线程安全的。
 *   3.因为通常需要复制整个基础数组，所以可变操作(add、set和remove)的开销很大。
 *   4.迭代器支持hasNext()、next()等不可变操作，但是不支持remove()等操作。
 *   5.使用迭代器进行遍历的速度很快，并且不会与其他线程发生冲突。在构造迭代器时，迭代器依赖于不变的数组快照。
 * */
public class CopyOnWriteSetTest {
    public static void main(String[] args) {
        Set<String> set = new CopyOnWriteArraySet<>();
        set.add("data1");
        set.add("data2");
        Iterator<String> iterator = set.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
