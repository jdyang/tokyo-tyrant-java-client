package tokyotyrant.protocol;

import org.jmock.Expectations;
import org.junit.Test;

public class PutkeepTest extends AbstractCommandTest {
	@Test public void protocol() {
		putFamily(new Putkeep(transcoder, transcoder, key, value), 0x11);
	}

	@Test public void rdb() {
		rdb.putkeep(key, value);
		rdb.putkeep(key, value, transcoder);
	}
	
	@Test public void mrdb() {
		mockery.checking(new Expectations() {{
			one(networking).send(with(any(Putkeep.class)));
			one(networking).send(with(any(Putkeep.class)));
		}});
		mrdb.putkeep(key, value);
		mrdb.putkeep(key, value, transcoder);
	}
}
