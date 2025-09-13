package com.wsw.leetcode.Thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author wangsongwen
 * @Date 2025/9/4 15:04
 * @Description: 并发执行四个任务，要求第一个任务执行完毕后执行第二个和第三个，第二个和第三个都结束后执行第四个任务
 * <p>
 * ExecutorService + CountDownLatch
 */
public class ConcurrentTask1 {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // 用于控制任务1执行完毕
        CountDownLatch latch1_task1_done = new CountDownLatch(1);
        // 用于控制任务2和任务3都执行完毕
        CountDownLatch latch2_task2_and_3_done = new CountDownLatch(2);

        Runnable task1 = () -> {
            try {
                System.out.println("Task 1 started.");
                Thread.sleep(2000);
                System.out.println("Task 1 finished.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Task 1 interrupted.");
            } finally {
                latch1_task1_done.countDown();
            }
        };

        Runnable task2 = () -> {
            try {
                latch1_task1_done.await();
                System.out.println("Task 2 started.");
                Thread.sleep(3000);
                System.out.println("Task 2 finished.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Task 2 interrupted.");
            } finally {
                latch2_task2_and_3_done.countDown();
            }
        };

        Runnable task3 = () -> {
            try {
                latch1_task1_done.await();
                System.out.println("Task 3 started.");
                Thread.sleep(1000);
                System.out.println("Task 3 finished.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Task 3 interrupted.");
            } finally {
                latch2_task2_and_3_done.countDown();
            }
        };

        Runnable task4 = () -> {
            try {
                latch2_task2_and_3_done.await();
                System.out.println("Task 4 started.");
                Thread.sleep(1500);
                System.out.println("Task 4 finished.");
                System.out.println("All tasks completed.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Task 4 interrupted.");
            }
        };

//        executor.submit(task1);
//        executor.submit(task2);
//        executor.submit(task3);
//        executor.submit(task4);

//        executor.submit(task4);
//        executor.submit(task3);
//        executor.submit(task2);
//        executor.submit(task1);

        executor.submit(task3);
        executor.submit(task1);
        executor.submit(task4);
        executor.submit(task2);

        executor.shutdown();
    }

}
