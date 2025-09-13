package com.wsw.leetcode.Thread;

/**
 * @Author wangsongwen
 * @Date 2025/9/4 12:38
 * @Description: 3个线程A, B, C 顺序输出ABC，需要A输出后B输出，B输出后C输出
 * <p>
 * synchronized + wait/notify：Java中最基础也最灵活的线程协作方式。通过共享一个锁对象，线程在满足条件时等待，在完成任务后通知所有等待的线程。
 */
public class SequencePrinter2 {

    private static final Object lock = new Object();
    // 使用状态变量来控制哪个线程应该打印
    private static int state = 0; // 0: A, 1: B, 2: C

    static class PrintThread extends Thread {
        private final String name;
        private final int targetState;
        private final int nextState;

        public PrintThread(String name, int targetState, int nextState) {
            this.name = name;
            this.targetState = targetState;
            this.nextState = nextState;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) { // 打印10次ABC
                synchronized (lock) {
                    // 如果不是当前线程的轮次，则等待
                    while (state != targetState) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.err.println("Thread " + name + " interrupted.");
                            return;
                        }
                    }
                    // 执行打印
                    System.out.print(name);
                    // 更新状态到下一个线程
                    state = nextState;
                    // 唤醒所有等待的线程
                    lock.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread threadA = new PrintThread("A", 0, 1);
        Thread threadB = new PrintThread("B", 1, 2);
        Thread threadC = new PrintThread("C", 2, 0); // C打印完后，状态回到0，即A的轮次

        // 启动线程
        threadA.start();
        threadB.start();
        threadC.start();
    }

}




