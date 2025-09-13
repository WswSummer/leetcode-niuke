package com.wsw.leetcode;

/**
 * @Author wangsongwen
 * @Date 2025/9/5 15:55
 * @Description:
 */
public class ThreadLocalTest {

    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    static void print(String str) {
        //打印当前线程中本地内存中本地变量的值
        System.out.println(str + ": " + threadLocal.get());
        //清除本地内存中的本地变量
        threadLocal.remove();
    }

    public static void main(String[] args) {

        new Thread(() -> {
            String name = Thread.currentThread().getName();
            threadLocal.set("A");
            print(name);
            System.out.println(name + "-after remove: " + threadLocal.get());
        }, "Thread-1").start();

        new Thread(() -> {
            String name = Thread.currentThread().getName();
            threadLocal.set("B");
            print(name);
            System.out.println(name + "-after remove: " + threadLocal.get());
        }, "Thread-2").start();

    }

}
