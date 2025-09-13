package com.wsw.leetcode.Thread;

/**
 * @Author wangsongwen
 * @Date 2025/9/4 15:38
 * @Description: 双线程交替打印出 0-100的奇数和偶数
 */
public class AlternatePrint1 {

    private static final Object lock = new Object();
    // true 表示应该打印偶数, false 表示应该打印奇数
    private static boolean printEven = true;
    private static final int MAX = 100;

    static class PrintThread extends Thread {
        private final boolean isEvenThread;

        public PrintThread(String name, boolean isEvenThread) {
            super(name);
            this.isEvenThread = isEvenThread;
        }

        @Override
        public void run() {
            int start = isEvenThread ? 0 : 1;
            for (int i = start; i <= MAX; i += 2) {
                synchronized (lock) {
                    // 等待轮到自己打印
                    while (printEven != isEvenThread) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.err.println(Thread.currentThread().getName() + " interrupted.");
                            return;
                        }
                    }
                    // 打印数字
                    System.out.print(i + " ");
                    // 切换打印标志
                    printEven = !printEven;
                    // 唤醒另一个线程
                    lock.notify();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread threadA = new PrintThread("A", true);
        Thread threadB = new PrintThread("B", false);

        // 启动线程
        threadA.start();
        threadB.start();
    }

}
