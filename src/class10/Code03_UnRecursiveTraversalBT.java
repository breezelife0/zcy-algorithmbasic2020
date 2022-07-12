package class10;

import java.util.Stack;

// ** 二叉树 **
// 2. 非递归 打印二叉树
// 提示： 任何递归函数都可以改成非递归，自己设计压栈的来实现

public class Code03_UnRecursiveTraversalBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	//先序，头左右
	//栈，压head，弹出打印，先压【右】再压【左】
	public static void pre(Node head) {
		System.out.print("pre-order: ");
		if (head != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.add(head);
			while (!stack.isEmpty()) {
				head = stack.pop();
				System.out.print(head.value + " ");
				if (head.right != null) {
					stack.push(head.right);
				}
				if (head.left != null) {
					stack.push(head.left);
				}
			}
		}
		System.out.println();
	}

	//中序，栈，左头右
	//中序：可以全部被左树分解
	public static void in(Node cur) {
		System.out.print("in-order: ");
		if (cur != null) {
			Stack<Node> stack = new Stack<Node>();
			while (!stack.isEmpty() || cur != null) {
				//整条左边界一直入栈(条件1)
				if (cur != null) {
					stack.push(cur);
					cur = cur.left;
				} else {
					//往上弹左边界，再打印该节点的整个左子树(条件1)
					cur = stack.pop();
					System.out.print(cur.value + " ");
					cur = cur.right;
				}
			}
		}
		System.out.println();
	}

	//后序1：左右头
	//利用先序，用栈2反转下：弹出入栈2，先压【左】再压【右】；最右遍历弹出栈2打印
	public static void pos1(Node head) {
		System.out.print("pos-order: ");
		if (head != null) {
			Stack<Node> s1 = new Stack<Node>();
			Stack<Node> s2 = new Stack<Node>();
			s1.push(head);
			while (!s1.isEmpty()) {
				head = s1.pop(); // 头 右 左
				s2.push(head);
				if (head.left != null) {
					s1.push(head.left);
				}
				if (head.right != null) {
					s1.push(head.right);
				}
			}
			// 左 右 头
			while (!s2.isEmpty()) {
				System.out.print(s2.pop().value + " ");
			}
		}
		System.out.println();
	}

	//后序2：左右头；
	// 利用peek，一个栈，两个指针 c & h
	public static void pos2(Node h) {
		System.out.print("pos-order: ");
		if (h != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.push(h);
			Node c = null;
			while (!stack.isEmpty()) {
				c = stack.peek();
				//左右孩子都没处理，新树，先处理左孩子
				if (c.left != null && h != c.left && h != c.right) {
					stack.push(c.left);
				//左孩子处理了，右孩子没处理，处理左孩子
				} else if (c.right != null && h != c.right) {
					stack.push(c.right);
				//左右孩子都处理了，处理自己
				} else {
					//h跟踪上次打印的节点
					System.out.print(stack.pop().value + " ");
					h = c;
				}
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		head.right.right = new Node(7);

		pre(head);
		System.out.println("========");
		in(head);
		System.out.println("========");
		pos1(head);
		System.out.println("========");
		pos2(head);
		System.out.println("========");
	}

}
