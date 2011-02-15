package tokyotyrant.protocol;

import org.jmock.Expectations;
import org.junit.Test;

public class PutTest extends AbstractCommandTest {
	@Test public void protocol() {
		putFamily(new Put(transcoder, transcoder, key, value), 0x10);
	}

	@Test public void rdb() {
		rdb.put(key, value);
		rdb.put(key, value, transcoder);
	}
	
	@Test public void mrdb() {
		mockery.checking(new Expectations() {{
			one(networking).send(with(any(Put.class)));
			one(networking).send(with(any(Put.class)));
		}});
		mrdb.put(key, value);
		mrdb.put(key, value, transcoder);
	}
}
