package com.wsw.leetcode.Array;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangsongwen
 * @Date 2025/11/20 09:38
 * @Description: 和为 K 的子数组
 * <p>
 * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。
 * 子数组是数组中元素的连续非空序列。
 * <p>
 * 示例 1：
 * 输入：nums = [1,1,1], k = 2
 * 输出：2
 * <p>
 * 示例 2：
 * 输入：nums = [1,2,3], k = 3
 * 输出：2
 */
public class A16 {

    // 暴力解法（O(n²)）枚举所有子数组，计算和
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k)
                    count++;
            }
        }
        return count;
    }

    // 前缀和 + 哈希表（O(n)）
    public int subarraySum2(int[] nums, int k) {
        Map<Integer, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0, 1); // 关键！空子数组和为0

        int count = 0;
        int sum = 0;

        for (int num : nums) {
            sum += num;                     // 当前前缀和
            int need = sum - k;             // 需要找的前缀和
            count += prefixCount.getOrDefault(need, 0);
            prefixCount.put(sum, prefixCount.getOrDefault(sum, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        A16 a16 = new A16();
        int[] nums = {1, 1, 1};
        System.out.println(a16.subarraySum(nums, 2));
    }

}
