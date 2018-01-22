package ProgramOptimization.String;

public class TestBuilder {

	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer(9);
		StringBuilder sbr = new StringBuilder(9);
		long begin = System.currentTimeMillis();
		for(int i=0;i<500000;i++) {
			sb.append(i);
		}
		long end = System.currentTimeMillis();
		System.out.println("StringBuffer所花费的时间" + (end - begin) + "ms");
		
		begin = System.currentTimeMillis();
		for(int i=0;i<500000;i++) {
			sbr.append(i);
		}
		end = System.currentTimeMillis();
		System.out.println("StringBuilder所花费的时间" + (end - begin) + "ms");
		
	}

}
