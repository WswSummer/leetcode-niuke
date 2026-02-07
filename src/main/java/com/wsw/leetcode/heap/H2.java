package com.wsw.leetcode.heap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Author wangsongwen
 * @Date 2025/11/16 21:26
 * @Description:
 */
public class H2 {

    /**
     * 20GB 数据，每个数 ≤ Long.MAX_VALUE（即可用 long 表示），求最大的 100 个数（Top-100）
     *
     * @param filePath
     * @return List<Long>
     */
    public static List<Long> findTop100(String filePath) throws IOException {
        //维护一个 大小为 100 的小顶堆
        //堆顶是当前 Top-100 中最小的
        //新来的数如果 > 堆顶，则替换堆顶并调整堆
        PriorityQueue<Long> minHeap = new PriorityQueue<>(100);

        // 大顶堆
        //PriorityQueue<Long> maxHeap = new PriorityQueue<>(100, Collections.reverseOrder());

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                long num = Long.parseLong(line.trim());
                if (minHeap.size() < 100) {
                    minHeap.offer(num);
                } else if (num > minHeap.peek()) {
                    minHeap.poll();      // 移除最小的
                    minHeap.offer(num);  // 加入更大的
                }
            }
        }

        // 转为列表并降序排序（可选）
        List<Long> result = new ArrayList<>(minHeap);
        result.sort(Collections.reverseOrder());
        return result;
    }

    public static void main(String[] args) {

    }

}
