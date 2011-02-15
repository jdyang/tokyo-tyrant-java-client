package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jmock.Expectations;
import org.junit.Test;

public class PutnrTest extends AbstractCommandTest {
	@Test public void protocol() {
		Putnr dut = new Putnr(transcoder, transcoder, key, value);
		
		ChannelBuffer expected = ChannelBuffers.buffer(2 + 4 + 4 + key.length + value.length);
		expected.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x18 });
		expected.writeInt(key.length);
		expected.writeInt(value.length);
		expected.writeBytes(key);
		expected.writeBytes(value);
		ChannelBuffer actual = ChannelBuffers.buffer(expected.capacity());
		dut.encode(actual);
		assertEquals(expected, actual);
		
		ChannelBuffer response = ChannelBuffers.buffer(1);
		assertTrue(dut.decode(response));
		assertTrue(dut.getReturnValue());
	}

	@Test public void rdb() {
		rdb.putnr(key, value);
		rdb.putnr(key, value, transcoder);
	}
	
	@Test public void mrdb() {
		mockery.checking(new Expectations() {{
			one(networking).send(with(any(Putnr.class)));
			one(networking).send(with(any(Putnr.class)));
		}});
		mrdb.putnr(key, value);
		mrdb.putnr(key, value, transcoder);
	}
}
