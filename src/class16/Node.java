package class16;

import java.util.ArrayList;
// ** 图论 **
//		1）由点的集合和边的集合构成
//		2）虽然存在有向图和无向图的概念，但实际上都可以用有向图来表达
//		3）边上可能带有权值

// 图的结构表示：点

//有向图的节点的入度:是该节点作为图中边的终点的【次数之和】，
//		出度: 是该节点作为图中边的起点的【次数之和】。


public class Node {
	public int value; //节点值
	public int in; //入度
	public int out; //出度
	public ArrayList<Node> nexts; //指向的节点
	public ArrayList<Edge> edges; //相邻的边

	public Node(int value) {
		this.value = value;
		in = 0;
		out = 0;
		nexts = new ArrayList<>();
		edges = new ArrayList<>();
	}
}
