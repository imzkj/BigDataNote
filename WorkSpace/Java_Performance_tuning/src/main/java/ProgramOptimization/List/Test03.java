package ProgramOptimization.List;

import java.util.ArrayList;
import java.util.List;

public class Test03 {
    public static void test01() {
    	List<String> list = new ArrayList<String>();
    	long start = System.currentTimeMillis();
    	for(int i=0;i<1000000;i++) {
    		list.add(String.valueOf(0));
    	}
    	long end = System.currentTimeMillis();
    	System.out.println("不指定容量耗时:"+(end-start)+"ms");
    	
    }
    
    public static void test02() {
    	List<String> list = new ArrayList<String>(10000);
    	long start = System.currentTimeMillis();
    	for(int i=0;i<1000000;i++) {
    		list.add(String.valueOf(0));
    	}
    	long end = System.currentTimeMillis();
    	System.out.println("指定容量耗时:"+(end-start)+"ms");
    	
    }
    
	public static void main(String[] args) {
		test01();
		test02();
	}

}
