package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jmock.Expectations;
import org.junit.Test;

import tokyotyrant.RDB;

public class ExtTest extends AbstractCommandTest {
	@Test public void protocol() {
		String name = "function";
		Ext dut = new Ext(transcoder, transcoder, name, key, value, RDB.XOLCKREC);
		
		ChannelBuffer request = ChannelBuffers.buffer(2 + 4 + 4 + 4 + 4 + name.getBytes().length + key.length + value.length);
		request.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x68 });
		request.writeInt(name.getBytes().length);
		request.writeInt(RDB.XOLCKREC);
		request.writeInt(key.length);
		request.writeInt(value.length);
		request.writeBytes(name.getBytes());
		request.writeBytes(key);
		request.writeBytes(value);
		ChannelBuffer actual = ChannelBuffers.buffer(request.capacity());
		dut.encode(actual);
		assertEquals(request, actual);
		
		ChannelBuffer response = ChannelBuffers.buffer(1 + 4 + value.length);
		assertFalse(dut.decode(response));
		
		response.writeByte(Command.ESUCCESS);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();
		
		response.writeInt(value.length);
		response.writeBytes(value);
		assertTrue(dut.decode(response));
		assertArrayEquals(value, (byte[])dut.getReturnValue());
		
		//error
		response.clear();
		response.writeByte(Command.EUNKNOWN);
		assertTrue(dut.decode(response));
		assertNull(dut.getReturnValue());
	}

	@Test public void rdb() {
		rdb.ext("name", key, value, RDB.XOLCKREC);
		rdb.ext("name", key, value, RDB.XOLCKREC, transcoder);
	}
	
	@Test public void mrdb() {
		mockery.checking(new Expectations() {{
			one(networking).send(with(any(Ext.class)));
			one(networking).send(with(any(Ext.class)));
		}});
		mrdb.ext("name", key, value, RDB.XOLCKREC);
		mrdb.ext("name", key, value, RDB.XOLCKREC, transcoder);
	}
}
