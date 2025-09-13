package com.wsw.leetcode.Thread;

/**
 * @Author wangsongwen
 * @Date 2025/9/4 15:38
 * @Description: 双线程交替打印出 0-100的奇数和偶数
 */
public class AlternatePrint2 {

    private final Object lock = new Object();
    private volatile int num = 0;
    private final int max;

    AlternatePrint2(int max) {
        this.max = max;
    }

    // 打印偶数
    private void printEven() {
        while (num <= max) {
            synchronized (lock) {
                while (num % 2 != 0) { // 如果是奇数，则等待
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (num > max) break;
                System.out.print(num + " ");
                num++;
                lock.notify(); // 唤醒等待的线程
            }
        }
    }

    // 打印奇数
    private void printOdd() {
        while (num <= max) {
            synchronized (lock) {
                while (num % 2 == 0) { // 如果是偶数，则等待
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (num > max) break;
                System.out.print(num + " ");
                num++;
                lock.notify();
            }
        }
    }

    public static void main(String[] args) {
        AlternatePrint2 AlternatePrint2 = new AlternatePrint2(100);
        Thread threadA = new Thread(AlternatePrint2::printEven);
        Thread threadB = new Thread(AlternatePrint2::printOdd);

        // 启动线程
        threadA.start();
        threadB.start();
    }

}
