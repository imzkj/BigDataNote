package ProgramOptimization.List;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Test02 {
	List<String> list = null;
	private static final int CIRCLE = 1000000;

	public void initList(List<String> list) {
		list.clear();
		for (int i = 0; i < CIRCLE; i++) {
			list.add(String.valueOf(0));
		}
	}

	public void internalTest() {
		String tmp;
		long start = System.currentTimeMillis();
		for (String s : list) {
			tmp = s;
		}
		System.out.println("foreach耗时:" + (System.currentTimeMillis() - start)
				+ "ms");
		start = System.currentTimeMillis();
		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			tmp = it.next();
		}
		System.out.println("Iterator耗时:" + (System.currentTimeMillis() - start)
				+ "ms");
		start = System.currentTimeMillis();
		int size = list.size();
		for(int i=0;i<size;i++) {
			tmp = list.get(i);
		}
		System.out.println("for循环耗时:" + (System.currentTimeMillis() - start)
				+ "ms");
	}
	
	public void testArrayList() {
		list = new ArrayList<String>();
		initList(list);
		internalTest();
	}
	
	public void testLinkList() {
		list = new LinkedList<String>();
		initList(list);
		internalTest();
	}
	
	

	public static void main(String[] args) {
//      	new Test02().testLinkList();
      	new Test02().testArrayList();
	}

}
