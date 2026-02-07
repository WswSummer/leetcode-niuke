package com.wsw.leetcode.dongtaiguihua;

/**
 * @Author wangsongwen
 * @Date 2025/11/19 16:02
 * @Description: 最长回文子串
 * 如果字符串向前和向后读都相同，则它满足 回文性。
 * <p>
 * 示例 1：
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 * <p>
 * 示例 2：
 * 输入：s = "cbbd"
 * 输出："bb"
 */
public class D3 {

    /**
     * 中心扩展法（推荐！O(n²) 时间，O(1) 空间）
     * <p>
     * 回文串一定围绕某个“中心”对称。
     * 对于长度为 n 的字符串，共有 2n - 1 个可能的中心：
     * n 个字符（奇数长度回文，如 "aba"，中心是 'b'）
     * n - 1 个字符间隙（偶数长度回文，如 "abba"，中心在两个 'b' 之间）
     * 对每个中心，向两边扩展，直到不满足回文条件。
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";

        int start = 0, end = 0;

        for (int i = 0; i < s.length(); i++) {
            // 奇数长度：以 i 为中心
            int len1 = expandAroundCenter(s, i, i);
            // 偶数长度：以 i 和 i+1 为中心
            int len2 = expandAroundCenter(s, i, i + 1);

            int len = Math.max(len1, len2);
            if (len > end - start) {
                // 更新最长回文的起止位置
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // 返回回文长度
        return right - left - 1;
    }

    /**
     * 动态规划
     * <p>
     * 定义 dp[i][j] 表示子串 s[i..j] 是否为回文。
     * 状态转移：
     * 如果 s[i] == s[j] 且：
     * j - i < 2（长度 ≤ 2），则是回文；
     * 或 dp[i+1][j-1] == true，则是回文。
     */
    public String longestPalindrome2(String s) {
        int n = s.length();
        if (n < 2) return s;

        boolean[][] dp = new boolean[n][n];
        int start = 0, maxLen = 1;

        // 单个字符都是回文
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        // 按长度 L 从 2 到 n 枚举
        for (int L = 2; L <= n; L++) {          // L 是子串长度
            for (int i = 0; i <= n - L; i++) {  // i 是起始位置
                int j = i + L - 1;              // j 是结束位置

                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = false;
                } else {
                    if (L == 2) {
                        dp[i][j] = true;        // "aa" 这种
                    } else {
                        dp[i][j] = dp[i + 1][j - 1]; // 看中间
                    }
                }

                // 如果是回文且更长，就更新答案
                if (dp[i][j] && L > maxLen) {
                    start = i;
                    maxLen = L;
                }
            }
        }

        return s.substring(start, start + maxLen);
    }

    public static void main(String[] args) {
        D3 d3 = new D3();
        System.out.println(d3.longestPalindrome("babad"));
        System.out.println(d3.longestPalindrome("cbbd"));

        System.out.println(d3.longestPalindrome2("babad"));
        System.out.println(d3.longestPalindrome2("cbbd"));
    }

}
