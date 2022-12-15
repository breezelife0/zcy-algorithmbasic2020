package class02;

// ** 认识异或运算 **
// 2. 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
// https://blog.csdn.net/qq_52880445/article/details/122846587
//思路
//
//		我们先整体异或运算，这样子得要的是一个数是a ^ b,这里的ab是指为奇数次的这两个数。
//		然后我们提取eor中最右边的值，因为这个值就代表了a和b不同的那一位。（固定写法）
//		我们通过这个数字就可以将a和b分开，然后得要a或者b
//		最后通过得到的数和eor进行异或运算得到另外一位。

public class Code02_EvenTimesOddTimes {

	// arr中，只有一种数，出现奇数次
	public static void printOddTimesNum1(int[] arr) {
		int eor = 0;
		for (int i = 0; i < arr.length; i++) {
			eor ^= arr[i];
		}
		System.out.println(eor);
	}

	/**
	 * breeze:好像也行
	 * @param arr
	 */
	public static void printOddTimesNum1_breeze(int[] arr) {
		int eor = arr[0];
		for (int i = 1; i < arr.length; i++) {
			eor ^= arr[i];
		}
		System.out.println(eor);
	}

	// arr中，有两种数，出现奇数次
	public static void printOddTimesNum2(int[] arr) {
		int eor = 0;
		for (int i = 0; i < arr.length; i++) {
			eor ^= arr[i];
		}
		// a 和 b是两种数
		// eor != 0
		// eor最右侧的1，提取出来
		// eor :     00110010110111000
		// rightOne :00000000000001000
		int rightOne = eor & (-eor); // 提取出最右的1
		
		
		int onlyOne = 0; // eor'
		for (int i = 0 ; i < arr.length;i++) {
			//  arr[1] =  111100011110000
			// rightOne=  000000000010000
			if ((arr[i] & rightOne) != 0) {
				onlyOne ^= arr[i];
			}
		}
		System.out.println(onlyOne + " " + (eor ^ onlyOne));
	}

	
	public static int bit1counts(int N) {
		int count = 0;
		
		//   011011010000
		//   000000010000     1
		
		//   011011000000
		// 
		
		
		
		while(N != 0) {
			int rightOne = N & ((~N) + 1);
			count++;
			N ^= rightOne;
			// N -= rightOne
		}
		
		
		return count;
		
	}
	
	
	public static void main(String[] args) {
//		int[] arr0 = { 3, 3, 3, 8, 3 ,10};
		int[] arr0 = {10, 3, 3, 3, 3 ,10, 3,10,3, 5,5};
		printOddTimesNum1(arr0);
		printOddTimesNum1_breeze(arr0);


		int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
 		printOddTimesNum2(arr2);

	}

}
