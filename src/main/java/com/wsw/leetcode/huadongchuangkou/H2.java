package com.wsw.leetcode.huadongchuangkou;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangsongwen
 * @Date 2025/9/15 13:11
 * @Description: 最小覆盖子串
 */
public class H2 {

    public String minWindow(String s, String t) {
        // 1. 初始化需求哈希表 (need) 和 窗口哈希表 (window)
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        // 统计 t 中每个字符需要的频次
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        // 2. 初始化指针和计数器
        int left = 0, right = 0; // 滑动窗口 [left, right)
        int valid = 0; // 窗口中满足 need 条件的字符种类数
        int start = 0, len = Integer.MAX_VALUE; // 记录最小覆盖子串的起始索引及长度

        // 3. 开始滑动窗口 (右指针扩展)
        while (right < s.length()) {
            char c = s.charAt(right); // 即将移入窗口的字符
            right++; // 扩大窗口 (注意：窗口是 [left, right)，所以先取char再right++)

            // 4. 更新窗口内数据 (如果是 t 中需要的字符)
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                // 如果窗口中该字符的数量刚好达到需求，则 valid++
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }

            // 5. 判断左侧窗口是否要收缩 (当窗口满足条件时：valid == need.size())
            while (valid == need.size()) {
                // 6. 在这里更新最小覆盖子串 (收缩前先记录当前最优解)
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }

                // 7. 收缩窗口 (左指针右移)
                char d = s.charAt(left); // 即将移出窗口的字符
                left++;

                // 8. 更新窗口内数据 (如果是 t 中需要的字符)
                if (need.containsKey(d)) {
                    // 如果移出前该字符数量是刚好的，移出后就不满足了，valid--
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1); // 更新窗口内该字符数量
                }
            }
        }

        // 9. 返回结果
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    public static void main(String[] args) {
        H2 h2 = new H2();
        String s = "ADOBECODEBANC";
        String t = "ABCB";
        System.out.println(h2.minWindow(s, t));
    }

}
