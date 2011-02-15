package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.Before;
import org.junit.Test;

import tokyotyrant.transcoder.ByteArrayTranscoder;

public class PutCommandSupportTest {
	private PutCommandSupport dut;
	private byte[] key = "key".getBytes();
	private byte[] value = "value".getBytes();
	
	@Before public void beforeEach() {
		dut = new PutCommandSupport((byte) 0xff, new ByteArrayTranscoder(), new ByteArrayTranscoder(), key, value) {
		};
	}
	
	@Test public void encodeShouldBeSuccefulAlways() {
		ChannelBuffer expected = ChannelBuffers.buffer(2 + 4 + 4 + key.length + value.length);
		expected.writeBytes(new byte[] { (byte) 0xc8, (byte) 0xff });
		expected.writeInt(3);
		expected.writeInt(5);
		expected.writeBytes(key);
		expected.writeBytes(value);
		ChannelBuffer actual = ChannelBuffers.buffer(expected.capacity());
		dut.encode(actual);
		assertEquals(expected, actual);
	}
	
	@Test public void decodeShouldBeCompletedWhenCodeIsGiven() {
		ChannelBuffer input = ChannelBuffers.buffer(1);
		input.writeByte((byte) 0);
		assertTrue(dut.decode(input));
	}

	@Test public void decodeShouldNotBeCompletedWhenCodeIsNotGiven() {
		ChannelBuffer input = ChannelBuffers.buffer(1);
		assertFalse(dut.decode(input));
	}
	
	@Test public void returnValueShouldBeTrueWhenCodeIsZero() {
		ChannelBuffer input = ChannelBuffers.buffer(1);
		input.writeByte((byte) 0);
		dut.decode(input);
		assertTrue(dut.getReturnValue());
	}
	
	@Test public void returnValueShouldBeFalseWhenCodeIsNotZero() {
		ChannelBuffer input = ChannelBuffers.buffer(1);
		input.writeByte((byte) 1);
		dut.decode(input);
		assertFalse(dut.getReturnValue());
	}
}
