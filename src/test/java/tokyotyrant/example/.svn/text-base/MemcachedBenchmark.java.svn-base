package tokyotyrant.example;

import static org.junit.Assert.*;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.CachedData;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.Transcoder;

public class MemcachedBenchmark {
	public static void main(String[] args) throws Exception {
		final MemcachedClient db = new MemcachedClient(AddrUtil.getAddresses(args[0]));
		final Transcoder<Object> transcoder = new Transcoder<Object>() {
			public CachedData encode(Object data) {
				return new CachedData(0, (byte[]) data, getMaxSize());
			}
			public Object decode(CachedData cachedData) {
				return cachedData.getData();
			}
			public boolean asyncDecode(CachedData arg0) {
				return false;
			}
			public int getMaxSize() {
				return CachedData.MAX_SIZE;
			}
		};
		final String key = "key";
		final byte[] value = new byte[128];
		value[0] = 1;
		value[1] = 2;
		value[2] = 3;
		db.set(key, 0, value).get();
		Runnable task = new Runnable() {
			public void run() {
				try {
					assertArrayEquals(value, (byte[]) db.asyncGet(key, transcoder).get());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		for (int c : new int[] {1, 10, 100, 200, 300}) {
			System.out.println(c + ":" + (new Benchmark(c, 10000).run(task)) + "ms");
		}
		db.shutdown();
	}
}
