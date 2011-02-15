package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jmock.Expectations;
import org.junit.Test;

public class RnumTest extends AbstractCommandTest {
	@Test public void protocol() {
		long rnum = 123;
		Rnum dut = new Rnum();
		
		ChannelBuffer request = ChannelBuffers.buffer(2);
		request.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x80 });
		ChannelBuffer actual = ChannelBuffers.buffer(request.capacity());
		dut.encode(actual);
		assertEquals(request, actual);
		
		ChannelBuffer response = ChannelBuffers.buffer(1 + 8);
		assertFalse(dut.decode(response));
		
		response.writeByte(Command.ESUCCESS);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();
		
		response.writeLong(rnum);
		assertTrue(dut.decode(response));
		assertEquals(rnum, (long)dut.getReturnValue());
	}

	@Test public void rdb() {
		rdb.rnum();
	}
	
	@Test public void mrdb() {
		mockery.checking(new Expectations() {{
			one(networking).send(with(any(Rnum.class)));
		}});
		mrdb.rnum();
	}
}
