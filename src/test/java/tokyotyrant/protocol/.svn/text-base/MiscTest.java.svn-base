package tokyotyrant.protocol;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.Test;

import tokyotyrant.RDB;

public class MiscTest extends AbstractCommandTest {
	@Test public void protocol() {
		String name = "name";
		List<byte[]> args = Arrays.asList(key);
		int opts = RDB.MONOULOG;
		List<byte[]> elements = Arrays.asList(value);
		Misc dut = new Misc(name, args, opts);
		
		ChannelBuffer request = ChannelBuffers.buffer(2 + 4 + 4 + 4 + name.length() + (4 + key.length));
		request.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x90 });
		request.writeInt(name.length());
		request.writeInt(opts);
		request.writeInt(args.size());
		request.writeBytes(name.getBytes());
		request.writeInt(key.length);
		request.writeBytes(key);
		ChannelBuffer actual = ChannelBuffers.buffer(request.capacity());
		dut.encode(actual);
		assertEquals(request, actual);
		
		ChannelBuffer response = ChannelBuffers.buffer(1 + 4 + (4 + value.length));
		assertFalse(dut.decode(response));
		
		response.writeByte(Command.ESUCCESS);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();

		response.writeInt(elements.size());
		assertFalse(dut.decode(response));
		response.resetReaderIndex();

		response.writeInt(value.length);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();
		
		response.writeBytes(value);
		assertTrue(dut.decode(response));
		assertArrayEquals(elements.toArray(), dut.getReturnValue().toArray());
		
		//error
		response.clear();
		response.writeByte(Command.EUNKNOWN);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();

		response.writeInt(0);
		assertTrue(dut.decode(response));
		assertNull(dut.getReturnValue());
	}
}
