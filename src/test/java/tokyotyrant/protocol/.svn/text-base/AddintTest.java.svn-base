package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jmock.Expectations;
import org.junit.Test;

public class AddintTest extends AbstractCommandTest {
	@Test
	public void protocol() {
		int num = 4;
		Addint dut = new Addint(transcoder, transcoder, key, num);

		ChannelBuffer request = ChannelBuffers.buffer(2 + 4 + 4 + key.length);
		request.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x60 });
		request.writeInt(key.length);
		request.writeInt(num);
		request.writeBytes(key);
		ChannelBuffer actual = ChannelBuffers.buffer(request.capacity());
		dut.encode(actual);
		assertEquals(request, actual);

		ChannelBuffer response = ChannelBuffers.buffer(1 + 4);
		assertFalse(dut.decode(response));

		response.writeByte(Command.ESUCCESS);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();

		response.writeInt(3 + num);
		assertTrue(dut.decode(response));
		assertEquals(3 + num, (int) dut.getReturnValue());

		// error
		response.clear();
		response.writeByte(Command.EUNKNOWN);
		assertTrue(dut.decode(response));
		assertEquals(Integer.MIN_VALUE, (int) dut.getReturnValue());
	}

	@Test
	public void rdb() {
		rdb.addint(key, 3);
	}

	@Test
	public void mrdb() {
		mockery.checking(new Expectations() {
			{
				one(networking).send(with(any(Addint.class)));
			}
		});
		mrdb.addint(key, 3);
	}
}
