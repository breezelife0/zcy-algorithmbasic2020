package class05;
// ** 快速排序 **
// 2.快排3个版本
// Partition过程 ：给定一个数组arr，和一个整数num。请把小于等于num的数放在数组的左边，大于num的数放在数组的右边。(左边或右边不需要有序)，要求额外空间复杂度O(1)，时间复杂度O(N)
// 荷兰国旗：包含等于区

//[时间复杂度]
// 快速排序1.0和2.0：数组已经有序的时候就是复杂度最高的时候，时间复杂度O(N^2)
// 快速排序3.0：
//		1）通过分析知道，划分值越靠近中间，性能越好；越靠近两边，性能越差
//		2）随机选一个数进行划分的目的就是让好情况和差情况都变成概率事件
//		3）把每一种情况都列出来，会有每种情况下的时间复杂度，但概率都是1/N
//		4）那么所有情况都考虑，时间复杂度就是这种概率模型下的长期期望！
//		时间复杂度O(N*logN)，额外空间复杂度O(logN)都是这么来的。


public class Code02_PartitionAndQuickSort {

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// arr[L..R]上，以arr[R]位置的数做划分值 （partition即分层）
	//partition返回值，即分界的坐标（小于区域的最后一个下标）
	public static int partition(int[] arr, int L, int R) {
		if (L > R) {
			return -1;
		}
		if (L == R) {
			return L;
		}
		int lessEqual = L - 1;
		int index = L;
		//arr[R]作为参照值，分层
		while (index < R) {
			if (arr[index] <= arr[R]) {
				swap(arr, index, ++lessEqual);
			}
			index++;
		}
		//完成后，将末位R值，放到小于区域的下一个(互换)
		swap(arr, ++lessEqual, R);
		return lessEqual;
	}

	// arr[L...R] 玩荷兰国旗问题的划分，以arr[R]做划分值
	// <arr[R] ==arr[R] > arr[R]
	// 荷兰国旗算法，比上面快排1.0的partition多一个等于区域(等于区间)，因此方法返回的是一个包含两个元素的数组，指示等于区域的左右边界
	// 因为处理了等于区，所以效率高些
	public static int[] netherlandsFlag(int[] arr, int L, int R) {
		if (L > R) { // L...R L>R
			return new int[] { -1, -1 };
		}
		if (L == R) {
			return new int[] { L, R };
		}
		int less = L - 1; // < 区 右边界
		int more = R; // > 区 左边界
		int index = L;
		while (index < more) { // 当前位置，不能和 >区的左边界撞上
			if (arr[index] == arr[R]) {
				index++;
			} else if (arr[index] < arr[R]) {
//				swap(arr, less + 1, index);
//				less++;
//				index++;						
				swap(arr, index++, ++less);
			} else { // >
				swap(arr, index, --more);
			}
		}
		swap(arr, more, R); // <[R]   =[R]   >[R]
		return new int[] { less + 1, more };
	}

	//---- 快排1-start ---
	public static void quickSort1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process1(arr, 0, arr.length - 1);
	}

	public static void process1(int[] arr, int L, int R) {
		if (L >= R) {
			return;
		}
		// L..R partition arr[R] [ <=arr[R] arr[R] >arr[R] ]
		//先分成两大块，左小右大，相对有序；再递归缩小有序的粒度
		int M = partition(arr, L, R);
		process1(arr, L, M - 1);
		process1(arr, M + 1, R);
	}
	//---- 快排1-end ---



	//---- 快排2-start ---
	public static void quickSort2(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process2(arr, 0, arr.length - 1);
	}

	// arr[L...R] 排有序，快排2.0方式
	public static void process2(int[] arr, int L, int R) {
		if (L >= R) {
			return;
		}
		// [ equalArea[0]  ,  equalArea[0]]
		int[] equalArea = netherlandsFlag(arr, L, R);
		process2(arr, L, equalArea[0] - 1);
		process2(arr, equalArea[1] + 1, R);
	}
	//---- 快排2-end ---


	// 学术上的快排，指的下面的快排3.0：荷兰国旗+随机参考值
	//---- 快排3-start ---
	public static void quickSort3(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process3(arr, 0, arr.length - 1);
	}

	public static void process3(int[] arr, int L, int R) {
		if (L >= R) {
			return;
		}
		swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
		int[] equalArea = netherlandsFlag(arr, L, R);
		process3(arr, L, equalArea[0] - 1);
		process3(arr, equalArea[1] + 1, R);
	}
	//---- 快排3-end ---


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


	// --------------------------- my-start -------------------------

	public static void quickSortMy(int[] arr) {
		if(arr == null  ||  arr.length <2){
			return;
		}
		processMy(arr, 0, arr.length - 1);
	}

	private static void processMy(int[] arr, int L, int R){
		if(L >= R){
			return ;
		}
		int pIndex = partitionMy(arr, L, R);
		processMy(arr, L, pIndex-1);
		processMy(arr,pIndex+1, R);
	}

	private static int partitionMy(int[] arr, int L, int R){
		int lessAndEqual = L;
		for (int i = L; i < R; i++) {
			if(arr[i] < arr[R]){
				swap(arr, i, lessAndEqual ++ );
			}
		}
		swap(arr, lessAndEqual, R);
		return lessAndEqual;
	}




	public static void quickSortMy2(int[] arr) {
		if(arr == null  ||  arr.length <2){
			return;
		}
		processMy2(arr, 0, arr.length - 1);
	}

	private static void processMy2(int[] arr, int L, int R){
		if(L >= R || L<0 || R>arr.length-1){
			return;
		}
		int[] partation = partitionMy2(arr, L, R);
		processMy(arr, L, partation[0]-1 );
		processMy(arr, partation[1]+1, R);
	}

	private static int[] partitionMy2(int[] arr, int L, int R){
		if(L > R){
			return new int[]{-1, -1};
		}
		if(L == R){
			return new int[]{L,L};
		}
		int equalL = L-1;
		int equalR = R;
		int index = L;
		while (index < equalR){
			if(arr[index] == arr[R]){
				index ++;
			}else if(arr[index] < arr[R]){
				swap(arr, index++, ++equalL);
			}else {
				swap(arr, index, --equalR);
			}
		}
		//交换arr[R]
		swap(arr, equalR, R);
		return new int[]{equalL, equalR--};
	}

	// --------------------------- my-end -------------------------

	// for test
	public static void main0(String[] args) {
		int testTime = 500000;
		int maxSize = 3;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			int[] arr3 = copyArray(arr1);
			int[] arr0 = copyArray(arr1);
			quickSort1(arr1);
			quickSort2(arr2);
//			quickSort3(arr3);
//			quickSortMy(arr3);
			quickSortMy2(arr3);
			if (!isEqual(arr1, arr2) || !isEqual(arr2, arr3)) {
				succeed = false;
				printArray(arr0);
				printArray(arr2);
				printArray(arr3);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Oops!");

	}

	public static void main(String[] args) {
		int[] arr = new int[]{-65, -69, -62 };
//		quickSortMy2(arr);
		quickSort2(arr);
	}

}
