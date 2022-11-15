package class14;

import java.util.HashSet;
// ** 贪心 **
// 出现几率：笔试60%用于筛选，5对2进入面试； 面试低于20%，因为没有区分度
//不用纠结贪心的证明，用对数器暴力(或暴力递归)验证

// 1. 放灯
//	题目： 给定一个字符串str，只由‘X’和‘.’两种字符构成。
//		‘X’表示墙，不能放灯，也不需要点亮
//		‘.’表示居民点，可以放灯，需要点亮
//		如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮
//		返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
// xx.x.xxx....x.x.xxxx...x.

public class Code01_Light {

	//test 暴力递归对数器！所有情况都尝试！
	public static int minLight1(String road) {
		if (road == null || road.length() == 0) {
			return 0;
		}
		return process(road.toCharArray(), 0, new HashSet<>());
	}

	// str[index....]位置，自由选择放灯还是不放灯
	// str[0..index-1]位置呢？已经做完决定了，那些放了灯的位置，存在lights里
	// 要求选出能照亮所有.的方案，并且在这些有效的方案中，返回最少需要几个灯
	public static int process(char[] str, int index, HashSet<Integer> lights) {
		// !!!breeze: index =length ,而不是length-1,结算
		if (index == str.length) { // 【base case】,结束的时候,验证收集的方案是否有效

			for (int i = 0; i < str.length; i++) {
				if (str[i] != 'X') { // 当前位置是点的话
					// !!!breeze: 不用关心i=0时是否越界，
					if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
						return Integer.MAX_VALUE;
					}
				}
			}
			return lights.size();
		} else { // str还没结束
			// i X .
			int no = process(str, index + 1, lights);
			int yes = Integer.MAX_VALUE;
			if (str[index] == '.') {
				lights.add(index);
				yes = process(str, index + 1, lights);
				lights.remove(index);  //恢复现场 // !!!breeze:
			}
			return Math.min(no, yes);
		}
	}

	public static int minLight2(String road) {
		char[] str = road.toCharArray();
		int i = 0;
		int light = 0;
		//!!!breeze: 不能用fori i++ !!!
		while (i < str.length) {
			if (str[i] == 'X') {
				i++;
			} else {
				light++;
				if (i + 1 == str.length) {
					break;
				} else { // 有i位置 i+ 1 X .
					if (str[i + 1] == 'X') { // i=. 1=X  nextI=2; 灯放i，覆盖i,1,下个判断i=2
						i = i + 2;
					} else { //  i=. 1=. 2=? nextI=3; 灯放1，覆盖i，1，2，下个判断i=3
						i = i + 3;
					}
				}
			}
		}
		return light;
	}

	public static int minLight2My(String road){
		if(road == null && road.length() == 0){
			return 0;
		}
		HashSet<Integer> lights = new HashSet<>();
		int i =0;
		char[] roads = road.toCharArray();
		while ( i < roads.length){
			if(roads[i] == 'X') {
				i++;

			}else if(roads[i] == '.'){

				if(i+1 == roads.length){

					lights.add(i);
					break;
				}else {
					if(roads[i+1] == '.') {
						lights.add(i+1);
						i += 3;


					}else if(roads[i+1] == 'X'){
						lights.add(i);
						i += 2;
					}
				}

			}
		}
		return lights.size();

	}




	// 更简洁的解法
	// 两个X之间，数一下.的数量，然后除以3，向上取整
	// 把灯数累加
	public static int minLight3(String road) {
		char[] str = road.toCharArray();
		int cur = 0;
		int light = 0;
		for (char c : str) {
			if (c == 'X') {
				light += (cur + 2) / 3;
				cur = 0;
			} else {
				cur++;
			}
		}
		light += (cur + 2) / 3;
		return light;
	}

	// for test
	public static String randomString(int len) {
		char[] res = new char[(int) (Math.random() * len) + 1];
		for (int i = 0; i < res.length; i++) {
			res[i] = Math.random() < 0.5 ? 'X' : '.';
		}
		return String.valueOf(res);
	}



	public static int minLight1My(String road){
		 return 0;

	}
	public static int processMy(char[] roads, int index, HashSet<Integer> lights){
		return 0;
	}

	public static void main(String[] args) {
		int len = 20;
		int testTime = 100000;
		for (int i = 0; i < testTime; i++) {
			String test = randomString(len);
			int ans1 = minLight1(test);
			int ans2 = minLight2(test);
			int ans3 = minLight3(test);

//			int ans1 = minLight1My(test);
//			int ans2 = minLight2My(test);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("oops!");
			}
		}
		System.out.println("finish!");
	}
}
