package class14;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
// ** 图论 **
// 并查集-连通性的问题都可以利用并查集

//　在计算机科学中，并查集是一种树型的数据结构，用于处理一些不交集（Disjoint Sets）的合并及查询问题。有一个联合-查找算法（union-find algorithm）定义了两个用于此数据结构的操作：
//		由于支持这两种操作，一个不相交集也常被称为联合-查找数据结构（union-find data structure）或合并-查找集合（merge-find set）。其他的重要方法，MakeSet，用于建立单元素集合。有了这些方法，许多经典的划分问题可以被解决。
//		1）每个节点都有一条往上指的指针
//		2）节点a往上找到的头节点，叫做a所在集合的代表节点
//		3）查询x和y是否属于同一个集合，就是看看找到的代表节点是不是一个
//		4）把x和y各自所在集合的所有点合并成一个集合，只需要小集合的代表点挂在大集合的代表点的下方即可


//有若干个样本a、b、c、d…类型假设是V
//		在并查集中一开始认为每个样本都在单独的集合里
//		用户可以在任何时候调用如下两个方法：
//		boolean isSameSet(V x, V y) : 查询样本x和样本y是否属于一个集合
//		void union(V x, V y) : 把x和y各自所在集合的所有样本合并成一个集合
//		4） isSameSet和union方法的代价越低越好


//
//并查集的优化
//		1）节点往上找代表点的过程，把沿途的链变成扁平的
//		2）小集合挂在大集合的下面
//		3）如果方法调用很频繁，那么单次调用的代价为O(1)，两个方法都如此

//并查集的应用
//		解决两大块区域的合并问题
//		常用在图等领域中


public class Code05_UnionFind {

	public static class Node<V> {
		V value;

		public Node(V v) {
			value = v;
		}
	}

	public static class UnionFind<V> {
		public HashMap<V, Node<V>> nodes;
		public HashMap<Node<V>, Node<V>> parents;
		public HashMap<Node<V>, Integer> sizeMap;

		public UnionFind(List<V> values) {
			nodes = new HashMap<>();
			parents = new HashMap<>();
			sizeMap = new HashMap<>();
			for (V cur : values) {
				Node<V> node = new Node<>(cur);
				nodes.put(cur, node);
				parents.put(node, node);
				sizeMap.put(node, 1);
			}
		}

		// 给你一个节点，请你往上到不能再往上，把代表返回
		public Node<V> findFather(Node<V> cur) {
			Stack<Node<V>> path = new Stack<>();
			while (cur != parents.get(cur)) {
				path.push(cur);
				cur = parents.get(cur);
			}
			//扁平化，将孙子的父都改成当前的代表点->优化点1，降低树高，快速找到父
			while (!path.isEmpty()) {
				parents.put(path.pop(), cur);
			}
			return cur;
		}
+
		// 调用频繁，时间复杂度0(1)
		public boolean isSameSet(V a, V b) {
			return findFather(nodes.get(a)) == findFather(nodes.get(b));
		}

		// 调用频繁，时间复杂度0(1)
		public void union(V a, V b) {
			Node<V> aHead = findFather(nodes.get(a));
			Node<V> bHead = findFather(nodes.get(b));
			if (aHead != bHead) {
				//优化点2：小挂大，减少复杂度
				int aSetSize = sizeMap.get(aHead);
				int bSetSize = sizeMap.get(bHead);
				Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
				Node<V> small = big == aHead ? bHead : aHead;
				parents.put(small, big);
				sizeMap.put(big, aSetSize + bSetSize);
				sizeMap.remove(small);
			}
		}

		public int sets() {
			return sizeMap.size();
		}

	}
}
