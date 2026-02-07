package com.wsw.leetcode.heap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @Author wangsongwen
 * @Date 2025/11/16 22:15
 * @Description: 前 K 个高频元素
 * <p>
 * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
 * 示例 1：
 * 输入：nums = [1,1,1,2,2,3], k = 2
 * 输出：[1,2]
 * <p>
 * 示例 2：
 * 输入：nums = [1], k = 1
 * 输出：[1]
 * <p>
 * 示例 3：
 * 输入：nums = [1,2,1,2,1,2,3,1,3,2], k = 2
 * 输出：[1,2]
 */
public class H3 {

    // 哈希表 + 最小堆
    public int[] topKFrequent(int[] nums, int k) {
        // 1. 统计频率
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // 2. 创建最小堆（按频率排序）
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap =
                new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());

        // 3. 遍历频率表，维护大小为 k 的堆
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            if (minHeap.size() < k) {
                minHeap.offer(entry);
            } else if (entry.getValue() > minHeap.peek().getValue()) {
                minHeap.poll();
                minHeap.offer(entry);
            }
        }

        // 4. 提取结果
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = minHeap.poll().getKey();
        }
        return result;
    }

    public static void main(String[] args) {
        H3 h3 = new H3();
        int[] nums = {1, 1, 1, 2, 2, 3, 3, 3, 3};
        System.out.println(Arrays.toString(h3.topKFrequent(nums, 2)));
    }

}
