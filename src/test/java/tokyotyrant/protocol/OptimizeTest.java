package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.Test;

public class OptimizeTest extends AbstractCommandTest {
	@Test public void protocol() {
		String parameters = "#bnum=1024";
		Optimize dut = new Optimize(parameters);
		
		ChannelBuffer request = ChannelBuffers.buffer(2 + 4 + parameters.getBytes().length);
		request.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x71 });
		request.writeInt(parameters.getBytes().length);
		request.writeBytes(parameters.getBytes());
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
		rdb.optimize("#bnum=2048");
	}
}
