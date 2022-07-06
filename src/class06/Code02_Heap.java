package class06;

import java.util.Comparator;
import java.util.PriorityQueue;

// ** 堆 **
// 重要！！ ！
// [树的概念]
//	1）树：树是n个结点的有限集合，有且仅有一个根结点，其余结点可分为m个根结点的子树。
//	2）二叉树：二叉树是每个节点最多拥有两个子节点，左子树和右子树是有顺序的不能任意颠倒。
//	3）满二叉树：高度为h，由2^h-1个节点构成的二叉树称为满二叉树。
//	4）完全二叉树：完全二叉树是由满二叉树而引出来的，若设二叉树的深度为h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数(即1~h-1层为一个满二叉树)，第 h 层所有的结点都连续集中在最左边，这就是完全二叉树。

//[堆的概念]
//	1）堆结构就是用数组实现的完全二叉树结构
//	2）完全二叉树中如果每棵子树的最大值都在顶部就是大根堆
//	3）完全二叉树中如果每棵子树的最小值都在顶部就是小根堆
//	4）堆结构的heapInsert与heapify操作
//	5）堆结构的增大和减少
//	6）PriorityQueue优先级队列结构，就是堆结构


public class Code02_Heap {

	public static class MyMaxHeap {
		private int[] heap;
		private final int limit;
		private int heapSize;

		public MyMaxHeap(int limit) {
			heap = new int[limit];
			this.limit = limit;
			heapSize = 0;
		}

		public boolean isEmpty() {
			return heapSize == 0;
		}

		public boolean isFull() {
			return heapSize == limit;
		}

		public void printInner() {
			for (int i = 0; i < heap.length; i++) {
				System.out.println(heap[i]);
			}
		}

		public void push(int value) {
			if (heapSize == limit) {
				throw new RuntimeException("heap is full");
			}
			heap[heapSize] = value;
			// value heapSize
			heapInsert(heap, heapSize++);
		}

		// 用户此时，让你返回最大值，并且在大根堆中，把最大值删掉
		// 剩下的数，依然保持大根堆组织
		public int pop() {
			int ans = heap[0];
			//将最后一个值直接和顶点交换,堆减少1
			swap(heap, 0, --heapSize);
			//然后新顶点和子节点比较，小的向下沉
			heapify(heap, 0, heapSize);
			return ans;
		}

		// 新加进来的数，现在停在了index位置，请依次往上移动，
		// 移动到0位置，或者干不掉自己的父亲了，停！
		private void heapInsert(int[] arr, int index) {
//			System.out.println((0 - 1) / 2); // 0
//			System.out.println(arr[(0 - 1) / 2]); // arr[0]
//			// 包含两个条件：arr[index] > arr[index-1]/2 或者index == 0
			while (arr[index] > arr[(index - 1) / 2]) {
				swap(arr, index, (index - 1) / 2);
				index = (index - 1) / 2;
			}
		}

		//
		// 从index位置，往下看，不断的下沉
		// 停：较大的孩子都不再比index位置的数大；已经没孩子了
		private void heapify(int[] arr, int index, int heapSize) {
			int left = index * 2 + 1;
			while (left < heapSize) { // 如果有左孩子，有没有右孩子，可能有可能没有！
				// 把较大孩子的下标，给largest
				int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
				largest = arr[largest] > arr[index] ? largest : index;
				if (largest == index) {
					break;
				}
				// index和较大孩子，要互换
				swap(arr, largest, index);
				index = largest;
				left = index * 2 + 1;
			}
		}


		private static void swap(int[] arr, int i, int j) {
			int tmp = arr[i];
			arr[i] = arr[j];
			arr[j] = tmp;
		}
	}

	public static class MyMaxHeapMy{
		public  void heapInsert(int[] arr, int index) {
			int fatherIndex;
			while ((fatherIndex = (index - 1) >> 1) >= 0 && arr[index] > arr[fatherIndex]) {
				swap(arr, index, fatherIndex);
				index = fatherIndex;
			}
		}
		public static void heapify(int[] arr, int index, int heapSize) {
			int childLeft ;
			// !!! 【不是<=】
			while ((childLeft = index * 2 + 1) < heapSize){
				int maxChild = childLeft + 1 < heapSize && arr[childLeft + 1] > arr[childLeft] ? childLeft + 1 : childLeft;

				if(arr[index] < arr[maxChild]) {
					swap(arr, index, maxChild);
					index = maxChild; // !!! 【不是index = childLeft】
				}else {
					return;
				}
			}
		}
		private static void swap(int[] arr, int i, int j) {
			int tmp = arr[i];
			arr[i] = arr[j];
			arr[j] = tmp;
		}
	}


		public static class RightMaxHeap {
			private int[] arr;
			private final int limit;
			private int size;

			public RightMaxHeap(int limit) {
				arr = new int[limit];
				this.limit = limit;
				size = 0;
			}

			public boolean isEmpty() {
				return size == 0;
			}

			public boolean isFull() {
				return size == limit;
			}

			public void push(int value) {
				if (size == limit) {
					throw new RuntimeException("heap is full");
				}
				arr[size++] = value;
			}

			public int pop() {
				int maxIndex = 0;
				for (int i = 1; i < size; i++) {
					if (arr[i] > arr[maxIndex]) {
						maxIndex = i;
					}
				}
				int ans = arr[maxIndex];
				arr[maxIndex] = arr[--size];
				return ans;
			}

		}

		public static class MyComparator implements Comparator<Integer> {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}

		}

		public static void main(String[] args) {

			// 小根堆
			PriorityQueue<Integer> heap = new PriorityQueue<>(new MyComparator());
			heap.add(5);
			heap.add(5);
			heap.add(5);
			heap.add(3);
			// 5 , 3
			System.out.println(heap.peek());
			heap.add(7);
			heap.add(0);
			heap.add(7);
			heap.add(0);
			heap.add(7);
			heap.add(0);
			System.out.println(heap.peek());
			while (!heap.isEmpty()) {
				System.out.println(heap.poll());
			}

			int value = 1000;
			int limit = 100;
//		int testTimes = 1000000;
			int testTimes = 100;
			for (int i = 0; i < testTimes; i++) {
				int curLimit = (int) (Math.random() * limit) + 1;
				MyMaxHeap my = new MyMaxHeap(curLimit);
//			MyMaxHeapMy my = new MyMaxHeapMy(curLimit);
				RightMaxHeap test = new RightMaxHeap(curLimit);
				int curOpTimes = (int) (Math.random() * limit);
				for (int j = 0; j < curOpTimes; j++) {
					if (my.isEmpty() != test.isEmpty()) {
						System.out.println("Oops1!");
						return;
					}
					if (my.isFull() != test.isFull()) {
						System.out.println("Oops2!");
						return;
					}
					if (my.isEmpty()) {
						int curValue = (int) (Math.random() * value);
						my.push(curValue);
						test.push(curValue);
					} else if (my.isFull()) {
						if (my.pop() != test.pop()) {
							System.out.println("Oops3!");
							return;
						}
					} else {
						if (Math.random() < 0.5) {
							int curValue = (int) (Math.random() * value);
							my.push(curValue);
							test.push(curValue);
						} else {
							if (my.pop() != test.pop()) {
								System.out.println("Oops4!");
								return;
							}
						}
					}
				}
			}
			System.out.println("finish!");

		}



}
