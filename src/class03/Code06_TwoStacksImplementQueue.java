package class03;

import java.util.Stack;
// ** 栈和队列 **
// 6.如何用栈结构实现队列结构
// 思路：两个栈倒腾一次
public class Code06_TwoStacksImplementQueue {

	public static class TwoStacksQueue {
		public Stack<Integer> stackPush;
		public Stack<Integer> stackPop;

		public TwoStacksQueue() {
			stackPush = new Stack<Integer>();
			stackPop = new Stack<Integer>();
		}

		// push栈向pop栈倒入数据
		// 1. 当弹出栈空了，才会一次性全量倒数据
		// 2 pop栈不为空的时候，不能倒数据(否则数据错乱)
		private void pushToPop() {
			if (stackPop.empty()) {
				while (!stackPush.empty()) {
					stackPop.push(stackPush.pop());
				}
			}
		}

		public void add(int pushInt) {
			stackPush.push(pushInt);
			pushToPop();
		}

		public int poll() {
			if (stackPop.empty() && stackPush.empty()) {
				throw new RuntimeException("Queue is empty!");
			}
			pushToPop();
			return stackPop.pop();
		}

		public int peek() {
			if (stackPop.empty() && stackPush.empty()) {
				throw new RuntimeException("Queue is empty!");
			}
			pushToPop();
			return stackPop.peek();
		}
	}

	public static void main(String[] args) {
		TwoStacksQueue test = new TwoStacksQueue();
		test.add(1);
		test.add(2);
		test.add(3);
		System.out.println(test.peek());
		System.out.println(test.poll());
		System.out.println(test.peek());
		System.out.println(test.poll());
		System.out.println(test.peek());
		System.out.println(test.poll());
	}

}
