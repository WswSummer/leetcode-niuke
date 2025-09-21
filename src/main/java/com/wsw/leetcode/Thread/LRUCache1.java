package com.wsw.leetcode.Thread;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author wangsongwen
 * @Date 2025/9/21 20:58
 * @Description: 实现一个 LRU（Least Recently Used）缓存
 * get(key)：获取 key 对应的 value，如果 key 不存在返回 -1；若存在，则将该 key 提升为最近使用。
 * set(key, value)：插入或更新 key-value，若 key 已存在则更新并提升为最近使用；若 key 不存在，则插入，若超出容量则淘汰最久未使用的 key。
 * <p>
 * LinkedHashMap + 重写removeEldestEntry
 * Java 的 LinkedHashMap 默认按插入顺序维护链表，但可通过构造函数设置为访问顺序（accessOrder = true），即每次 get 或 put 都会把元素移到链表尾部 —— 尾部是最新访问的，头部是最久未访问的。
 * 我们只需重写 removeEldestEntry 方法，在 size 超过 capacity 时自动移除最老元素。
 */
public class LRUCache1 {

    private final int capacity;
    private final LinkedHashMap<Integer, Integer> cache;

    public LRUCache1(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                // 当大小超过容量时，自动移除最老元素
                return size() > LRUCache1.this.capacity;
            }
        };
    }

    public int get(int key) {
        return cache.getOrDefault(key, -1);
    }

    public void set(int key, int value) {
        cache.put(key, value);
    }

    public static void main(String[] args) {
        LRUCache1 lruCache = new LRUCache1(2);

        lruCache.set(1, 1);
        lruCache.set(2, 2);
        System.out.println(lruCache.get(1)); // 输出 1

        lruCache.set(3, 3); // 淘汰key=2
        System.out.println(lruCache.get(2)); // 输出 -1

        lruCache.set(4, 4); // 淘汰key=1

        System.out.println(lruCache.get(1)); // 输出 -1
        System.out.println(lruCache.get(3)); // 输出 3
        System.out.println(lruCache.get(4)); // 输出 4
    }

}
