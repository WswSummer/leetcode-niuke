package com.wsw.leetcode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangsongwen
 * @Date 2025/6/24 20:15
 * @Description:
 */
public class JvmTest {

    /**
     * 消耗CPU的线程
     * 不断循环进行浮点运算
     */
    private static void cpuHigh() {
        Thread thread = new Thread(() -> {
            Thread.currentThread().setName("cpu_high_thread");
            while (true){
                double pi = 0;
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    pi += Math.pow(-1, i) / (2 * i + 1);
                }
                System.out.println("Pi: " + pi * 4);
            }
        });
        thread.start();
    }

    /**
     * 不断新增 BigDecimal 信息到 list
     */
    private static void allocate() {
        new Thread(()->{
            Thread.currentThread().setName("memory_allocate_thread");
            List<BigDecimal> list = new ArrayList<>();
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                list.add(new BigDecimal(i));
            }
        }).start();
    }

    public static void main(String[] args) {
        //cpuHigh();
        allocate();
    }

}
