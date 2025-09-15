package com.wsw.leetcode.huadongchuangkou;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangsongwen
 * @Date 2025/9/13 22:33
 * @Description: 无重复字符的最长子串
 */
public class H1 {

    /**
     * 滑动窗口
     */
    public int lengthOfLongestSubstring(String s) {
        int left = 0, maxLength = 0;
        Map<Character, Integer> map = new HashMap<>(); // 记录字符 -> 最后出现的索引

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (map.containsKey(c) && map.get(c) >= left) { // 发现重复，且重复字符在当前窗口内
                left = map.get(c) + 1; // 左指针跳到重复字符的下一个位置
            }
            map.put(c, right); // 更新字符位置
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        H1 h1 = new H1();
        String s = "abcabcbb";
        s = "bbbbb";
        s = "pwwkew";
        int i = h1.lengthOfLongestSubstring(s);
        System.out.println(i);
    }

}
