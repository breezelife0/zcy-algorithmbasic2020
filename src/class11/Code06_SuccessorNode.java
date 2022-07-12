package class11;


// ** 二叉树 **
// 6.给你二叉树中的某个节点，返回该节点的后继节点
// 后继节点定义：中序遍历排序后，当前节点的下一个节点
// 前继节点定义：中序遍历排序后，当前节点的上一个节点

// 包含父节信息的特殊二叉树：通过K步到达后继节点，则做到时间复杂度O(k)
// 1. 没有右树，且是父孩子的左孩子，则父是该节点的后继【左（头）右】，即 头 是左树的最后一个节点的后继；

// 1）有右树，头的后继：4->5 ,右树的最左孩子
//	  4
// 2    6
//1  3 5  7  中序【1 2 3 4 5 6 7】

// 2）无右树，
// 		2.1）该节点如果是父的左孩子，则父是其后继 1-> 2, 2->3
//		2.2）该节点如果是父的右孩子， 一直找到祖先是祖先的父亲的左孩子，则该祖先的父亲即为其后继：5->6 , 11-> null
//			  	   6
//  		3				9
//		2   	5	 	 8	   	10
// 	 1        4		  7				11   中序[1 2 3 4 5 6 7 8 9 10 11 ]


public class Code06_SuccessorNode {

	public static class Node {
		public int value;
		public Node left;
		public Node right;
		public Node parent;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node getSuccessorNode(Node node) {
		if (node == null) {
			return node;
		}
		//有右树，把node当头，找右树上的最左节点
		if (node.right != null) {
			return getLeftMost(node.right);
		} else { // 无右子树
			Node parent = node.parent;
			while (parent != null && parent.right == node) { // 当前节点是其父亲节点右孩子
				node = parent;
				parent = node.parent;
			}
			return parent;
		}
	}

	public static Node getLeftMost(Node node) {
		if (node == null) {
			return node;
		}
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	public static void main(String[] args) {
		Node head = new Node(6);
		head.parent = null;
		head.left = new Node(3);
		head.left.parent = head;
		head.left.left = new Node(1);
		head.left.left.parent = head.left;
		head.left.left.right = new Node(2);
		head.left.left.right.parent = head.left.left;
		head.left.right = new Node(4);
		head.left.right.parent = head.left;
		head.left.right.right = new Node(5);
		head.left.right.right.parent = head.left.right;
		head.right = new Node(9);
		head.right.parent = head;
		head.right.left = new Node(8);
		head.right.left.parent = head.right;
		head.right.left.left = new Node(7);
		head.right.left.left.parent = head.right.left;
		head.right.right = new Node(10);
		head.right.right.parent = head.right;



		Node test = head.left.left;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.left.left.right;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.left;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.left.right;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.left.right.right;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.right.left.left;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.right.left;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.right;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.right.right; // 10's next is null
		System.out.println(test.value + " next: " + getSuccessorNode(test));

//			1
//		2		3
//		中序 2 1 3 ； head S: 1->3 , left s：
		Node head2 = new Node(1);
		head2.left = new Node(2);
		head2.left.parent = head2;
		head2.right = new Node(3);
		head2.right.parent = head2;

		System.out.println("==== ");
		System.out.println(getSuccessorNode(head2).value);
		System.out.println(getSuccessorNode(head2.left).value);
		System.out.println(getSuccessorNode(head2.right).value);

	}

}
