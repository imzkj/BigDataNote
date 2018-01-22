package ApplicationLimit.Distrubute;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class DistributeLimit {
	public Long acquire() throws IOException {
		String luaScript = Files.toString(new File("src/main/resources/limit.lua"), Charset.defaultCharset());
		Jedis jedis = new Jedis("localhost", 6379);
		//  String key = "ip:" + System.currentTimeMillis() / 1000; //此处将当前时间戳取秒数
		String key = "ip:" + 1; //此处硬编码时间，保证请求都是在同一秒内发起
		String limit = "6"; //限流大小
		return (Long) jedis.eval(luaScript, Lists.newArrayList(key), Lists.newArrayList("2", limit));
	}

	public static void main(String[] args) throws IOException {
		final DistributeLimit distributeLimit = new DistributeLimit();
		final CountDownLatch latch = new CountDownLatch(1);//两个工人的协作
		final Random random = new Random(10);
		for (int i = 0; i < 10; i++) {
			final int finalI = i;
			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						latch.await();
						int sleepTime = random.nextInt(1000);
						Thread.sleep(sleepTime);
						Long rev = distributeLimit.acquire();
						if (rev == 1) {
							System.out.println("t:" + finalI + ":" + "请求成功");
						} else {
							System.out.println("t:" + finalI + ":" + "被限流了");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			t.start();
		}
		latch.countDown();
		System.in.read();
	}
}