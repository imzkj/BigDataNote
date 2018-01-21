package ProgramOptimization.String;

import java.util.StringTokenizer;


public class TestSplit {

	public static void main(String[] args) {
		testSplit();
		testToken();
		testIndex();
	}
	
	//测试split
	public static void testSplit() {
		String orgStr = null;
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<500;i++) {
			sb.append(i);
			sb.append(";");
		}
		orgStr = sb.toString();
		long begin = System.currentTimeMillis();
		for(int i=0;i<10000;i++) {
			orgStr.split(";");
		}
		long end = System.currentTimeMillis();
		System.out.println("split所花费的时间"+(end-begin)+"ms");
	}
	
	//测试toke
	public static void testToken() {
		String orgStr = null;
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<500;i++) {
			sb.append(i);
			sb.append(";");
		}
		orgStr = sb.toString();
		long begin = System.currentTimeMillis();
		StringTokenizer st = new StringTokenizer(orgStr,";");
		for(int i=0;i<10000;i++) {
			while(st.hasMoreTokens()) {
				st.nextToken();
			}
			st = new StringTokenizer(orgStr, ";");
		}
		long end = System.currentTimeMillis();
		System.out.println("token所花费的时间"+(end-begin)+"ms");
	}
	
	//测试indexOf
	public static void testIndex() {
		String orgStr = null;
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<500;i++) {
			sb.append(i);
			sb.append(";");
		}
		orgStr = sb.toString();
		long begin = System.currentTimeMillis();
		String tmp = orgStr;
		for(int i=0;i<10000;i++) {
			while(true) {
				String splitStr = null;
				int j = tmp.indexOf(";");
				if(j<0) break;
				splitStr = tmp.substring(0,j);
				tmp = tmp.substring(j+1);
			}
			
			tmp = orgStr;
		}
		long end = System.currentTimeMillis();
		System.out.println("indexof所花费的时间"+(end-begin)+"ms");
	}
}
