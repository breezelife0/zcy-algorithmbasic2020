package class07;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
// ** 堆 **
// 4.最大线段重合问题
//给定很多线段，每个线段都有两个数[start，end]
//返回线段最多重合区域中，包含了几条线段(重合区域的长度必须>=1)

// 指令量在10^8内，时间校验可以过，也可以通过此技巧猜测可行的时间复杂度

/**
 * 堆和有序表结合的贪心考题类型，几乎是互联网大厂的第一题的标配题型，因此理解本题，对于你应对大厂笔试有非常大的帮助
 */

public class Code01_CoverMax {

	//暴力遍历
	//O（(max-min)*N）
	public static int maxCover1(int[][] lines) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < lines.length; i++) {
			min = Math.min(min, lines[i][0]);
			max = Math.max(max, lines[i][1]);
		}
		int cover = 0;
		//遍历最小到最大之间的每个点
		for (double p = min + 0.5; p < max; p += 1) {
			int cur = 0;
			//遍历线段的每个点
			for (int i = 0; i < lines.length; i++) {
				//线段包含P点
				if (lines[i][0] < p && lines[i][1] > p) {
					cur++;
				}
			}
			cover = Math.max(cover, cur);
		}
		return cover;
	}

	//时间复杂度：O（N*log2N）
	public static int maxCover2(int[][] m) {
		Line[] lines = new Line[m.length];
		for (int i = 0; i < m.length; i++) {
			lines[i] = new Line(m[i][0], m[i][1]);
		}
		//以线段起始节点从小到大排序
		Arrays.sort(lines, new StartComparator());

		// 小根堆，每一条线段的[结尾数]值，使用默认的
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		int max = 0;
		for (int i = 0; i < lines.length; i++) {
			// lines[i] -> cur 在黑盒中，把<=cur.start 东西都弹出
			// 比较当前线段头，与栈中最小的线段结尾
			while (!heap.isEmpty() && heap.peek() <= lines[i].start) {
				heap.poll(); //无效线段，弹出丢弃
			}
			heap.add(lines[i].end);
			//堆中剩余的线段结尾个数(即线段数)，即为以当前线段头为交集，贯穿当前线段的线段数。
			max = Math.max(max, heap.size());
		}
		return max;
	}

	//定义一个线段对象
	public static class Line {
		public int start;
		public int end;

		public Line(int s, int e) {
			start = s;
			end = e;
		}
	}

	public static class EndComparator implements Comparator<Line> {

		@Override
		public int compare(Line o1, Line o2) {
			return o1.end - o2.end;
		}

	}

	// 和maxCover2过程是一样的
	// 只是代码更短
	// 不使用类定义的写法
	// lamda匿名函数
	public static int maxCover3(int[][] m) {
		// m是二维数组，可以认为m内部是一个一个的一维数组
		// 每一个一维数组就是一个对象，也就是线段
		// 如下的code，就是根据每一个线段的开始位置排序
		// 比如, m = { {5,7}, {1,4}, {2,6} } 跑完如下的code之后变成：{ {1,4}, {2,6}, {5,7} }
		Arrays.sort(m, (a, b) -> (a[0] - b[0]));
		// 准备好小根堆，和课堂的说法一样
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		int max = 0;
		for (int[] line : m) {
			while (!heap.isEmpty() && heap.peek() <= line[0]) {
				heap.poll();
			}
			heap.add(line[1]);
			max = Math.max(max, heap.size());
		}
		return max;
	}


	/**
	 * maxCover4
	 * https://blog.csdn.net/weixin_46838716/article/details/124930008
	 */
	public static int mostNumCoverLine(int[][] arr){
		if (arr == null || arr.length == 0) return 0;

		//（1）将线段整合为Line数据结构lines
		int N = arr.length;
		Line[] lines = new Line[N];
		for (int i = 0; i < N; i++) {
			lines[i] = new Line(arr[i][0], arr[i][1]);//变统一的线段数据结构
		}
		//（2）将lines **按照每条线段的start升序排序**
		Arrays.sort(lines, new startReviewComparator());

		//（3）准备一个小根堆heap，排序方式是**线段的end降序排列**
		PriorityQueue<Line> heap = new PriorityQueue<>(new endReviewComparator());

		int max = 0;
		//（4）遍历lines的每一条线i，看看有多少条线会影响我，跟我重合，更新max
		for (int i = 0; i < N; i++) {
			Line cur = lines[i];//当前线段
			//具体咋操作呢？就是让**小根堆中end<=line[i].start的那些线段，弹出去，他们不会跟我重合的**
			while (!heap.isEmpty() && heap.peek().end <= cur.start) {
				heap.poll();
			}
			//然后把我line[i]加入小根堆，此时能影响我line[i]的线段们都留在小根堆中了，
			heap.add(cur);
			// 小根堆的size就是重合数量，更新给max
			max = Math.max(max, heap.size());
		}
		//（5）所有线段操作完，结果max已经得到了最大值。

		return max;
	}

	//线段cur按照start排序升序
	public static class startReviewComparator implements Comparator<Line>{
		@Override
		public int compare(Line o1, Line o2){
			return o1.start - o2.start;//返回-1，o1放前面
		}
	}

	//线段cur按照end升序降序，小根堆的比较器
	public static class endReviewComparator implements Comparator<Line>{
		@Override
		public int compare(Line o1, Line o2){
			return o1.end - o2.end;//返回-1，o1放上面
		}
	}




	// for test
	public static int[][] generateLines(int N, int L, int R) {
		int size = (int) (Math.random() * N) + 1;
		int[][] ans = new int[size][2];
		for (int i = 0; i < size; i++) {
			int a = L + (int) (Math.random() * (R - L + 1));
			int b = L + (int) (Math.random() * (R - L + 1));
			if (a == b) {
				b = a + 1;
			}
			ans[i][0] = Math.min(a, b);
			ans[i][1] = Math.max(a, b);
		}
		return ans;
	}

   //按线段开始位置，从小到大排序
	public static class StartComparator implements Comparator<Line> {

		@Override
		public int compare(Line o1, Line o2) {
			return o1.start - o2.start;
		}

	}

	public static void main(String[] args) {

		Line l1 = new Line(4, 9);
		Line l2 = new Line(1, 4);
		Line l3 = new Line(7, 15);
		Line l4 = new Line(2, 4);
		Line l5 = new Line(4, 6);
		Line l6 = new Line(3, 7);

		// 底层堆结构，heap
		PriorityQueue<Line> heap = new PriorityQueue<>(new StartComparator());
		heap.add(l1);
		heap.add(l2);
		heap.add(l3);
		heap.add(l4);
		heap.add(l5);
		heap.add(l6);

		while (!heap.isEmpty()) {
			Line cur = heap.poll();
			System.out.println(cur.start + "," + cur.end);
		}

		System.out.println("test begin");
		int N = 100;
		int L = 0;
		int R = 200;
		int testTimes = 200000;
		for (int i = 0; i < testTimes; i++) {
			int[][] lines = generateLines(N, L, R);
			int ans1 = maxCover1(lines);
			int ans2 = maxCover2(lines);
			int ans3 = maxCover3(lines);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test end");
	}

}
