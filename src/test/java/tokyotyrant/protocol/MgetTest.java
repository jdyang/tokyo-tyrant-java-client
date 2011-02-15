package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jmock.Expectations;
import org.junit.Test;

public class MgetTest extends AbstractCommandTest {
	@Test public void protocol() {
		Mget dut = new Mget(transcoder, transcoder, new Object[] { key });
		
		ChannelBuffer request = ChannelBuffers.buffer(2 + 4 + 4 + key.length);
		request.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x31 });
		request.writeInt(1);
		request.writeInt(key.length);
		request.writeBytes(key);
		ChannelBuffer actual = ChannelBuffers.buffer(request.capacity());
		dut.encode(actual);
		assertEquals(request, actual);
		
		ChannelBuffer response = ChannelBuffers.buffer(1 + 4 + 4 + 4 + key.length + value.length);
		assertFalse(dut.decode(response));
		
		response.writeByte(Command.ESUCCESS);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();

		response.writeInt(1);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();

		response.writeInt(key.length);
		response.writeInt(value.length);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();
		
		response.writeBytes(key);
		response.writeBytes(value);
		assertTrue(dut.decode(response));
		assertArrayEquals(value, (byte[]) dut.getReturnValue().values().iterator().next());
		
		//error
		response.clear();
		response.writeByte(Command.EUNKNOWN);
		response.writeInt(0);
		assertTrue(dut.decode(response));
		assertNull(dut.getReturnValue());
	}

	@Test public void rdb() {
		rdb.mget(new Object[] { key });
		rdb.mget(new Object[] { key }, transcoder);
	}
	
	@Test public void mrdb() {
		mockery.checking(new Expectations() {{
			one(networking).send(with(any(Mget.class)));
			one(networking).send(with(any(Mget.class)));
		}});
		mrdb.mget(new Object[] { key });
		mrdb.mget(new Object[] { key }, transcoder);
	}
}
