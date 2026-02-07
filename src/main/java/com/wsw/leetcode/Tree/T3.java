package com.wsw.leetcode.Tree;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangsongwen
 * @Date 2025/11/20 00:12
 * @Description: 路径总和3
 * <p>
 * 给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
 * 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
 * <p>
 * 输入：root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
 * 输出：3 [5,3] [5,2,1] [-3,11]
 * <p>
 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
 * 输出：3
 */
public class T3 {

    /**
     * 暴力 DFS（双重递归）—— 简单但效率低
     */
//    public int pathSum(TreeNode root, int targetSum) {
//        if (root == null) return 0;
//
//        // 以当前节点为起点的路径数 + 左右子树的路径数
//        return dfs(root, targetSum)
//                + pathSum(root.left, targetSum)
//                + pathSum(root.right, targetSum);
//    }
//
//    // 从 node 开始，向下找路径和为 targetSum 的数量
//    private int dfs(TreeNode node, long targetSum) {
//        if (node == null) return 0;
//
//        int count = 0;
//        if (node.val == targetSum) {
//            count = 1; // 找到一条
//        }
//
//        // 继续向下（注意：即使找到，也要继续，因为可能有 0 或负数）
//        count += dfs(node.left, targetSum - node.val);
//        count += dfs(node.right, targetSum - node.val);
//
//        return count;
//    }

    /**
     * 前缀和 + 哈希表
     */
    public int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> prefixSumCount = new HashMap<>();
        prefixSumCount.put(0L, 1); // 初始化：空路径和为0
        return dfs(root, 0L, targetSum, prefixSumCount);
    }

    private int dfs(TreeNode node, long currSum, int targetSum, Map<Long, Integer> map) {
        if (node == null) return 0;

        // 更新当前前缀和
        currSum += node.val;

        // 查找是否存在前缀和 = currSum - targetSum
        // 查有没有能构成 target 的起点
        int count = map.getOrDefault(currSum - targetSum, 0);

        // 将当前前缀和加入 map
        map.put(currSum, map.getOrDefault(currSum, 0) + 1);

        // 递归左右子树
        count += dfs(node.left, currSum, targetSum, map);
        count += dfs(node.right, currSum, targetSum, map);

        // 回溯：移除当前前缀和（避免影响其他分支）
        map.put(currSum, map.get(currSum) - 1);
        if (map.get(currSum) == 0) {
            map.remove(currSum);
        }

        return count;
    }

    public static void main(String[] args) {
        T3 t3 = new T3();
        TreeNode node1 = new TreeNode(5);
        TreeNode node2 = new TreeNode(4);
        TreeNode node3 = new TreeNode(8);
        TreeNode node4 = new TreeNode(11);
        TreeNode node5 = new TreeNode(13);
        TreeNode node6 = new TreeNode(4);
        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(2);
        TreeNode node9 = new TreeNode(5);
        TreeNode node10 = new TreeNode(1);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node3.left = node5;
        node3.right = node6;
        node4.left = node7;
        node4.right = node8;
        node6.left = node9;
        node6.right = node10;
        System.out.println(t3.pathSum(node1, 22));
    }

}
