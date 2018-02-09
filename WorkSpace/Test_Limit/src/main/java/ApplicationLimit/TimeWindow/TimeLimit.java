package ApplicationLimit.TimeWindow;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/*限制住一个窗口时间内接口的请求量，例如某个基础服务调用量很大，怕被突然的大流量打挂，下面是一种实现窗口时间并发控制的方法
我们使用Guava的Cache来存储计数器，利用秒数作为Key，Value代表这一秒有多少个请求，这样就限制了一秒内的并发数。另外过期
时间设置为两秒，保证一秒内的数据是存在的。*/

public class TimeLimit {

	private long limit = 5; //限流数

	static volatile boolean exit;

	private LoadingCache<Long, AtomicLong> counter =
			CacheBuilder.newBuilder()
					.expireAfterWrite(2, TimeUnit.SECONDS)
					.build(new CacheLoader<Long, AtomicLong>() {
						@Override
						public AtomicLong load(Long aLong) throws Exception {
							return new AtomicLong(0);
						}
					});


	public void doRequest(String threadName) throws ExecutionException {
		long currentSecond = System.currentTimeMillis() / 1000;
		if (counter.get(currentSecond).incrementAndGet() > limit) {
			System.out.println(threadName + ":请求过多，请稍后再尝试");
			System.out.println("第 " + currentSecond + " 秒请求个数: " + counter.get(currentSecond));
		} else {
			System.out.println(threadName + ":您的请求已受理");
			System.out.println("第 " + currentSecond + " 秒请求个数: " + counter.get(currentSecond));
		}
	}

	public static void main(String[] args) throws InterruptedException {
		//或等当前秒数

		final TimeLimit timeLimit = new TimeLimit();
		final CountDownLatch latch = new CountDownLatch(1);
		for (int i = 0; i < 10; i++) {
			final int finalI = i;
			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						latch.await();
						while (!exit) {
							timeLimit.doRequest("t-" + finalI);
							Thread.sleep(1000);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			});
			t.start();
		}
		latch.countDown();
		Thread.sleep(3000);
		exit = true;
	}

}