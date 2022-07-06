package class16;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
// ** 图论 **
//图的面试题如何搞定
//		图的算法都不算难，只不过coding的代价比较高
//		1）先用自己最熟练的方式，实现图结构的表达
//		2）在自己熟悉的结构上，实现所有常用的图算法作为模板
//		3）把面试题提供的图结构转化为自己熟悉的图结构，再调用模板或改写即可

//		图结构的表达
//		1）邻接表法
//		2）邻接矩阵法
//		3）除此之外还有其他众多的方式
// ZCY套路： 定义边，点，图

//宽度优先遍历BFS:	(Breadth First Search)
//		1，利用队列实现
//		2，从源节点开始依次按照宽度进队列，然后弹出
//		3，每弹出一个点，把该节点所有没有进过队列的邻接点放入队列
//		4，直到队列变空


public class Code01_BFS {

	// 从node出发，进行宽度优先遍历
	public static void bfs(Node start) {
		if (start == null) {
			return;
		}
		Queue<Node> queue = new LinkedList<>();
		HashSet<Node> set = new HashSet<>();  //使用set去重
		queue.add(start);
		set.add(start);
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			System.out.println(cur.value);
			for (Node next : cur.nexts) {
				if (!set.contains(next)) {
					set.add(next);
					queue.add(next);
				}
			}
		}
	}

}
