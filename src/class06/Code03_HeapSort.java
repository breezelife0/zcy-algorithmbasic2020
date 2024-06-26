package class06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
// ** 堆 **
// 3. 堆排序
// 1.先把数组调成【大根堆】，2.再从堆定拿最大值,放在arr[heapSize]，然后恢复堆结构 3.heapSize--,重复2
// 参考： https://blog.csdn.net/q925092273/article/details/109462468

/**
 * 最大堆：父节点永远比子节点大。
 * 最小堆：父节点永远比子节点小。
 * (左右孩子没有大小关系)
 *
 * 如果把一个数组排成完全二叉树，则是下标循环时，是从上到下，从左到右,进行填充
 *
 * i的左子节点下标：2*i+1 ,如果越界，证明左孩子不存在
 * i的右子节点下标：2*i+2，如果越界，证明右孩子不存在
 * i的父节点下标：(i-1)/2,注意下标0位置求父节点，(0-1)/2 = 0
 * 第一个非叶子结点 arr.length/2-1
 */
public class Code03_HeapSort {
    // 堆排序额外空间复杂度O(1)
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // O(N*logN)
//		for (int i = 0; i < arr.length; i++) { // O(N)
//			heapInsert(arr, i); // O(logN)
//		}


        // O(N)
//        for (int i = arr.length - 1; i >= 0; i--) {
//            heapify(arr, i, arr.length);
//
//        }
		/**
		 * 优化，从第一个非叶子节点开始
		 */
		for (int i = arr.length/2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        int heapSize = arr.length;
        swap(arr, 0, --heapSize);
        // O(N*logN)
        while (heapSize > 0) { // O(N)
            heapify(arr, 0, heapSize); // O(logN)
            swap(arr, 0, --heapSize); // O(1)
        }
    }



	// arr[index]刚来的数，往上
//	public static void heapInsert(int[] arr, int index) {
//		while (arr[index] > arr[(index - 1) / 2]) {
//			swap(arr, index, (index - 1) / 2);
//			index = (index - 1) / 2;
//		}
//	}

	public static void heapInsert(int[] arr, int index) {
		int fatherIndex ;
		while ((fatherIndex = (index - 1)>>1)  > 0 && arr[index] > arr[fatherIndex] ) {
			swap(arr, index, fatherIndex);
			index = fatherIndex;
		}
	}
	// arr[index]位置的数，能否往下移动
	public static void heapify(int[] arr, int index, int heapSize) {
		int left = index * 2 + 1; // 左孩子的下标
		while (left < heapSize) { // 下方还有孩子的时候
			// 两个孩子中，谁的值大，把下标给largest
			// 1）只有左孩子，left -> largest
			// 2) 同时有左孩子和右孩子，右孩子的值<= 左孩子的值，left -> largest
			// 3) 同时有左孩子和右孩子并且右孩子的值> 左孩子的值， right -> largest
			int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
			// 父和较大的孩子之间，谁的值大，把下标给largest
			largest = arr[largest] > arr[index] ? largest : index;
			if (largest == index) {
				break;
			}
			swap(arr, largest, index);
			index = largest;
			left = index * 2 + 1;
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// for test
	public static void comparator(int[] arr) {
		Arrays.sort(arr);
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main0(String[] args) {
		// 默认小根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		heap.add(6);
		heap.add(8);
		heap.add(0);
		heap.add(2);
		heap.add(9);
		heap.add(1);

		while (!heap.isEmpty()) {
			System.out.println(heap.poll());
		}


		int[]arr =new int[5];
		arr[0] = 3;
		arr[1] = 2;
		arr[2] = 5;
		arr[3] = 1;
		arr[4] = 4;

		printArray(arr);
		heapSort(arr);
		printArray(arr);
	}
	// for test
	public static void main(String[] args) {


		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			System.out.println(i);
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			heapSort(arr1);
//			heapSortMy(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

//		int[] arr = generateRandomArray(maxSize, maxValue);
//		printArray(arr);
//		heapSort(arr);
//		printArray(arr);
	}

    public static class ComparatorMy implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2){
            return o2 - o1;
        }
    }

    // for test
//    public static void main1(String[] args) {
//        int testTime = 1;
//        int maxSize = 10;
//        int maxValue = 10;
//        for (int i = 0; i < testTime; i++) {
//            int[] arr = generateRandomArray(maxSize, maxValue);
//            int[] arr1 = copyArray(arr);
//
////            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Code03_HeapSort.ComparatorMy());
//            Code02_Heap.MyMaxHeap myMaxHeap = new Code02_Heap.MyMaxHeap(arr.length);
//            for (int j = 0; j < arr.length; j++) {
////                priorityQueue.add(arr[j]);
//                myMaxHeap.push(arr[j]);
//            }
//            myMaxHeap.printInner();
//            //===
//            for (int jj = arr1.length - 1; jj >= 0; jj--) {
//                heapify(arr1, jj, arr1.length);
//            }
//            printArray(arr1);
//
//
//        }
//    }

}
