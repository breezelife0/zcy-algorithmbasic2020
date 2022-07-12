package class11;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

// ** 二叉树 **
// 5.求二叉树最宽的层有多少个节点
public class Code05_TreeMaxWidth {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	//入队列时记录该节点的level,出队列统计该层个数，出队列的节点的Level发生变化，结算上一层。
	public static int maxWidthUseMap(Node head) {
		if (head == null) {
			return 0;
		}
		Queue<Node> queue = new LinkedList<>();
		queue.add(head);
		// key 在 哪一层，value
		HashMap<Node, Integer> levelMap = new HashMap<>();
		levelMap.put(head, 1);
		int curLevel = 1; // 当前你正在统计哪一层的宽度
		// !!! 出队列时计数
		int curLevelNodes = 0; // 当前层curLevel层，宽度目前是多少

		int max = 0;
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			int curNodeLevel = levelMap.get(cur); //
			if (cur.left != null) {
				levelMap.put(cur.left, curNodeLevel + 1);
				queue.add(cur.left);
			}
			if (cur.right != null) {
				levelMap.put(cur.right, curNodeLevel + 1);
				queue.add(cur.right);
			}
			if (curNodeLevel == curLevel) {
				curLevelNodes++;
			} else {//到了下一层
				//有最新层时，结算上一层
				max = Math.max(max, curLevelNodes);
				curLevel++;
				curLevelNodes = 1;
			}
		}
		// 最后一层没有新的层，单独结算
		max = Math.max(max, curLevelNodes);
		return max;
	}

	public static int maxWidthUseMapMy(Node head) {
		return 1;
	}



	public static int maxWidthNoMap(Node head){
		if (head == null) {
			return 0;
		}
		Queue<Node> queue = new LinkedList<>();
		queue.add(head);
		Node curEnd = head; // 当前层，最右节点是谁
		Node nextEnd = null; // 下一层，最右节点是谁
		int max = 0;
		int curLevelNodes = 0; // 当前层的节点数
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			if (cur.left != null) {
				queue.add(cur.left);
				nextEnd = cur.left;
			}
			if (cur.right != null) {
				queue.add(cur.right);
				nextEnd = cur.right;
			}
			curLevelNodes++;
			if (cur == curEnd) {
				max = Math.max(max, curLevelNodes);
				curLevelNodes = 0;
				curEnd = nextEnd;
			}
		}
		return max;
	}

	//更简单些
	public static int maxWidthNoMapMy(Node head) {
		return 1;

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
		int maxLevel = 10;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (maxWidthUseMap(head) != maxWidthNoMapMy(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");

	}

}
