import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TestLodingCache {

	private static LoadingCache<Long, AtomicLong> counter =
			CacheBuilder.newBuilder()
					.expireAfterWrite(2, TimeUnit.SECONDS)
					.build(new CacheLoader<Long, AtomicLong>() {
						@Override
						public AtomicLong load(Long aLong) throws Exception {
							return new AtomicLong(0);
						}
					});

	public static void main(String[] args) throws ExecutionException {
		while (true) {
			counter.get(1L).incrementAndGet();
			ConcurrentMap<Long, AtomicLong> longAtomicLongConcurrentMap = counter.asMap();
			System.out.println(longAtomicLongConcurrentMap.toString());
		}
	}
}
