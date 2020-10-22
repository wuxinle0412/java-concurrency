package cn.unipus.java.learning.concurrency.chapter6;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/*
*    获取并测试Unsafe类
* */
public class Test6 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        //通过反射的方式，获取Unsafe类的唯一实例
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe)theUnsafe.get(null);

        System.out.println(unsafe);

        Student student = new Student();

        Field id = Student.class.getDeclaredField("id");
        Field name = Student.class.getDeclaredField("name");

        long idOffset = unsafe.objectFieldOffset(id);
        long nameOffset = unsafe.objectFieldOffset(name);

        unsafe.compareAndSwapInt(student, idOffset, 0, 1);
        unsafe.compareAndSwapObject(student, nameOffset, null, "张三");

        System.out.println(student);
    }
}

class Student {
    private volatile int id;
    private volatile String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
