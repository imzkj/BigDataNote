package special;

public class Test {
	public static void main(String[] args) {
		IDBQuery q = new DBQueryProxy();
		System.out.println(q.request());
	}

}
