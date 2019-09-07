package com.wsw.leetcode.Main;

public class MyOrder {
    static int y=10;
    static {
        System.out.println("父类静态块"+y);
    }
    {
        System.out.println("父类代码块");
    }
    public MyOrder() {
        System.out.println("父类构造方法");
    }
    public static class InnerOrder{
        static {
            System.out.println("我是内部类");
        }
    }
    public static void main(String[] args){
        new SonOrder();
    }
}
class SonOrder extends MyOrder{
    static int x=10;
    static {
        System.out.println("子类静态块"+x);
    }
    {
        System.out.println("子类代码块");
    }
    public SonOrder() {
        System.out.println("子类构造方法");
    }
}