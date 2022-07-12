package class11;


import java.util.LinkedList;
import java.util.Queue;

// ** 二叉树 **
// 1.实现二叉树的按层遍
// 思路：1）其实就是宽度优先遍历，用队列
//		2）可以通过设置flag变量的方式，来发现某一层的结束（看题目）

public class Code01_LevelTraversalBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	//用链表作为队列，宽度优先遍历
	public static void level(Node head) {
		if (head == null) {
			return;
		}
		Queue<Node> queue = new LinkedList<>();
		queue.add(head);
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			System.out.println(cur.value);
			if (cur.left != null) {
				queue.add(cur.left);
			}
			if (cur.right != null) {
				queue.add(cur.right);
			}
		}
	}

	public static void levelMy(Node head){

	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		head.right.right = new Node(7);

		level(head);
		System.out.println("========");
		levelMy(head);
		System.out.println("========");

	}

}
