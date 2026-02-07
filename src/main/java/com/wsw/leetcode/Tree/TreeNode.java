package com.wsw.leetcode.Tree;

/**
 * @Author wangsongwen
 * @Date 2025/11/19 23:31
 * @Description:
 */
public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

}
