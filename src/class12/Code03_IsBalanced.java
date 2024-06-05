package class12;

// ** 二叉树递归套路 **
// 3.给定一棵二叉树的头节点head，返回这颗二叉树是不是平衡二叉树

/**
 * 平衡二叉树
 * 一棵空树或它的左右两个子树的高度差的绝对值不超过1就是平衡二叉树
 *
 *
 * 平衡二叉树有很多种，其中比较著名的是 AVL 树和红黑树。
 * AVL 树是最早的【自平衡二叉搜索树】，它的每个节点都有两个属性：左子树和右子树的高度。通过不断调整树的结构，使得每个节点的两个子树高度差不超过1，从而保证树的平衡性。
 * 红黑树是一种【自平衡的二叉搜索树】，它通过将节点染成红色或黑色，并维护一些性质，使得在插入、删除和搜索操作时具有较好的性能。
 */

public class Code03_IsBalanced {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static boolean isBalanced1(Node head) {
		boolean[] ans = new boolean[1];
		ans[0] = true;
		process1(head, ans);
		return ans[0];
	}

	/**
	 * 方法一：后序遍历
	 * 向子树要高度
	 */
	public static int process1(Node head, boolean[] ans) {
		if (!ans[0] || head == null) {
			return -1;
		}
		int leftHeight = process1(head.left, ans);
		int rightHeight = process1(head.right, ans);
		if (Math.abs(leftHeight - rightHeight) > 1) {
			ans[0] = false;
		}
		return Math.max(leftHeight, rightHeight) + 1;
	}

	public static boolean isBalanced2(Node head) {
		return process(head).isBalanced;
	}
	
	public static class Info{
		public boolean isBalanced;
		public int height;
		
		public Info(boolean i, int h) {
			isBalanced = i;
			height = h;
		}
	}
	
	public static Info process(Node x) {
		if(x == null) {
			return new Info(true, 0);
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);
		int height = Math.max(leftInfo.height, rightInfo.height)  + 1;
		boolean isBalanced = true;
		if(!leftInfo.isBalanced) {
			isBalanced = false;
		}
		if(!rightInfo.isBalanced) {
			isBalanced = false;
		}
		if(Math.abs(leftInfo.height - rightInfo.height) > 1) {
			isBalanced = false;
		}
		return new Info(isBalanced, height);
	}


	// for test
	public static Node generateRandomBST(int maxLevel, int maxValue) {
		return generate(1, maxLevel, maxValue);
	}

	// for test
	public static Node generate(int level, int maxLevel, int maxValue) {
		if (level > maxLevel || Math.random() < 0.5) {
			return null;
		}
		Node head = new Node((int) (Math.random() * maxValue));
		head.left = generate(level + 1, maxLevel, maxValue);
		head.right = generate(level + 1, maxLevel, maxValue);
		return head;
	}


	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (isBalanced1(head) != isBalanced2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
