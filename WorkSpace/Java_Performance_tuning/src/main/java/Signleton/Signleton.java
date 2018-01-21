package Signleton;

public class Signleton {
	private Signleton() {
		System.out.println("hello 单例模式");
	}

	private static Signleton instance = null;

	public static synchronized Signleton getInstance() {
		if (instance == null)
			instance = new Signleton();
		return instance;
	}

	//	引入双重检查，double_check
//	原方式每次访问的时候都需要同步。为了减少同步的开销，于是有了双重检查模式。
//	private volatile static Signleton instance = null;
//
//	public static Singleton getInstance() {
//		if (instance == null) {
//			synchronized (Singleton.class) {
//				if (instance == null)
//					instance = new Singleton();
//			}
//		}
//		return instance;
//	}
}

