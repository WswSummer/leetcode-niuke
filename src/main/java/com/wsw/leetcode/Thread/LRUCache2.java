package com.wsw.leetcode.Thread;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangsongwen
 * @Date 2025/9/21 20:58
 * @Description: 实现一个 LRU（Least Recently Used）缓存
 * get(key)：获取 key 对应的 value，如果 key 不存在返回 -1；若存在，则将该 key 提升为最近使用。
 * set(key, value)：插入或更新 key-value，若 key 已存在则更新并提升为最近使用；若 key 不存在，则插入，若超出容量则淘汰最久未使用的 key。
 * <p>
 * 使用 HashMap + 双向链表 自己实现
 */
public class LRUCache2 {

    class Node {
        int key, value;
        Node prev, next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> map;
    private final Node head, tail; // 虚拟头尾节点

    public LRUCache2(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        moveToHead(node); // 提升为最近使用
        return node.value;
    }

    public void set(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            moveToHead(node); // 提升为最近使用
        } else {
            Node node = new Node(key, value);
            map.put(key, node);
            addToHead(node);
            if (map.size() > capacity) {
                Node tailNode = removeTail();
                map.remove(tailNode.key); // 同步移除map中的键
            }
        }
    }

    // 双向链表辅助方法
    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }

    private Node removeTail() {
        Node node = tail.prev;
        removeNode(node);
        return node;
    }


    public static void main(String[] args) {
        LRUCache2 lruCache = new LRUCache2(2);

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
