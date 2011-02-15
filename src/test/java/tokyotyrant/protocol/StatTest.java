package tokyotyrant.protocol;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jmock.Expectations;
import org.junit.Test;

import tokyotyrant.networking.ActiveStandbyNodeLocator;
import tokyotyrant.networking.NodeLocator;
import tokyotyrant.networking.ServerNode;

public class StatTest extends AbstractCommandTest {
	@Test public void protocol() {
		String stat = "k1\tv1\nk2\tv2\n";
		Stat dut = new Stat();
		
		ChannelBuffer request = ChannelBuffers.buffer(2);
		request.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x88 });
		ChannelBuffer actual = ChannelBuffers.buffer(request.capacity());
		dut.encode(actual);
		assertEquals(request, actual);
		
		ChannelBuffer response = ChannelBuffers.buffer(1 + 4 + stat.getBytes().length);
		assertFalse(dut.decode(response));
		
		response.writeByte(Command.ESUCCESS);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();

		response.writeInt(stat.getBytes().length);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();

		response.writeBytes(stat.getBytes());
		assertTrue(dut.decode(response));
		Map<String, String> expected = new HashMap<String, String>();
		expected.put("k1", "v1");
		expected.put("k2", "v2");
		assertEquals(expected, dut.getReturnValue());
	}

	@Test public void rdb() {
		rdb.stat();
	}
	
	@Test public void mrdb() {
		final NodeLocator locator = new ActiveStandbyNodeLocator();
		locator.initialize(new ServerNode[] {});
		mockery.checking(new Expectations() {{
			allowing(networking).getNodeLocator(); will(returnValue(locator));
		}});
		mrdb.stat();
	}
}
