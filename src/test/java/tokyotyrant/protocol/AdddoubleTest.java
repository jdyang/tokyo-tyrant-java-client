package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jmock.Expectations;
import org.junit.Test;

public class AdddoubleTest extends AbstractCommandTest {
	@Test
	public void protocol() {
		double num = 4;
		Adddouble dut = new Adddouble(transcoder, transcoder, key, num);

		ChannelBuffer request = ChannelBuffers.buffer(2 + 4 + 8 + 8
				+ key.length);
		request.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x61 });
		request.writeInt(key.length);
		request.writeLong(dut._integ(num));
		request.writeLong(dut._fract(num));
		request.writeBytes(key);
		ChannelBuffer actual = ChannelBuffers.buffer(request.capacity());
		dut.encode(actual);
		assertEquals(request, actual);

		ChannelBuffer response = ChannelBuffers.buffer(1 + 8 + 8);
		assertFalse(dut.decode(response));

		response.writeByte(Command.ESUCCESS);
		assertFalse(dut.decode(response));
		response.resetReaderIndex();

		response.writeLong(dut._integ(3.0 + num));
		assertFalse(dut.decode(response));
		response.resetReaderIndex();

		response.writeLong(dut._fract(3.0 + num));
		assertTrue(dut.decode(response));
		assertEquals(3.0 + num, (double) dut.getReturnValue(), 0.0);

		// error
		response.clear();
		response.writeByte(Command.EUNKNOWN);
		assertTrue(dut.decode(response));
		assertEquals(Double.NaN, (double) dut.getReturnValue(), 0.0);
	}

	@Test
	public void rdb() {
		rdb.adddouble(key, 3);
	}

	@Test
	public void mrdb() {
		mockery.checking(new Expectations() {
			{
				one(networking).send(with(any(Adddouble.class)));
			}
		});
		mrdb.adddouble(key, 3);
	}
}
