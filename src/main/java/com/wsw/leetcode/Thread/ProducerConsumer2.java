package com.wsw.leetcode.Thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author wangsongwen
 * @Date 2025/9/21 20:33
 * @Description: 生产者消费者模型-BlockingQueue，推荐，Java 并发包提供了线程安全的阻塞队列，自动处理同步和阻塞
 */
public class ProducerConsumer2 {

    private static final int CAPACITY = 5;
    private final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(CAPACITY);

    // 生产者
    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            queue.put(value); // 队列满时自动阻塞
            System.out.println("生产者生产: " + value);
            value++;
            Thread.sleep(1000); // 模拟生产耗时
        }
    }

    // 消费者
    public void consume() throws InterruptedException {
        while (true) {
            int value = queue.take(); // 队列空时自动阻塞
            System.out.println("消费者消费: " + value);
            Thread.sleep(1500); // 模拟消费耗时
        }
    }

    public static void main(String[] args) {
        ProducerConsumer2 producerConsumer = new ProducerConsumer2();

        // 启动生产者线程
        new Thread(() -> {
            try {
                producerConsumer.produce();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer").start();

        // 启动消费者线程
        new Thread(() -> {
            try {
                producerConsumer.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer").start();
    }

}
