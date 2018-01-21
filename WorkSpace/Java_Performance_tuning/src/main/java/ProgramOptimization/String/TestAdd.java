package ProgramOptimization.String;

public class TestAdd {

	public static void main(String[] args) {
		testAdd();
		testConcat();
		testBuilder();
	}
	
	public static void testAdd() {
		long begin = System.currentTimeMillis();
		String str = "";
		for(int i=0;i<10000;i++) {
			str=str+i;
		}
		long end = System.currentTimeMillis();
		System.out.println("+所花费的时间" + (end - begin) + "ms");
	}
	
	public static void testConcat() {
		long begin = System.currentTimeMillis();
		String str = "";
		for(int i=0;i<10000;i++) {
			str=str.concat(String.valueOf(i));
		}
		long end = System.currentTimeMillis();
		System.out.println("concat所花费的时间" + (end - begin) + "ms");
	}
	
	public static void testBuilder() {
		long begin = System.currentTimeMillis();
		StringBuilder str = new StringBuilder();
		for(int i=0;i<10000;i++) {
			str.append(i);
		}
		long end = System.currentTimeMillis();
		System.out.println("Builder所花费的时间" + (end - begin) + "ms");
	}

}
