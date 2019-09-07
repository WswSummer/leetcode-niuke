package com.wsw.leetcode.Main;

public class Main {
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//        Student s1 = new Student("小张");
//        Student s2 = new Student("小李");
//        Main.swap(s1, s2);
//        System.out.println("s1:" + s1.getName());
//        System.out.println("s2:" + s2.getName());
//    }

    public static void swap(Student x, Student y) {
        Student temp = x;
        x = y;
        y = temp;
        System.out.println("x:" + x.getName());
        System.out.println("y:" + y.getName());
    }

    public static void main(String[] args) {
        float a = 2.0f;
        double b = 2.0;
        long c = 2;
        System.out.println(c);

        boolean b1 = true;
        boolean b2 = true;
        if (b1 == b2){
            System.out.println("true");
        }

        int i = 1;
        int j = 2;
        if (i == 1 && j ==2){

        }
        if (i == 1 || j ==2){

        }

    }
}
