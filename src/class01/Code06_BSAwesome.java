package class01;
// ** 二分查找 **
// 获取一个局部最小值

//题意是指当a[i]的前后两个元素都存在时，需要满足“a[i] < a[i-1]，且a[i] < a[i+1]”这个条件，
//但是如果a[i]是第一个元素或者是最后一个元素，那么只需要看一边。
//所以对于任何一个数组，”局部最小元素“一定是存在的，
//题意要求找一个就行，于是就用二分了。

public class Code06_BSAwesome {

	// 课上的代码
	public static int getLessIndex(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		if (arr.length == 1 || arr[0] < arr[1]) {
			return 0;
		}
		if (arr[arr.length - 1] < arr[arr.length - 2]) {
			return arr.length - 1;
		}
		int left = 1;
		int right = arr.length - 2;
		int mid = 0;
		while (left < right) {
			mid = (left + right) / 2;
			if (arr[mid] > arr[mid - 1]) {
				right = mid - 1;
			} else if (arr[mid] > arr[mid + 1]) {
				left = mid + 1;
			} else {
				return mid;
			}
		}
		return left;
	}

	public static int getLessIndexMy(int[] arr) {
		return  -1;
	}


	// 验证得到的结果，是不是局部最小
	public static boolean isRight(int[] arr, int index) {
		if (arr.length <= 1) {
			return true;
		}
		if (index == 0) {
			return arr[index] < arr[index + 1];
		}
		if (index == arr.length - 1) {
			return arr[index] < arr[index - 1];
		}
		return arr[index] < arr[index - 1] && arr[index] < arr[index + 1];
	}

	// 为了测试
	// 生成相邻不相等的数组
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) (Math.random() * maxSize) + 1];
		arr[0] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
		for (int i = 1; i < arr.length; i++) {
			do {
				arr[i] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
			} while (arr[i] == arr[i - 1]);
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 30;
		int maxValue = 100;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int ans = getLessIndexMy(arr);
			if (!isRight(arr, ans)) {
				System.out.println("出错了！");
				break;
			}
		}
		System.out.println("测试结束");
	}

}
