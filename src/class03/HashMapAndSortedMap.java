package class03;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
// ** 哈希表和有序表的原理 **
// 9.哈希表和有序表的原理

//哈希表在使用时，增删改查时间复杂度都是O(1)
//有序表在使用时，比哈希表功能多，时间复杂度都是O(logN)
//
//[哈希表]
//		1)哈希表在使用层面上可以理解为一种集合结构
//		2)如果只有key，没有伴随数据value，可以使用HashSet结构
//		3)如果既有key，又有伴随数据value，可以使用HashMap结构
//		4)有无伴随数据，是HashMap和HashSet唯一的区别，实际结构是一回事
//		5)使用哈希表增(put)、删(remove)、改(put)和查(get)的操作，可以认为时间复杂度为 O(1)，但是常数时间比较大
//		6)放入哈希表的东西，如果是基础类型，内部按值传递，内存占用是这个东西的大小
//		7)放入哈希表的东西，如果不是基础类型，内部按引用传递，内存占用是8字节
//[有序表]
//		1)有序表在使用层面上可以理解为一种集合结构
//		2)如果只有key，没有伴随数据value，可以使用TreeSet结构
//		3)如果既有key，又有伴随数据value，可以使用TreeMap结构
//		4)有无伴随数据，是TreeSet和TreeMap唯一的区别，底层的实际结构是一回事
//		5)有序表把key按照顺序组织起来，而哈希表完全不组织
//		6)红黑树、AVL树、size-balance-tree和跳表等都属于有序表结构，只是底层具体实现不同
//		7)放入如果是基础类型，内部按值传递，内存占用就是这个东西的大小
//		8)放入如果不是基础类型，内部按引用传递，内存占用是8字节
//		9)不管是什么底层具体实现，只要是有序表，都有以下固定的基本功能和固定的时间复杂度


public class HashMapAndSortedMap {

	public static class Node {
		public int value;

		public Node(int v) {
			value = v;
		}
	}

	public static class Zuo {
		public int value;

		public Zuo(int v) {
			value = v;
		}
	}

	public static void main(String[] args) {

		HashMap<Integer, String> test = new HashMap<>();
		Integer a = 19000000;
		Integer b = 19000000;
		System.out.println(a == b);

		test.put(a, "我是3");
		System.out.println(test.containsKey(b));

		Zuo z1 = new Zuo(1);
		Zuo z2 = new Zuo(1);
		HashMap<Zuo, String> test2 = new HashMap<>();
		test2.put(z1, "我是z1");
		System.out.println(test2.containsKey(z2));

		// UnSortedMap
		HashMap<Integer, String> map = new HashMap<>();
		map.put(1000000, "我是1000000");
		map.put(2, "我是2");
		map.put(3, "我是3");
		map.put(4, "我是4");
		map.put(5, "我是5");
		map.put(6, "我是6");
		map.put(1000000, "我是1000001");

		System.out.println(map.containsKey(1));
		System.out.println(map.containsKey(10));

		System.out.println(map.get(4));
		System.out.println(map.get(10));

		map.put(4, "他是4");
		System.out.println(map.get(4));

		map.remove(4);
		System.out.println(map.get(4));

		// key
		HashSet<String> set = new HashSet<>();
		set.add("abc");
		set.contains("abc");
		set.remove("abc");

		// 哈希表，增、删、改、查，在使用时，O（1）

		System.out.println("=====================");

		Integer c = 100000;
		Integer d = 100000;
		System.out.println(c.equals(d));

		Integer e = 127; // - 128 ~ 127
		Integer f = 127;
		System.out.println(e == f);

		HashMap<Node, String> map2 = new HashMap<>();
		Node node1 = new Node(1);
		Node node2 = node1;
		map2.put(node1, "我是node1");
		map2.put(node2, "我是node1");
		System.out.println(map2.size());

		System.out.println("======================");

		// TreeMap 有序表：接口名
		// 红黑树、avl、sb树、跳表
		// O(logN)
		System.out.println("有序表测试开始");
		TreeMap<Integer, String> treeMap = new TreeMap<>();

		treeMap.put(3, "我是3");
		treeMap.put(4, "我是4");
		treeMap.put(8, "我是8");
		treeMap.put(5, "我是5");
		treeMap.put(7, "我是7");
		treeMap.put(1, "我是1");
		treeMap.put(2, "我是2");

		System.out.println(treeMap.containsKey(1));
		System.out.println(treeMap.containsKey(10));

		System.out.println(treeMap.get(4));
		System.out.println(treeMap.get(10));

		treeMap.put(4, "他是4");
		System.out.println(treeMap.get(4));

		// treeMap.remove(4);
		System.out.println(treeMap.get(4));

		System.out.println("新鲜：");

		System.out.println(treeMap.firstKey());
		System.out.println(treeMap.lastKey());
		// <= 4
		System.out.println(treeMap.floorKey(4));
		// >= 4
		System.out.println(treeMap.ceilingKey(4));
		// O(logN)

	}

}
