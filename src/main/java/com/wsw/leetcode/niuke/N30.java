package com.wsw.leetcode.niuke;

/**
 * Created by wsw on 2019/9/14 14:32
 * 1--n整数中1出现的次数
 */
public class N30 {
    public int NumberOf1Between1AndN_Solution(int n) {
        int number = 0;
        for (int i = 1; i <= n; i++) {
            number += NumberOf1(i);
        }
        return number;
    }

    private static int NumberOf1(int n){
        int number = 0;
        while (n != 0){
            if (n % 10 == 1)
                number++;
            n /= 10;
        }
        return number;
    }

    public static void main(String[] args) {
        N30 n30 = new N30();
        int res = n30.NumberOf1Between1AndN_Solution(100);
        System.out.println(res);
    }
}
