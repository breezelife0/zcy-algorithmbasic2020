package class11;



import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
// ** 二叉树 **
// 2. 二叉树的序列化和反序列化
//		1）可以用先序或者后序或者按层遍历，来实现二叉树的序列化
//		2）用了什么方式序列化，就用什么样的方式反序列化
//		3）中序遍历无法实现序列化和反序列化

//思路：先序 中序 后序 【递归遍历序】

public class Code02_SerializeAndReconstructTree {
    /*
     * 二叉树可以通过先序、后序或者按层遍历的方式序列化和反序列化，
     * 以下代码全部实现了。
     * 但是，二叉树无法通过中序遍历的方式实现序列化和反序列化
     * 因为不同的两棵树，可能得到同样的中序序列，即便补了空位置也可能一样。
     * 比如如下两棵树
     *         __2
     *        /
     *       1
     *       和
     *       1__
     *          \
     *           2
     * 补足空位置的中序遍历结果都是{ null, 1, null, 2, null}
     *       
     * */
	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	//先序序列化
	public static Queue<String> preSerial(Node head) {
		Queue<String> ans = new LinkedList<>();
		pres(head, ans);
		return ans;
	}

	public static void pres(Node head, Queue<String> ans) {
		if (head == null) {
			ans.add(null);
		} else {
			ans.add(String.valueOf(head.value));
			pres(head.left, ans);
			pres(head.right, ans);
		}
	}

	public static Queue<String> inSerial(Node head) {
		Queue<String> ans = new LinkedList<>();
		ins(head, ans);
		return ans;
	}

	public static void ins(Node head, Queue<String> ans) {
		if (head == null) {
			ans.add(null);
		} else {
			ins(head.left, ans);
			ans.add(String.valueOf(head.value));
			ins(head.right, ans);
		}
	}


	//后序序列化
	public static Queue<String> posSerial(Node head) {
		Queue<String> ans = new LinkedList<>();
		poss(head, ans);
		return ans;
	}

	public static void poss(Node head, Queue<String> ans) {
		if (head == null) {
			ans.add(null);
		} else {
			poss(head.left, ans);
			poss(head.right, ans);
			ans.add(String.valueOf(head.value));
		}
	}

	//先序 反序列化
	public static Node buildByPreQueue(Queue<String> prelist) {
		if (prelist == null || prelist.size() == 0) {
			return null;
		}
		return preb(prelist);
	}

	public static Node preb(Queue<String> prelist) {
		String value = prelist.poll();
		if (value == null) {
			return null;
		}
		Node head = new Node(Integer.valueOf(value));

		head.left = preb(prelist);
		head.right = preb(prelist);
		return head;
	}

	//后序 反序列化 breeze-todo
	public static Node buildByPosQueue(Queue<String> poslist) {
		if (poslist == null || poslist.size() == 0) {
			return null;
		}
		// 左右中  ->  stack(中右左)
		Stack<String> stack = new Stack<>();
		while (!poslist.isEmpty()) {
			stack.push(poslist.poll());
		}
		return posb(stack);
	}

	public static Node posb(Stack<String> posstack) {
		String value = posstack.pop();
		if (value == null) {
			return null;
		}
		Node head = new Node(Integer.valueOf(value));
		head.right = posb(posstack);
		head.left = posb(posstack);
		return head;
	}

	//按层 序列化，两个queue，一个遍历，一个记录结果
	// 比按层遍历多一个为null也序列化的动作
	public static Queue<String> levelSerial(Node head) {
		Queue<String> ans = new LinkedList<>();
		if (head == null) {
			ans.add(null);
		} else {
			ans.add(String.valueOf(head.value));
			Queue<Node> queue = new LinkedList<Node>();
			queue.add(head);
			while (!queue.isEmpty()) {
				head = queue.poll(); // head 父   子
				//孩子不为空，即序列化，又加队列
				if (head.left != null) {
					ans.add(String.valueOf(head.left.value));
					queue.add(head.left);
				//孩子为空，只加序列化
				} else {
					ans.add(null);
				}
				if (head.right != null) {
					ans.add(String.valueOf(head.right.value));
					queue.add(head.right);
				} else {	
					ans.add(null);
				}
			}
		}
		return ans;
	}

	//按层 反序列化
	public static Node buildByLevelQueue(Queue<String> levelList) {
		if (levelList == null || levelList.size() == 0) {
			return null;
		}
		Node head = generateNode(levelList.poll());
		Queue<Node> queue = new LinkedList<Node>();
		if (head != null) {
			queue.add(head);
		}
		Node node = null;
		while (!queue.isEmpty()) {
			node = queue.poll();
			node.left = generateNode(levelList.poll());
			node.right = generateNode(levelList.poll());
			if (node.left != null) {
				queue.add(node.left);
			}
			if (node.right != null) {
				queue.add(node.right);
			}
		}
		return head;
	}

	public static Node generateNode(String val) {
		if (val == null) {
			return null;
		}
		return new Node(Integer.valueOf(val));
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

	// for test
	public static boolean isSameValueStructure(Node head1, Node head2) {
		if (head1 == null && head2 != null) {
			return false;
		}
		if (head1 != null && head2 == null) {
			return false;
		}
		if (head1 == null && head2 == null) {
			return true;
		}
		if (head1.value != head2.value) {
			return false;
		}
		return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
	}

	// for test
	public static void printTree(Node head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}

	public static void printInOrder(Node head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.right, height + 1, "v", len);
		String val = to + head.value + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.left, height + 1, "^", len);
	}

	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}



	///////////////////////////
	//按层
	public static Queue<String> levelSerialMy(Node head){
		Queue<String> ans = new LinkedList<>();
		Queue<Node> queue = new LinkedList<>();
		if(head == null){
			ans.add(null);
			return ans;
		}
		queue.add(head);
		while (!queue.isEmpty()){
			Node node = queue.poll();
			if(node == null){ // !!! 【int 没有null,不用node.value==null】
				ans.add(null);
				continue; // !!! 【null点没有左右子节点】
			}else {
				ans.add(String.valueOf(node.value));
			}


			if(node.left != null){
				queue.add(node.left);
			}else {
				queue.add(null);
			}
			if(node.right != null){
				queue.add(node.right);
			}else {
				queue.add(null);
			}
		}

		return ans;
	}

	//先序遍历
	public static Queue<String > preSerialMy(Node head){
		Queue<String> ans= new LinkedList<>();
		preProcess(head, ans);
		return ans;
	}
	private static  void preProcess(Node node, Queue<String> ans){
		if(node == null){
			ans.add(null);
			return;
		}
		ans.add(String.valueOf(node.value)); // !!! node.value 不是node
		preProcess(node.left, ans);
		preProcess(node.right, ans);
	}

	//后序遍历
	public static Queue<String > posSerialMy(Node head){
		Queue<String> ans= new LinkedList<>();
		posProcess(head, ans);
		return ans;
	}
	private static  void posProcess(Node node, Queue<String> ans){
		if(node == null){
			ans.add(null);
			return;
		}
		posProcess(node.left, ans);
		posProcess(node.right, ans);
		ans.add(String.valueOf(node.value)); // !!! node.value 不是node

	}



	public static Node buildByPreQueueMy(Queue<String> preList) {
		if(preList == null) {
			return null;
		}
		return prebProcess(preList);
	}
	public static Node prebProcess(Queue<String > list){
		String value = list.poll();
		if(value == null){
			return null;
		}
		Node node = new Node(Integer.valueOf(value));
		node.left = prebProcess(list);
		node.right = prebProcess(list);
		return node;
	}



	public static Node buildByPosQueueMy(Queue<String> postList) {
		if(postList == null) {
			return null;
		}
		return posProcess(postList);
	}
	public static Node posProcess(Queue<String > list){
		String value = list.poll();
		if(value == null){
			return null;
		}
		Node node  = new Node(0);
		node.left = posProcess(list);
		node.right = posProcess(list);
//		 node = new Node(Integer.valueOf(value));
		node.value = Integer.valueOf(value);

		return node;
	}

	///////////////////////////////////

	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			Queue<String> pre = preSerial(head);
			Queue<String> pos = posSerial(head);
			Queue<String> level = levelSerial(head);

//			Queue<String> level = levelSerialMy(head);
//			Queue<String> pre = preSerialMy(head);
//			Queue<String> pos = posSerialMy(head);


			Node preBuild = buildByPreQueue(pre);
//			Node posBuild = buildByPosQueue(pos);
			Node levelBuild = buildByLevelQueue(level);


//			Node preBuild = buildByPreQueueMy(pre);
			Node posBuild = buildByPosQueueMy(pos);
//			Node levelBuild = buildByLevelQueue(level);
			if (!isSameValueStructure(preBuild, posBuild) || !isSameValueStructure(posBuild, levelBuild)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish!");
		
	}
}
