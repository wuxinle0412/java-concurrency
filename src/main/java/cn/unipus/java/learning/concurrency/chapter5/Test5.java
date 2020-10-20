package cn.unipus.java.learning.concurrency.chapter5;

/*
*   单例模式的double-checked locking问题
*
* final class cn.unipus.java.learning.concurrency.chapter5.Singleton {
  public static cn.unipus.java.learning.concurrency.chapter5.Singleton getInstance();
    Code:
       0: getstatic     #2                  // Field INSTANCE:Lcn/unipus/java/learning/concurrency/chapter5/Singleton;
       3: ifnonnull     37
       6: ldc           #3                  // class cn/unipus/java/learning/concurrency/chapter5/Singleton
       8: dup
       9: astore_0
      10: monitorenter
      11: getstatic     #2                  // Field INSTANCE:Lcn/unipus/java/learning/concurrency/chapter5/Singleton;
      14: ifnonnull     27
      17: new           #3                  // class cn/unipus/java/learning/concurrency/chapter5/Singleton
      20: dup
      21: invokespecial #4                  // Method "<init>":()V
      24: putstatic     #2                  // Field INSTANCE:Lcn/unipus/java/learning/concurrency/chapter5/Singleton;
      *                                     //加入对INSTANCE变量的写屏障保证创建对象过程不会发生指令重排序
      27: aload_0
      28: monitorexit
      29: goto          37
      32: astore_1
      33: aload_0
      34: monitorexit
      35: aload_1
      36: athrow
      37: getstatic     #2                  // Field INSTANCE:Lcn/unipus/java/learning/concurrency/chapter5/Singleton;
      40: areturn
    Exception table:
       from    to  target type
          11    29    32   any
          32    35    32   any

  static {};
    Code:
       0: aconst_null
       1: putstatic     #2                  // Field INSTANCE:Lcn/unipus/java/learning/concurrency/chapter5/Singleton;
       4: return
}

* */
public class Test5 {
    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();

        System.out.println(s1 == s2);
    }
}

final class Singleton {
    private Singleton() {

    }

    private static volatile Singleton INSTANCE = null;

    public static Singleton getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton.class) {
                //再加一次判断是防止阻塞在monitor中的线程再次创建实例。
                if (INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }

        return INSTANCE;
    }
}
