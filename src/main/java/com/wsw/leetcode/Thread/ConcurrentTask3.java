package com.wsw.leetcode.Thread;

import java.util.concurrent.*;

/**
 * @Author wangsongwen
 * @Date 2025/9/4 15:04
 * @Description: 并发执行四个任务，要求第一个任务执行完毕后执行第二个和第三个，第二个和第三个都结束后执行第四个任务
 * <p>
 * ExecutorService + Future
 */
public class ConcurrentTask3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // 定义任务1
        Callable<String> callableTask1 = () -> {
            System.out.println("任务1: 开始执行。");
            Thread.sleep(500);
            System.out.println("任务1: 执行完毕。");
            return "Task1_Done";
        };

        // 提交任务1，并获取其Future
        Future<String> future1 = executor.submit(callableTask1);

        // 等待任务1完成
        future1.get(); // 阻塞，直到任务1完成

        // 定义任务2和3，可以并发执行
        Callable<String> callableTask2 = () -> {
            System.out.println("任务2: 开始执行。");
            Thread.sleep(3000);
            System.out.println("任务2: 执行完毕。");
            return "Task2_Done";
        };
        Callable<String> callableTask3 = () -> {
            System.out.println("任务3: 开始执行。");
            Thread.sleep(1500);
            System.out.println("任务3: 执行完毕。");
            return "Task3_Done";
        };

        // 提交任务2和3，并发执行
        Future<String> future2 = executor.submit(callableTask2);
        Future<String> future3 = executor.submit(callableTask3);

        // 等待任务2和3都完成
        future2.get(); // 阻塞，直到任务2完成
        future3.get(); // 阻塞，直到任务3完成

        // 定义任务4
        Runnable task4 = () -> {
            System.out.println("任务4: 开始执行。");
            System.out.println("任务4: 执行完毕。");
        };

        // 提交任务4
        executor.submit(task4);

        executor.shutdown();
    }

}
