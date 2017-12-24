package special.test;

public class Test {
	public static final int CIRCLE = 30000000;

	public static void main(String[] args) {
		IDBQuery d = null;
		long begin = System.currentTimeMillis();
		d = JdkDbQeuryHandler.createJdkProxy();
		System.out.println("创建jdk动态代理所需要的时间:"
				+ (System.currentTimeMillis() - begin));
		begin = System.currentTimeMillis();
		for (int i = 0; i < CIRCLE; i++) {
			d.request();
		}
		System.out.println("创建jdk动态代理调用方法所需要的时间:"
				+ (System.currentTimeMillis() - begin));
		begin = System.currentTimeMillis();
		d = CglibDbQueryInterceptor.createCglibProxy();
		System.out.println("创建cglib动态代理所需要的时间:"
				+ (System.currentTimeMillis() - begin));
		begin = System.currentTimeMillis();
		for (int i = 0; i < CIRCLE; i++) {
			d.request();
		}
		System.out.println("创建cglib动态代理调用方法所需要的时间:"
				+ (System.currentTimeMillis() - begin));
	}

}