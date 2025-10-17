package com.wsw.leetcode.Thread;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;

/**
 * @Author wangsongwen
 * @Date 2025/10/17 15:31
 * @Description: 并行且异步发送n个HTTP POST请求
 * 当n个请求都返回，并且在各自规定时间内获得response，要求是第一个request在发出去的一秒内收到，第二个request在两秒内...第n个request在n秒内收到，则返回成功
 * 如果不满足以上条件且等待超过n秒，则返回失败，并且记录失败原因
 * 支持打印输出到log文件，并以清晰的格式呈现
 * 提供必要的http 服务端程序，能够接收http request以及模拟所需要的行为
 *
 */
@Slf4j
public class MultiHttpRequestTest {

    public void asyncSendPostRequest(int n) throws Exception {
        CountDownLatch latch = new CountDownLatch(n); // 协调多个线程之间的同步, 以免子线程执行过程中被主线程阻断, 确保主线程等待所有子线程完成后再继续执行
        ExecutorService threadPool = Executors.newFixedThreadPool(8); // 固定大小线程池
        Map<Integer, String> failMsgMap = new HashMap<>(); // 请求失败信息
        Random random = new Random();

        log.info("并发请求开始!");
        for (int i = 1; i <= n; i++) {
            int finalI = i;
            // 线程池执行
            threadPool.execute(() -> {
                try {
                    long startRequestTime = System.currentTimeMillis();
                    int number = postRequest(finalI, random); //发送请求
                    long requestTime = System.currentTimeMillis() - startRequestTime;
                    // 在各自规定时间内获得response 第一个request 在发出去的一秒内收到，第二个request在两秒内… 第n个request在n秒内收到
                    if (requestTime <= finalI * 1000L) {
                        log.info(Thread.currentThread().getName() + " -> 第" + finalI + "个POST请求成功, 请求返回数据: " + number);
                    } else {
                        String failMsg = "第" + finalI + "个POST请求失败! 失败原因: 未能在规定时间内返回respose!";
                        failMsgMap.put(finalI, failMsg);
                        log.info(Thread.currentThread().getName() + " -> " + failMsg);
                    }
                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        latch.await();
        threadPool.shutdown();

        if (failMsgMap.isEmpty()) {
            log.info("并发请求结束! 请求结果: SUCCESS!");
        } else {
            log.info("并发请求结束! 请求结果: FAIL! 原因: ");
            for (String value : failMsgMap.values()) {
                log.info(value);
            }
        }
    }

    public boolean asyncSendPostRequestWithCompletableFuture(int n) {
        List<CompletableFuture<Boolean>> futures = new ArrayList<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(8);
        Random random = new Random();

        for (int i = 1; i <= n; i++) {
            final int requestId = i;
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                try {
                    long startRequestTime = System.currentTimeMillis();
                    int number = postRequest(requestId, random);
                    long requestTime = System.currentTimeMillis() - startRequestTime;

                    if (requestTime <= requestId * 1000L) {
                        log.info(Thread.currentThread().getName() + " -> 第" + requestId + "个POST请求成功, 请求返回数据: " + number);
                        return true;
                    } else {
                        log.info(Thread.currentThread().getName() + " -> 第" + requestId + "个POST请求失败! 失败原因: 未能在规定时间内返回respose!");
                        return false;
                    }
                } catch (Exception e) {
                    log.error("请求处理异常", e);
                    return false;
                }
            }, threadPool);

            futures.add(future);
        }

        threadPool.shutdown();

        // 等待所有请求完成
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );

        try {
            allFutures.get(10, TimeUnit.SECONDS); // 设置总超时时间
            // 检查是否有失败的请求
            return futures.stream().allMatch(CompletableFuture::join);
        } catch (Exception e) {
            log.error("等待请求完成时发生异常", e);
            return false;
        }
    }

    public int postRequest(int number, Random random) {
        log.info(Thread.currentThread().getName() + " -> 发起POST请求, 请求参数: " + number);

        int delay = random.nextInt(3000); // 0-3秒随机延迟
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return number;
    }

    public static void main(String[] args) throws Exception {
        MultiHttpRequestTest multiHttpRequestTest = new MultiHttpRequestTest();
        multiHttpRequestTest.asyncSendPostRequest(5);
        //multiHttpRequestTest.asyncSendPostRequestWithCompletableFuture(5);
    }

}
