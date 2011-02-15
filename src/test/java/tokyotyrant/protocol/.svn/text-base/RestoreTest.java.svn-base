package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.Test;

import tokyotyrant.RDB;

public class RestoreTest extends AbstractCommandTest {
	@Test public void protocol() {
		String path = "path";
		long timestamp = System.currentTimeMillis();
		int opts = RDB.ROCHKCON;
		Restore dut = new Restore(path, timestamp, opts);
		
		ChannelBuffer request = ChannelBuffers.buffer(2 + 4 + 8 + 4 + path.getBytes().length);
		request.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x74 });
		request.writeInt(path.getBytes().length);
		request.writeLong(timestamp);
		request.writeInt(opts);
		request.writeBytes(path.getBytes());
		ChannelBuffer actual = ChannelBuffers.buffer(request.capacity());
		dut.encode(actual);
		assertEquals(request, actual);
		
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
		rdb.restore("path", 123L, RDB.ROCHKCON);
	}
}
