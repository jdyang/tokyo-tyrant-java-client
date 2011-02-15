package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jmock.Expectations;
import org.junit.Test;

public class SizeTest extends AbstractCommandTest {
	@Test public void protocol() {
		long size = 12345;
		Size dut = new Size();
		
		ChannelBuffer request = ChannelBuffers.buffer(2);
		request.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x81 });
		ChannelBuffer actual = ChannelBuffers.buffer(request.capacity());
		dut.encode(actual);
		assertEquals(request, actual);
		
		ChannelBuffer response = ChannelBuffers.buffer(1 + 8);
		assertFalse(dut.decode(response));
		
		response.writeByte(Command.ESUCCESS);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();
		
		response.writeLong(size);
		assertTrue(dut.decode(response));
		assertEquals(size, (long)dut.getReturnValue());
	}

	@Test public void rdb() {
		rdb.size();
	}
	
	@Test public void mrdb() {
		mockery.checking(new Expectations() {{
			one(networking).send(with(any(Size.class)));
		}});
		mrdb.size();
	}
}
