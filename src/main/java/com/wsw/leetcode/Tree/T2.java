package com.wsw.leetcode.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangsongwen
 * @Date 2025/11/19 23:15
 * @Description: 路径总和2
 * <p>
 * 给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
 * <p>
 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
 * 输出：[[5,4,11,2],[5,8,4,5]]
 * <p>
 * 输入：root = [1,2,3], targetSum = 5
 * 输出：[]
 */
public class T2 {

    /**
     * 深度优先搜索（DFS） + 回溯（Backtracking）
     * <p>
     * 使用 递归遍历 所有可能的根→叶路径；
     * 用一个 临时列表 path 记录当前路径；
     * 到达叶子节点时，若路径和等于 targetSum，就把当前路径 拷贝一份 加入结果；
     * 回溯：在递归返回前，把当前节点从 path 中移除，避免影响其他分支。
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(root, targetSum, new ArrayList<>(), result);
        return result;
    }

    private void dfs(TreeNode node, int targetSum, List<Integer> path, List<List<Integer>> result) {
        // 终止条件：空节点直接返回
        if (node == null) return;

        // 将当前节点加入路径
        // 为什么先加再判断？保证叶子节点也被加入 path，否则路径不完整
        path.add(node.val);

        // 如果是叶子节点，检查是否满足目标和
        if (node.left == null && node.right == null) {
            if (targetSum == node.val) {
                // 注意：要拷贝！
                // result.add(path) 添加的是引用，后续 path 被修改会导致结果错误
                // 必须 深拷贝当前路径的快照
                result.add(new ArrayList<>(path));
            }
        } else {
            // 递归左右子树，目标值减去当前节点值
            dfs(node.left, targetSum - node.val, path, result);
            dfs(node.right, targetSum - node.val, path, result);
        }

        // 回溯：移除当前节点，恢复 path 状态
        // 无论是否找到有效路径，只要递归返回，就要 remove
        path.remove(path.size() - 1);
    }

    public static void main(String[] args) {
        T2 t2 = new T2();
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
        System.out.println(t2.pathSum(node1, 22));
    }

}
