package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.Test;

import tokyotyrant.RDB;

public class SetmstTest extends AbstractCommandTest {
	@Test public void setmst() {
		String host = "host";
		int port = 1978;
		long timestamp = System.currentTimeMillis();
		int opts = RDB.ROCHKCON;
		Setmst dut = new Setmst(host, port, timestamp, opts);
		
		ChannelBuffer request = ChannelBuffers.buffer(2 + 4 + 4 + 8 + 4 + host.getBytes().length);
		request.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x78 });
		request.writeInt(host.getBytes().length);
		request.writeInt(port);
		request.writeLong(timestamp);
		request.writeInt(opts);
		request.writeBytes(host.getBytes());
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
		rdb.setmst("host", 1978, 123L, RDB.ROCHKCON);
	}
}
