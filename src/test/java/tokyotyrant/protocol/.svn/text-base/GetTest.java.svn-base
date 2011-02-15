package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jmock.Expectations;
import org.junit.Test;

public class GetTest extends AbstractCommandTest {

	@Test
	public void testGet() {
		byte[] key = "name1".getBytes();
		Get dut = new Get(transcoder, transcoder, key);

		ChannelBuffer request = ChannelBuffers.buffer(2 + 4 + key.length);
		request.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x30 });
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
		response.writeBytes(value);
		
		System.out.println(new String(value));
		
		assertTrue(dut.decode(response));
		assertArrayEquals(value, (byte[]) dut.getReturnValue());

		// error
		response.clear();
		response.writeByte(Command.EUNKNOWN);
		assertTrue(dut.decode(response));
		assertNull(dut.getReturnValue());
	}

	@Test
	public void protocol() {
		Get dut = new Get(transcoder, transcoder, key);

		ChannelBuffer request = ChannelBuffers.buffer(2 + 4 + key.length);
		request.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x30 });
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
		response.writeBytes(value);
		assertTrue(dut.decode(response));
		assertArrayEquals(value, (byte[]) dut.getReturnValue());

		// error
		response.clear();
		response.writeByte(Command.EUNKNOWN);
		assertTrue(dut.decode(response));
		assertNull(dut.getReturnValue());
	}

	@Test
	public void rdb() {
		rdb.get(key);
		rdb.get(key, transcoder);
	}

	@Test
	public void mrdb() {
		mockery.checking(new Expectations() {
			{
				one(networking).send(with(any(Get.class)));
				one(networking).send(with(any(Get.class)));
			}
		});
		mrdb.get(key);
		mrdb.get(key, transcoder);
	}
}
