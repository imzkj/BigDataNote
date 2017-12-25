package Signleton;

public class Signleton {
	private Signleton() {
		System.out.println("hello 单例模式");
	}

	private static Signleton instance = null;

	public static synchronized Signleton getInstance() {
		if(instance==null)
			instance = new Signleton();
		return instance;
	}
}
