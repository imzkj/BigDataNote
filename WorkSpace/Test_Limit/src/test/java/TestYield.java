import java.util.concurrent.CountDownLatch;

public class TestYield extends Thread{

	final static CountDownLatch latch = new CountDownLatch(1);

		public TestYield(String name) {
			super(name);
		}

		@Override
		public void run() {
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = 1; i <= 50; i++) {
				System.out.println("" + this.getName() + "-----" + i);
				// 当i为30时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
				if (i == 30) {
					this.yield();
				}
			}
		}

		public static void main(String[] args) {
			TestYield yt1 = new TestYield("张三");
			TestYield yt2 = new TestYield("李四");
			TestYield yt3 = new TestYield("王五");
			yt1.start();
			yt2.start();
			yt3.start();
			latch.countDown();
		}

}
