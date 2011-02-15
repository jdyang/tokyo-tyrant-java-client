package tokyotyrant.protocol;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.Before;
import org.junit.Test;

public class CommandSupportTest {
	private PingCommand dut;
	
	@Before public void beforeEach() {
		dut = new PingCommand(42);
	}
	
	@Test public void packBeforeEncodeToPacket() {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		dut.encode(buffer);
		assertEquals((byte)0xc8, buffer.readByte());
		assertEquals((byte)0xff, buffer.readByte());
		assertEquals(42, buffer.readInt());
		assertFalse(buffer.readable());
	}

	@Test public void unpackWhenPacketDecodingIsCompleted() {
		ChannelBuffer buffer = ChannelBuffers.buffer(1024);
		buffer.writeByte(Command.ESUCCESS);
		buffer.writeInt(43);
		assertTrue(dut.decode(buffer));
		assertEquals(43, dut.pong);
	}

	@Test public void doNotUnpackWhenPacketDecodingIsNotCompleted() {
		ChannelBuffer buffer = ChannelBuffers.buffer(1024);
		buffer.writeByte((byte) 0);
		assertFalse(dut.decode(buffer));
		assertEquals(0, dut.pong);
	}
	
	@Test public void encodingContextShouldContainMagicByDefault() {
		assertArrayEquals(new byte[] {(byte) 0xc8, (byte) 0xff}, (byte[])dut.encodingContext().get("magic"));
	}
}
