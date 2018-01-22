package ProgramOptimization.Map;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Test04 {
  Map map;
  final int CIRCLE = 100000;
  
  public void testGet(String name) {
	  for(int i=0;i<CIRCLE;i++) {
		  String key = Double.toString(Math.random());
		  map.put(key, key);
	  }
	  long start = System.currentTimeMillis();
	  for(int i=0;i<CIRCLE;i++) {
		  String key = Double.toString(Math.random());
		  map.get(key);
	  }
	  long end = System.currentTimeMillis();
	  System.out.println(name+"ºÄÊ±"+(end-start)+"ms");
  }
  
  public void testHashMap() {
	  map = new HashMap();
	  testGet("testHashMap");
  }
  
  public void testHashTable() {
	  map = new Hashtable();
	  testGet("testHashTable");
  }
	
	public static void main(String[] args) {
		new Test04().testHashMap();
		new Test04().testHashTable();

	}

}
