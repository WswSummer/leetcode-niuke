package com.wsw.leetcode.Thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author wangsongwen
 * @Date 2025/9/4 15:04
 * @Description: 并发执行四个任务，要求第一个任务执行完毕后执行第二个和第三个，第二个和第三个都结束后执行第四个任务
 * <p>
 * CompletableFuture
 */
public class ConcurrentTask2 {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // 任务1
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            System.out.println("任务1开始执行...");
            try {
                Thread.sleep(2000); // 模拟任务1执行时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务1执行完毕...");
        }, executor);

        // 任务2: 等待任务1完成后执行
        CompletableFuture<Void> task2 = task1.thenRunAsync(() -> {
            System.out.println("任务2开始执行...");
            try {
                Thread.sleep(3000); // 模拟任务2执行时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务2执行完毕...");
        }, executor);

        // 任务3: 等待任务1完成后执行
        CompletableFuture<Void> task3 = task1.thenRunAsync(() -> {
            System.out.println("任务3开始执行...");
            try {
                Thread.sleep(2000); // 模拟任务3执行时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务3执行完毕...");
        }, executor);

        // 任务4: 等待任务2和3都完成后执行
        CompletableFuture<Void> task4 = CompletableFuture.allOf(task2, task3).thenRunAsync(() -> {
            System.out.println("任务4开始执行...");
            try {
                Thread.sleep(1000); // 模拟任务4执行时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务4执行完毕...");
        }, executor);

        // 等待所有任务完成
        task4.join();

        executor.shutdown();
    }

}
