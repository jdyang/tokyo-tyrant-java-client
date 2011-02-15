package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jmock.Expectations;
import org.junit.Test;

public class FwmkeysTest extends AbstractCommandTest {
	@Test public void protocol() {
		Fwmkeys dut = new Fwmkeys(transcoder, transcoder, key, Integer.MAX_VALUE);
		
		ChannelBuffer request = ChannelBuffers.buffer(2 + 4 + 4 + key.length);
		request.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x58 });
		request.writeInt(key.length);
		request.writeInt(Integer.MAX_VALUE);
		request.writeBytes(key);
		ChannelBuffer actual = ChannelBuffers.buffer(request.capacity());
		dut.encode(actual);
		assertEquals(request, actual);
		
		ChannelBuffer response = ChannelBuffers.buffer(1 + 4 + 4 + key.length);
		assertFalse(dut.decode(response));
		
		response.writeByte(Command.ESUCCESS);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();

		response.writeInt(1);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();

		response.writeInt(key.length);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();
		
		response.writeBytes(key);
		assertTrue(dut.decode(response));
		assertEquals(1, dut.getReturnValue().length);
		assertArrayEquals(key, (byte[]) dut.getReturnValue()[0]);
		
		//error
		response.clear();
		response.writeByte(Command.EUNKNOWN);
		response.writeInt(0);
		assertTrue(dut.decode(response));
		assertNull(dut.getReturnValue());
	}

	@Test public void rdb() {
		rdb.fwmkeys(key, 10);
	}
	
	@Test public void mrdb() {
		mockery.checking(new Expectations() {{
			one(networking).send(with(any(Fwmkeys.class)));
		}});
		mrdb.fwmkeys(key, 10);
	}
}
