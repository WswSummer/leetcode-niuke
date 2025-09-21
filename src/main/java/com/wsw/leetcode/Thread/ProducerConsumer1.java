package com.wsw.leetcode.Thread;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author wangsongwen
 * @Date 2025/9/21 20:33
 * @Description: 生产者消费者模型-synchronized+wait/notify
 */
public class ProducerConsumer1 {

    private static final int CAPACITY = 5;
    private final Queue<Integer> queue = new LinkedList<>();

    // 生产者
    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (queue) {
                while (queue.size() == CAPACITY) {
                    System.out.println("队列已满, 生产者等待...");
                    queue.wait(); // 释放锁，等待消费者消费
                }
                queue.offer(value);
                System.out.println("生产者生产: " + value);
                value++;
                queue.notify(); // 唤醒等待的消费者
                Thread.sleep(1000); // 模拟生产耗时
            }
        }
    }

    // 消费者
    public void consume() throws InterruptedException {
        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    System.out.println("队列已空, 消费者等待...");
                    queue.wait(); // 释放锁，等待生产者生产
                }
                int value = queue.poll();
                System.out.println("消费者消费: " + value);
                queue.notify(); // 唤醒等待的生产者
                Thread.sleep(5000); // 模拟消费耗时
            }
        }
    }

    public static void main(String[] args) {
        ProducerConsumer1 producerConsumer = new ProducerConsumer1();

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
