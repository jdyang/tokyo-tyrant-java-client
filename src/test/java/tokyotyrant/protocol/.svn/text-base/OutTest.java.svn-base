package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jmock.Expectations;
import org.junit.Test;

public class OutTest extends AbstractCommandTest {
	@Test public void protocol() {
		Out dut = new Out(transcoder, transcoder, key);
		
		ChannelBuffer expected = ChannelBuffers.buffer(2 + 4 + key.length);
		expected.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x20 });
		expected.writeInt(key.length);
		expected.writeBytes(key);
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
		rdb.out(key);
	}
	
	@Test public void mrdb() {
		mockery.checking(new Expectations() {{
			one(networking).send(with(any(Out.class)));
		}});
		mrdb.out(key);
	}
}
