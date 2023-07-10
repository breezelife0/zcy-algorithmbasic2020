package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
// ** 手写加强堆 **
// 自建反向索引，可以根据值快速定位位置
//java的自带的堆没有反向索引，需要遍历，时间复杂度O(N)

// 引入：
// java系统自带的堆，如果内部某个节点的数值突然增大或是突然的减小，会导致这个堆功能失效。如果想要调整变化的节点，时间复
// 杂度是NlogN(先遍历数组找到值改变的节点，然后再向上/下调整)。
// 系统自带的堆，我们只能通过index找到value，无法通过value找到其index.所以我们对其进行改良，加上反向索引表hashmap。
// 这样我们可以通过value找到其index，使调整的时间复杂度降到log N。
// 图1

/*
 * T一定要是非基础类型，有基础类型需求包一层
 */
public class HeapGreater<T> {

	private ArrayList<T> heap;
	private HashMap<T, Integer> indexMap;
	private int heapSize;
	private Comparator<? super T> comp;

	public HeapGreater(Comparator<? super T> c) {
		heap = new ArrayList<>();
		indexMap = new HashMap<>();
		heapSize = 0;
		comp = c;
	}

	public boolean isEmpty() {
		return heapSize == 0;
	}

	public int size() {
		return heapSize;
	}

	public boolean contains(T obj) {
		return indexMap.containsKey(obj);
	}

	public T peek() {
		return heap.get(0);
	}

	public void push(T obj) {
		//放到数组中
		heap.add(obj);
		//放到索引中
		indexMap.put(obj, heapSize);
		//按堆的逻辑结构来调整数组
		heapInsert(heapSize++);
	}

	public T pop() {
		T ans = heap.get(0);
		swap(0, heapSize - 1);
		indexMap.remove(ans);
		heap.remove(--heapSize);
		heapify(0);
		return ans;
	}

	public void remove(T obj) {
		T replace = heap.get(heapSize - 1);
		int index = indexMap.get(obj);
		indexMap.remove(obj);
		heap.remove(--heapSize);
		if (obj != replace) {
			heap.set(index, replace);
			indexMap.put(replace, index);
			resign(replace);
		}
	}
	public void resign(T obj) {
		//进行heapInsert[或者]heapify
		heapInsert(indexMap.get(obj));
		heapify(indexMap.get(obj));
	}

	// 请返回堆上的所有元素
	public List<T> getAllElements() {
		List<T> ans = new ArrayList<>();
		for (T c : heap) {
			ans.add(c);
		}
		return ans;
	}

	private void heapInsert(int index) {
		while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
			swap(index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

	private void heapify(int index) {
		int left = index * 2 + 1;
		while (left < heapSize) {
			//使用自定义比较器
			int best = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? (left + 1) : left;
			best = comp.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
			if (best == index) {
				break;
			}
			swap(best, index);
			index = best;
			left = index * 2 + 1;
		}
	}

	//封装函数，同步调整堆和反向索引
	private void swap(int i, int j) {
		T o1 = heap.get(i);
		T o2 = heap.get(j);
		heap.set(i, o2);
		heap.set(j, o1);
		indexMap.put(o2, i);
		indexMap.put(o1, j);
	}

}
