package com.wsw.leetcode.Main;

public class Student {
    private String name;
    private int no;

    Student(String name){
        this.name = name;
    }

    Student(String name, int no){
        this.name = name;
        this.no = no;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
