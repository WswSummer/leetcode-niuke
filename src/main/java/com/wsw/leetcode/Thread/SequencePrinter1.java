package com.wsw.leetcode.Thread;

/**
 * @Author wangsongwen
 * @Date 2025/9/4 12:38
 * @Description: 3个线程A, B, C 顺序输出ABC，需要A输出后B输出，B输出后C输出
 * <p>
 * join()方法允许一个线程等待另一个线程执行完毕。这是一种简单粗暴的顺序控制方法，严格来说，它让多线程变成了串行执行。
 * 它并没有实现线程间的并发协作，而是在主线程中将子线程串行执行，失去了多线程的优势。如果需要循环或更复杂的协作，这种方法就不适用。
 */
public class SequencePrinter1 {

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(() -> System.out.println("A"));
        Thread threadB = new Thread(() -> System.out.println("B"));
        Thread threadC = new Thread(() -> System.out.println("C"));

        threadA.start();
        threadA.join(); // main线程等待A完成

        threadB.start();
        threadB.join(); // main线程等待B完成

        threadC.start();
        threadC.join(); // main线程等待C完成

    }

}




