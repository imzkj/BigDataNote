public class TestVolatile {
	public volatile int inc = 0;

	public void increase() {
		inc++;
	}

	public static void main(String[] args) throws InterruptedException {
		final TestVolatile test = new TestVolatile();
		for (int i = 0; i < 100; i++) {
			new Thread() {
				public void run() {
					for (int j = 0; j < 1000; j++)
						test.increase();
				}
			}.start();
		}

		Thread.sleep(60*1000);
		//  以下错误，无法保证，线程让步后竞争是随机性
//		while (Thread.activeCount() > 1)  //保证前面的线程都执行完
//			Thread.yield();  //  线程让步，让其他线程都执行完成
		System.out.println(test.inc);
	}

}
