package com.wsw.leetcode.dongtaiguihua;

/**
 * @Author wangsongwen
 * @Date 2025/9/15 15:19
 * @Description: 爬楼梯
 * 要走到第 n 级，最后一步要么是从 n-1 级走 1 步，要么是从 n-2 级走 2 步，所以 “n 级的走法数 = n-1 级的走法数 + n-2 级的走法数”
 * 状态转移方程：dp [n] = dp [n-1] + dp [n-2]，base case 是 dp [1]=1，dp [2]=2；
 * 本质是把复杂问题拆解为重叠子问题，用递推关系解决，时间 O (n)，空间 O (1)。
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
