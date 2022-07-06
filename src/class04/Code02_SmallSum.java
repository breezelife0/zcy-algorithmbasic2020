package class04;
// ** 递归-归并排序 **
// 2.[数组小和（单调和）]
//		数组小和的定义如下：例如，数组s=[1,3,5,2,4,6]
//		在s[0]的左边小于或等于s[0]的数的和为0
//		在s[1]的左边小于或等于s[1]的数的和为1
//		在s[2]的左边小于或等于s[2]的数的和为1+3=4
//		在s[3]的左边小于或等于s[3]的数的和为1
//		在s[4]的左边小于或等于s[4]的数的和为1+3+2=6
//		在s[5]的左边小于或等于s[5]的数的和为1+3+5+2+4=15
//		所以s的小和为0+1+4+1+6+15=27
//		给定一个数组s，实现函数返回s的小和。

//思路：左侧小->右侧比当前值大的个数 当前值*个数
//利用归并排序加工

// 暴力遍历累加O(N^2)没分；使用归并排序:O(N*logN)
public class Code02_SmallSum {

	public static int smallSum(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		return process(arr, 0, arr.length - 1);
	}

	// arr[L..R]既要排好序，也要求小和返回
	// 所有merge时，产生的小和，累加
	// 左 排序   merge
	// 右 排序  merge
	// merge
	public static int process(int[] arr, int l, int r) {
		//只有一个点时返回小和=0
		if (l == r) {
			return 0;
		}
		// l < r
		int mid = l + ((r - l) >> 1);


//		return
//				process(arr, l, mid)
//				+
//				process(arr, mid + 1, r)
//				+
//				merge(arr, l, mid, r);

		//方便debug看结果
		//将数组部分排列有序，且返回小和
		 int left = process(arr, l, mid) ;
		 int right = process(arr, mid + 1, r);
		 int merge=	merge(arr, l, mid, r);

		 return left + right + merge;
	}

	public static int merge(int[] arr, int L, int m, int r) {
		int[] help = new int[r - L + 1];
		int i = 0;
		int p1 = L;
		int p2 = m + 1;
		int res = 0;
		while (p1 <= m && p2 <= r) {
			//【在次统计】
			res += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] : 0;
			help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
		}
		while (p1 <= m) {
			help[i++] = arr[p1++];
		}
		while (p2 <= r) {
			help[i++] = arr[p2++];
		}
		for (i = 0; i < help.length; i++) {
			arr[L + i] = help[i];
		}
		return res;
	}

	// for test
	public static int comparator(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int res = 0;
		for (int i = 1; i < arr.length; i++) {
			for (int j = 0; j < i; j++) {
				res += arr[j] < arr[i] ? arr[j] : 0;
			}
		}
		return res;
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

	// for test
	public static void main0(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			if (smallSum(arr1) != comparator(arr2)) {
				succeed = false;
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}

	public static void main(String[] args) {
		int testTime = 5;
		int maxSize = 10;
		int maxValue = 10;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			System.out.println(smallSum(arr1));
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}
}
