package com.wsw.leetcode.Tree;

/**
 * @Author wangsongwen
 * @Date 2025/11/19 23:04
 * @Description: 路径总和
 * <p>
 * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。如果存在，返回 true ；否则，返回 false 。
 * 叶子节点 是指没有子节点的节点。
 * <p>
 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
 * 输出：true
 * 解释：5 4 11 2
 * <p>
 * 输入：root = [1,2,3], targetSum = 5
 * 输出：false
 */
public class T1 {

    /**
     * 深度优先搜索（DFS） + 回溯思想
     * <p>
     * 从根开始，每往下走一层，就用 targetSum - 当前节点值
     * 到达叶子节点时，检查剩余目标是否为 0
     * 如果是，说明找到了一条有效路径
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        // 空树没有路径
        if (root == null) return false;

        // 到达叶子节点：检查是否匹配
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }

        // 递归检查左右子树（目标值减去当前节点值）
        return hasPathSum(root.left, targetSum - root.val) ||
                hasPathSum(root.right, targetSum - root.val);
    }

    public static void main(String[] args) {
        T1 t1 = new T1();
        TreeNode node1 = new TreeNode(5);
        TreeNode node2 = new TreeNode(4);
        TreeNode node3 = new TreeNode(8);
        TreeNode node4 = new TreeNode(11);
        TreeNode node5 = new TreeNode(13);
        TreeNode node6 = new TreeNode(4);
        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(2);
        TreeNode node9 = new TreeNode(1);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node3.left = node5;
        node3.right = node6;
        node4.left = node7;
        node4.right = node8;
        node6.right = node9;
        System.out.println(t1.hasPathSum(node1, 22));
    }

}
