package class14;

import java.util.Arrays;
import java.util.Comparator;
// ** 贪心 **
// 题目：3.安排最多长会议室

//一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。
//给你每一个项目开始的时间和结束的时间
//你来安排宣讲的日程，要求会议室进行的宣讲的场次最多。
//返回最多的宣讲场次。
public class Code03_BestArrange {

	public static class Program {
		public int start;
		public int end;

		public Program(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

	//test 暴力递归对数器！所有情况都尝试！
	public static int bestArrange1(Program[] programs) {
		if (programs == null || programs.length == 0) {
			return 0;
		}
		return process(programs, 0, 0);
	}

	// 还剩下的会议都放在programs里
	// done之前已经安排了多少会议的数量
	// timeLine目前来到的时间点是什么
	
	// 目前来到timeLine的时间点，已经安排了done多的会议，剩下的会议programs可以自由安排
	// 返回能安排的最多会议数量
	public static int process(Program[] programs, int doneNum, int timeLine) {
		if (programs.length == 0) { //basecase
			return doneNum;
		}
		// 还剩下会议
		int max = doneNum;
		// 当前安排的会议是什么会，每一个都枚举
		for (int i = 0; i < programs.length; i++) {
			//!!!breeze包含等于
			if (programs[i].start >= timeLine) {
				Program[] next = copyButExcept(programs, i); //copy的，不用恢复现场
				max = Math.max(max, process(next, doneNum + 1, programs[i].end));
			}
		}
		return max;
	}
	
	public static Program[] copyButExcept(Program[] programs, int i) {
		Program[] ans = new Program[programs.length - 1];
		int index = 0;
		for (int k = 0; k < programs.length; k++) {
			if (k != i) {
				ans[index++] = programs[k];
			}
		}
		return ans;
	}

	// 会议的开始时间和结束时间，都是数值，不会 < 0
	public static int bestArrange2(Program[] programs) {
		Arrays.sort(programs, new ProgramComparator());
		int timeLine = 0;
		int result = 0;
		// 依次遍历每一个会议，结束时间早的会议先遍历
		for (int i = 0; i < programs.length; i++) {
			if (timeLine <= programs[i].start) {
				result++;
				timeLine = programs[i].end;
			}
		}
		return result;
	}

	public static class ProgramComparator implements Comparator<Program> {

		@Override
		public int compare(Program o1, Program o2) {
			return o1.end - o2.end;
		}

	}

	public static int bestArrange2My(Program[] programs) {
		 return 0;
	}


	private static class MyComparator implements Comparator<Program> {
		@Override
		public int compare(Program o1, Program o2) {
			return o1.end - o2.end;
		}
	}


	// for test
	public static Program[] generatePrograms(int programSize, int timeMax) {
		Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
		for (int i = 0; i < ans.length; i++) {
			int r1 = (int) (Math.random() * (timeMax + 1));
			int r2 = (int) (Math.random() * (timeMax + 1));
			if (r1 == r2) {
				ans[i] = new Program(r1, r1 + 1);
			} else {
				ans[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
			}
		}
		return ans;
	}


	public static int bestArrange1My(Program[] programs) {
		if(programs == null || programs.length == 0) {
			return 0;
		}
		return processMy(programs, 0, 0);
	}
	public static int processMy(Program[] programs, int doneNum, int timeEnd){
		if(programs.length == 0) {
			return doneNum;
		}
		int max = doneNum;
		for (int i = 0; i < programs.length; i++) {
			if(programs[i].start >= timeEnd) {
				int cur =  processMy(copyButExcept(programs, i), doneNum+1, programs[i].end);
				max = Math.max(max, cur);
			}
		}
		return max;
	}

	public static void main(String[] args) {
		int programSize = 12;
		int timeMax = 20;
		int timeTimes = 1000000;
		for (int i = 0; i < timeTimes; i++) {
			Program[] programs = generatePrograms(programSize, timeMax);
			if (bestArrange1(programs) != bestArrange2My(programs)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
