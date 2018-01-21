import java.util.ArrayList;
import java.util.List;

public class Test01 {
	static long a = 0;

	public static void main(String[] args) {
//		testArrayCopyNormal();
//		testArrayCopy();

		testifelse();
		testSwitchInt();
		testArrayInt();

//		test1Array();
//		test2Array();
	}

	public static void testArrayCopyNormal() {
		int size = 100000;
		int[] array = new int[size];
		int[] arraydst = new int[size];
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}
		long start = System.currentTimeMillis();
		for (int k = 0; k < 10000; k++) {
			for (int i = 0; i < size; i++) {
				arraydst[i] = array[i];
			}
		}
		System.out.println("一般方式复制数组" + (System.currentTimeMillis() - start)
				+ "ms");
	}

	public static void testArrayCopy() {
		int size = 100000;
		int[] array = new int[size];
		int[] arraydst = new int[size];
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}
		long start = System.currentTimeMillis();
		for (int k = 0; k < 10000; k++) {
			System.arraycopy(array, 0, arraydst, 0, size);
		}
		System.out.println("arraycope方式复制数组"
				+ (System.currentTimeMillis() - start) + "ms");
	}

	public static void testBit() {
		long start = System.currentTimeMillis();
		boolean a = false;
		boolean b = true;
		int d = 0;
		for (int i = 0; i < 10000000; i++) {
			if (a & b & "java_special".contains("java")) {
				d = 0;
			}
		}
		System.out.println("位运算" + (System.currentTimeMillis() - start) + "ms");
	}

	public static void testBool() {
		long start = System.currentTimeMillis();
		boolean a = false;
		boolean b = true;
		int d = 0;
		for (int i = 0; i < 10000000; i++) {
			if (a && b && "java_special".contains("java")) {
				d = 0;
			}
		}
		System.out
				.println("布尔运算" + (System.currentTimeMillis() - start) + "ms");
	}

	public static void testNormalCircle() {
		long start = System.currentTimeMillis();
		int[] array = new int[10000000];
		for (int i = 0; i < 10000000; i++) {
			array[i] = i;
		}
		System.out
				.println("一般循环" + (System.currentTimeMillis() - start) + "ms");
	}

	public static void testExpandCircle() {
		long start = System.currentTimeMillis();
		int[] array = new int[10000000];
		for (int i = 0; i < 10000000; i += 2) {
			array[i] = i;
			array[i + 1] = i + 1;
		}
		System.out
				.println("展开循环" + (System.currentTimeMillis() - start) + "ms");
	}

	public static void testWeitiqu() {
		double d = Math.random();
		double a = Math.random();
		double b = Math.random();
		double e = Math.random();
		double x, y;
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			x = d * a * b / 3 * 4 * a;
			y = e * a * b / 3 * 4 * a;
		}
		System.out.println("未提取表达式" + (System.currentTimeMillis() - start)
				+ "ms");
	}

	public static void testtiqu() {
		double d = Math.random();
		double a = Math.random();
		double b = Math.random();
		double e = Math.random();
		double x, y, t;
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			t = a * b / 3 * 4 * a;
			x = d * t;
			y = e * t;
		}
		System.out.println("提取表达式" + (System.currentTimeMillis() - start)
				+ "ms");
	}

	public static int switchInt(int z) {
		int i = z % 10 + 1;
		switch (i) {
		case 1:
			return 3;
		case 2:
			return 6;
		case 3:
			return 7;
		case 4:
			return 8;
		case 5:
			return 10;
		case 6:
			return 15;
		case 7:
			return 20;
		case 8:
			return 35;
		default:
			return -1;
		}
	}

	public static int ifelseInt(int z) {
		int i = z % 10 + 1;
		if (i == 1)
			return 3;
		else if (i == 2)
			return 6;
		else if (i == 3)
			return 7;
		else if (i == 4)
			return 8;
		else if (i == 5)
			return 10;
		else if (i == 6)
			return 15;
		else if (i == 7)
			return 20;
		else if (i == 8)
			return 35;
		else
			return -1;
	}

	public static int arrayInt(int[] sw, int z) {
		int i = z % 10 + 1;
		if (i > 7 || i < 1)
			return -1;
		else
			return sw[i];
	}

	public static void testifelse() {
		long start = System.currentTimeMillis();
		int re = 0;
		for (int i = 0; i < 10000000; i++) {
			re = ifelseInt(i);
		}
		System.out.println("ifelse所消耗" + (System.currentTimeMillis() - start)
				+ "ms");
	}

	public static void testSwitchInt() {
		long start = System.currentTimeMillis();
		int re = 0;
		for (int i = 0; i < 10000000; i++) {
			re = switchInt(i);
		}
		System.out.println("switch所消耗" + (System.currentTimeMillis() - start)
				+ "ms");
	}

	public static void testArrayInt() {
		long start = System.currentTimeMillis();
		int re = 0;
		int[] sw = new int[] { 0, 3, 6, 7, 8, 10, 15, 20, 35 };
		for (int i = 0; i < 10000000; i++) {
			re = arrayInt(sw, i);
		}
		System.out.println("array所消耗" + (System.currentTimeMillis() - start)
				+ "ms");
	}

	public static void testMultiply() {
		long start = System.currentTimeMillis();
		long a = 100;
		for (int i = 0; i < 10000000; i++) {
			a *= 2;
			a /= 2;
		}
		System.out.println("直接做乘法" + (System.currentTimeMillis() - start)
				+ "ms");
	}

	public static void testMultplyBit() {
		long start = System.currentTimeMillis();
		long a = 100;
		for (int i = 0; i < 10000000; i++) {
			a <<= 1;
			a >>= 1;
		}
		System.out.println("位运算乘法" + (System.currentTimeMillis() - start)
				+ "ms");
	}

	public static void testLocalVar() {
		long start = System.currentTimeMillis();
		long a = 0;
		for (int i = 0; i < 10000000; i++) {
			a++;
		}
		System.out
				.println("局部变量" + (System.currentTimeMillis() - start) + "ms");
	}

	public static void testMemberVar() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			a++;
		}
		System.out
				.println("成员变量" + (System.currentTimeMillis() - start) + "ms");
	}

	public static void testException() {
		long start = System.currentTimeMillis();
		int a = 0;
		for (int i = 0; i < 10000000; i++) {
			try {
				a++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("有异常" + (System.currentTimeMillis() - start) + "ms");
	}

	public static void testNoException() {
		long start = System.currentTimeMillis();
		int a = 0;
		for (int i = 0; i < 10000000; i++) {
			a++;
		}
		System.out.println("没异常" + (System.currentTimeMillis() - start) + "ms");
	}

	public static void test1Array() {
		long start = System.currentTimeMillis();
		int[] array = new int[1000000];
		int re = 0;
		int size = array.length;
		for (int k = 0; k < 100; k++)
			for (int i = 0; i < size; i++)
				array[i] = i;
		for (int k = 0; k < 100; k++)
			for (int i = 0; i < size; i++)
				re = array[i];
		System.out.println("一维数组" + (System.currentTimeMillis() - start) + "ms");
	}
	
	public static void test2Array(){
		long start = System.currentTimeMillis();
		int[][] array = new int[1000][1000];
		int re = 0;
		int size = array.length;
		int size1 = array[0].length;
		for (int k = 0; k < 100; k++)
			for(int i=0;i<size;i++)
				 for(int j=0;j<size1;j++)
					 array[i][j] = i;
		
		for (int k = 0; k < 100; k++)
			for(int i=0;i<size;i++)
				 for(int j=0;j<size1;j++)
					 re = array[i][j];
		System.out.println("二维数组" + (System.currentTimeMillis() - start) + "ms");
	}

}
