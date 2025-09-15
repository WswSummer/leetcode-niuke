package com.wsw.leetcode.dongtaiguihua;

/**
 * @Author wangsongwen
 * @Date 2025/9/15 15:19
 * @Description: 爬楼梯
 */
public class D1 {

    public int climbStairs(int n) {
        if (n == 1)
            return 1;
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i < n + 1; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public static void main(String[] args) {
        D1 d1 = new D1();
        System.out.println(d1.climbStairs(4));
    }

}
