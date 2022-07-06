package class02;

// ** 认识异或运算 **
// 1.如何不用额外变量交换两个数
//
//一、异或运算符
//		在数字逻辑中，逻辑算符异或（exclusive or）是对两个运算元的一种逻辑分析类型，符号为 XOR 或 ⊕（编程语言中常用 ^）。

//		1.如果你用这个办法交换2个指针的内容.那么你要先检查2个指针指向的地址是否相同.不然会导致内容被清0
//		2.速度并不比朴素的中间变量交换快.
//		结论.别这么干

public class Code01_Swap {
	
	public static void main(String[] args) {

		
		
		int a = 16;
		int b = 603;
		
		System.out.println(a);
		System.out.println(b);
		
		
		a = a ^ b;
		b = a ^ b;
		a = a ^ b;
		
		
		System.out.println(a);
		System.out.println(b);
		
		
		
		
		int[] arr = {3,1,100};
		
		int i = 0;
		int j = 0;
		
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
		
		System.out.println(arr[i] + " , " + arr[j]);
		
		
		
		
		
		
		
		
		
		System.out.println(arr[0]);
		System.out.println(arr[2]);
		
		swap(arr, 0, 0);
		
		System.out.println(arr[0]);
		System.out.println(arr[2]);
		
		
		
	}
	
	
	public static void swap (int[] arr, int i, int j) {
		// arr[0] = arr[0] ^ arr[0];
		arr[i]  = arr[i] ^ arr[j];
		arr[j]  = arr[i] ^ arr[j];
		arr[i]  = arr[i] ^ arr[j];
	}
	
	

}
