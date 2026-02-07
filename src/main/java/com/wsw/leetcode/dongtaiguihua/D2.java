package com.wsw.leetcode.dongtaiguihua;

/**
 * @Author wangsongwen
 * @Date 2025/9/15 15:41
 * @Description: 最长递增子序列
 * <p>
 * 示例 1：
 * 输入：nums = [10,9,2,5,3,7,101,18]
 * 输出：4
 * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
 * <p>
 * 示例 2：
 * 输入：nums = [0,1,0,3,2,3]
 * 输出：4
 * <p>
 * 示例 3：
 * 输入：nums = [7,7,7,7,7,7,7]
 * 输出：1
 */
public class D2 {

    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // dp[i] 表示以 nums[i] 结尾的最长递增子序列的长度
        int[] dp = new int[nums.length];

        // 初始化：每个元素自身构成长度为1的递增子序列
        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
        }

        int maxLength = 1;

        // 对于每个位置i，检查前面所有小于nums[i]的元素
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                // 如果nums[j] < nums[i]，可以将nums[i]接在以nums[j]结尾的递增子序列后面
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLength = Math.max(maxLength, dp[i]);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        D2 d2 = new D2();
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(d2.lengthOfLIS(nums));
    }

}
