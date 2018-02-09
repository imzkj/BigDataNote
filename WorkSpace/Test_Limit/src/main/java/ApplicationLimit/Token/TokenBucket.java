package ApplicationLimit.Token;

import com.google.common.util.concurrent.RateLimiter;

public class TokenBucket {
	public static void main(String[] args) {
		normalUsage();
		overdraftUsage();
		acquireToken();
	}

	//	正常用法
	public static void normalUsage() {
		RateLimiter rateLimiter = RateLimiter.create(5); //令牌桶容量为5，即每200毫秒产生1个令牌
		System.out.println(rateLimiter.acquire()); //阻塞获取一个令牌 马上得到令牌，所以等待时间为0
		//因为令牌桶每200毫秒产生1个令牌，所以上面消耗掉令牌后桶里没令牌，需要等待新的令牌产生(200ms)后才能消费，下面的同理
		System.out.println(rateLimiter.acquire());
		System.out.println(rateLimiter.acquire());
		System.out.println(rateLimiter.acquire());
		System.out.println(rateLimiter.acquire());
		System.out.println(rateLimiter.acquire());
	}

	//	透支用法
	public static void overdraftUsage() {
		RateLimiter rateLimiter = RateLimiter.create(5); //令牌桶容量为5，即每200毫秒产生1个令牌
		System.out.println(rateLimiter.acquire(10)); //透支令牌
		//	令牌桶允许一定程度的透支，不过接下来的请求需要等透支完的补充回来后才能继续执行。
		System.out.println(rateLimiter.acquire());
		System.out.println(rateLimiter.acquire());
		System.out.println(rateLimiter.acquire());
		System.out.println(rateLimiter.acquire());
		System.out.println(rateLimiter.acquire());
	}

	//	请求令牌，没有令牌则抛弃请求
	public static void acquireToken() {
		RateLimiter rateLimiter = RateLimiter.create(10000); //每秒投放10000个令牌
		for (int i = 0; i < 10; i++) {
			if (rateLimiter.tryAcquire()) { //tryAcquire检测有没有可用的令牌，结果马上返回
				System.out.println("处理请求");
			} else {
				System.out.println("拒绝请求");
			}
		}
	}
}

