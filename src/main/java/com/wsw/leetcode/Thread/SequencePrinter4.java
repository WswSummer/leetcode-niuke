package com.wsw.leetcode.Thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author wangsongwen
 * @Date 2025/9/4 12:38
 * @Description: 3个线程A, B, C 顺序输出ABC，需要A输出后B输出，B输出后C输出
 * <p>
 * ReentrantLock + Condition: 更加灵活和高效，可以精确地唤醒指定的线程 (signal() vs notifyAll())，是推荐的现代并发工具。
 */
public class SequencePrinter4 {

    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition conditionA = lock.newCondition();
    private static final Condition conditionB = lock.newCondition();
    private static final Condition conditionC = lock.newCondition();
    private static int state = 0; // 0: A, 1: B, 2: C

    static class PrintThread extends Thread {
        private final String name;
        private final int targetState;
        private final Condition currentCondition;
        private final Condition nextCondition;

        public PrintThread(String name, int targetState, Condition currentCondition, Condition nextCondition) {
            this.name = name;
            this.targetState = targetState;
            this.currentCondition = currentCondition;
            this.nextCondition = nextCondition;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    // 等待直到轮到自己
                    while (state != targetState) {
                        try {
                            currentCondition.await();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.err.println("Thread " + name + " interrupted.");
                            return;
                        }
                    }
                    // 打印
                    System.out.print(name);
                    // 更新状态
                    state = (state + 1) % 3; // 循环状态
                    // 通知下一个线程
                    if (nextCondition != null) {
                        nextCondition.signal();
                    } else {
                        // 如果是C线程，通知A线程
                        conditionA.signal();
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Thread threadA = new PrintThread("A", 0, conditionA, conditionB);
        Thread threadB = new PrintThread("B", 1, conditionB, conditionC);
        Thread threadC = new PrintThread("C", 2, conditionC, null); // C完成后通知A

        // 启动线程
        threadA.start();
        threadB.start();
        threadC.start();
    }

}




