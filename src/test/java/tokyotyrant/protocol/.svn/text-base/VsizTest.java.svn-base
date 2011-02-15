package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jmock.Expectations;
import org.junit.Test;

public class VsizTest extends AbstractCommandTest {
	@Test public void protocol() {
		Vsiz dut = new Vsiz(transcoder, transcoder, key);
		
		ChannelBuffer request = ChannelBuffers.buffer(2 + 4 + key.length);
		request.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x38 });
		request.writeInt(key.length);
		request.writeBytes(key);
		ChannelBuffer actual = ChannelBuffers.buffer(request.capacity());
		dut.encode(actual);
		assertEquals(request, actual);
		
		ChannelBuffer response = ChannelBuffers.buffer(1 + 4 + value.length);
		assertFalse(dut.decode(response));
		
		response.writeByte(Command.ESUCCESS);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();
		
		response.writeInt(value.length);
		assertTrue(dut.decode(response));
		assertEquals(value.length, (int)dut.getReturnValue());
		
		//error
		response.clear();
		response.writeByte(Command.EUNKNOWN);
		assertTrue(dut.decode(response));
		assertEquals(-1, (int)dut.getReturnValue());
	}

	@Test public void rdb() {
		rdb.vsiz(key);
	}
	
	@Test public void mrdb() {
		mockery.checking(new Expectations() {{
			one(networking).send(with(any(Out.class)));
		}});
		mrdb.vsiz(key);
	}
}
