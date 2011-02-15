package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jmock.Expectations;
import org.junit.Test;

public class PutshlTest extends AbstractCommandTest {
	@Test public void protocol() {
		int width = 1;
		Putshl dut = new Putshl(transcoder, transcoder, key, value, width);
		
		ChannelBuffer expected = ChannelBuffers.buffer(2 + 4 + 4 + 4 + key.length + value.length);
		expected.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x13 });
		expected.writeInt(key.length);
		expected.writeInt(value.length);
		expected.writeInt(width);
		expected.writeBytes(key);
		expected.writeBytes(value);
		ChannelBuffer actual = ChannelBuffers.buffer(expected.capacity());
		dut.encode(actual);
		assertEquals(expected, actual);
		
		ChannelBuffer response = ChannelBuffers.buffer(1);
		assertFalse(dut.decode(response));
		
		response.writeByte(Command.ESUCCESS);
		assertTrue(dut.decode(response));
		assertTrue(dut.getReturnValue());
		
		//error
		response.clear();
		response.writeByte(Command.EUNKNOWN);
		assertTrue(dut.decode(response));
		assertFalse(dut.getReturnValue());
	}

	@Test public void rdb() {
		rdb.putshl(key, value, 1);
		rdb.putshl(key, value, 1, transcoder);
	}
	
	@Test public void mrdb() {
		mockery.checking(new Expectations() {{
			one(networking).send(with(any(Putshl.class)));
			one(networking).send(with(any(Putshl.class)));
		}});
		mrdb.putshl(key, value, 1);
		mrdb.putshl(key, value, 1, transcoder);
	}
}
