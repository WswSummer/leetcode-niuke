package com.wsw.leetcode.Thread;

import java.util.concurrent.Semaphore;

/**
 * @Author wangsongwen
 * @Date 2025/9/4 12:38
 * @Description: 3个线程A, B, C 顺序输出ABC，需要A输出后B输出，B输出后C输出
 * <p>
 * 信号量（Semaphore）是一种更高级的同步工具，用于控制对资源的访问。我们可以使用它来授予或请求执行的“许可”。
 */
public class SequencePrinter3 {

    // 初始许可为0，B、C线程需要等待
    private static final Semaphore semaphoreA = new Semaphore(1); // A先启动
    private static final Semaphore semaphoreB = new Semaphore(0);
    private static final Semaphore semaphoreC = new Semaphore(0);

    static class PrintThread extends Thread {
        private final String name;
        private final Semaphore currentSemaphore;
        private final Semaphore nextSemaphore;

        public PrintThread(String name, Semaphore currentSemaphore, Semaphore nextSemaphore) {
            this.name = name;
            this.currentSemaphore = currentSemaphore;
            this.nextSemaphore = nextSemaphore;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    // 获取当前线程的许可
                    currentSemaphore.acquire();
                    // 打印
                    System.out.print(name);
                    // 释放下一个线程的许可
                    nextSemaphore.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread " + name + " interrupted.");
            }
        }
    }

    public static void main(String[] args) {
        Thread threadA = new PrintThread("A", semaphoreA, semaphoreB);
        Thread threadB = new PrintThread("B", semaphoreB, semaphoreC);
        Thread threadC = new PrintThread("C", semaphoreC, semaphoreA); // C完成后释放A的许可

        threadA.start();
        threadB.start();
        threadC.start();
    }

}




