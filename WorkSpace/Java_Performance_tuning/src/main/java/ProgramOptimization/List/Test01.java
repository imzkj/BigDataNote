package ProgramOptimization.List;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Test01 {
	private static final int CIRCLE = 100000;
	protected List<Object> list;

	public static void main(String[] args) {
		new Test01().del();
	}

	// 添加
	public void addTail(String name) {
		Object obj = new Object();
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 500000; i++) {
			list.add(obj);
		}
		long end = System.currentTimeMillis();
		System.out.println(name + "添加所需要的时间" + (end - begin) + "ms");
	}

	// 在顶部插数据
	public void addFirst(String name) {
		Object obj = new Object();
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 500000; i++) {
			list.add(0, obj);
		}
		long end = System.currentTimeMillis();
		System.out.println(name + "添加所需要的时间" + (end - begin) + "ms");
	}

	// 在尾部删除
	public void testDelTail(String name) {
		Object obj = new Object();
		for (int i = 0; i < 500000; i++) {
			list.add(0, obj);
		}
		long begin = System.currentTimeMillis();
		for (int i = 0; i < CIRCLE; i++) {
			list.remove(list.size() - 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(name + "添加所需要的时间" + (end - begin) + "ms");
	}

	// 从头部删除
	public void testDelFirst(String name) {
		Object obj = new Object();
		for (int i = 0; i < 500000; i++) {
			list.add(0, obj);
		}
		long begin = System.currentTimeMillis();
		for (int i = 0; i < CIRCLE; i++) {
			list.remove(0);
		}
		long end = System.currentTimeMillis();
		System.out.println(name + "添加所需要的时间" + (end - begin) + "ms");
	}

	// 从中部删除
	public void testDelMiddle(String name) {
		Object obj = new Object();
		for (int i = 0; i < 500000; i++) {
			list.add(0, obj);
		}
		long begin = System.currentTimeMillis();
		while(list.size()>0) {
			list.remove(list.size()>>1);
		}
		long end = System.currentTimeMillis();
		System.out.println(name + "删除所需要的时间" + (end - begin) + "ms");
	}

	public void add() {
		//list = new ArrayList<Object>();
		// list = new LinkedList<Object>();
		addFirst("LinkedList");
	}

	public void del() {
		 list = new ArrayList<Object>();
//		list = new LinkedList<Object>();
		testDelMiddle("LinkedList");
	}

}
