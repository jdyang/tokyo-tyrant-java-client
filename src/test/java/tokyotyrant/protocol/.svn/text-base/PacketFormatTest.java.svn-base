package tokyotyrant.protocol;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.Test;

public class PacketFormatTest {
	@Test public void shouldEncodeStaticSizeField() {
		PacketContext context = new PacketContext();
		context.put("magic", new byte[] { (byte) 0xC8, (byte) 0x80 });
		ChannelBuffer actual = ChannelBuffers.dynamicBuffer();
		new PacketFormatBuilder().bytes("magic", 2).end().encode(context, actual);
		ChannelBuffer expected = ChannelBuffers.dynamicBuffer();
		expected.writeBytes(new byte[] { (byte) 0xC8, (byte) 0x80 });
		assertEquals(expected, actual);
	}
	
	@Test public void shouldEncodeVariableSizeField() {
		PacketContext context = new PacketContext();
		context.put("ksiz", 3);
		context.put("kbuf", new byte[] { 1, 2, 3 });
		ChannelBuffer actual = ChannelBuffers.dynamicBuffer();
		new PacketFormatBuilder().int32("ksiz").bytes("kbuf", "ksiz").end().encode(context, actual);
		ChannelBuffer expected = ChannelBuffers.dynamicBuffer();
		expected.writeInt(3);
		expected.writeBytes(new byte[] { 1, 2, 3 });
		assertEquals(expected, actual);
	}
	
	@Test public void shouldDecodeStaticSizeField() {
		ChannelBuffer in = ChannelBuffers.buffer(1);
		in.writeByte((byte) 1);
		PacketContext context = new PacketContext();
		assertTrue(new PacketFormatBuilder().int8("code").end().decode(context, in));
		assertEquals((byte)1, context.get("code"));
	}
	
	@Test public void shouldDecodeVariableSizeField() {
		ChannelBuffer in = ChannelBuffers.buffer(4 + 3);
		in.writeInt(3);
		in.writeBytes(new byte[] { 1, 2, 3 });
		PacketContext context = new PacketContext();
		assertTrue(new PacketFormatBuilder().int32("vsiz").bytes("vbuf", "vsiz").end().decode(context, in));
		assertEquals(3, context.get("vsiz"));
		assertArrayEquals(new byte[] { 1, 2, 3 }, (byte[]) context.get("vbuf"));
	}

	@Test public void shouldStopToDecodeAfterCodeWhenError() {
		ChannelBuffer in = ChannelBuffers.buffer(1);
		in.writeByte((byte) 1);
		PacketContext context = new PacketContext();
		assertTrue(new PacketFormatBuilder().code(true).int32("vsiz").end().decode(context, in));
	}

	@Test public void shouldNotStopDecodeAfterCodeWhenSuccess() {
		ChannelBuffer in = ChannelBuffers.buffer(1);
		in.writeByte((byte) 0);
		PacketContext context = new PacketContext();
		assertFalse(new PacketFormatBuilder().code(true).int32("vsiz").end().decode(context, in));
	}

	@Test public void shouldNotStopDecodeAfterCode() {
		ChannelBuffer in = ChannelBuffers.buffer(1);
		in.writeByte((byte) 1);
		PacketContext context = new PacketContext();
		assertFalse(new PacketFormatBuilder().code(false).int32("vsiz").end().decode(context, in));
	}
	
	@Test public void encodeAndDecodeByteArrays() {
		String fieldName = "field";
		PacketContext encodingContext = new PacketContext();
		PacketContext decodingContext = new PacketContext();
		PacketFormat format = new PacketFormatBuilder().bytes(fieldName, 1).end();
		encodingContext.put(fieldName, new byte[] {42});
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		format.encode(encodingContext, buffer);
		assertArrayEquals(new byte[] {42}, (byte[])encodingContext.get(fieldName));
		format.decode(decodingContext, buffer);
		assertArrayEquals((byte[])decodingContext.get(fieldName), (byte[])encodingContext.get(fieldName));
	}

	@Test public void encodeAndDecodeInt32s() {
		String fieldName = "field";
		PacketContext encodingContext = new PacketContext();
		PacketContext decodingContext = new PacketContext();
		PacketFormat format = new PacketFormatBuilder().int32(fieldName).end();
		encodingContext.put(fieldName, 42);
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		format.encode(encodingContext, buffer);
		assertEquals(42, encodingContext.get(fieldName));
		format.decode(decodingContext, buffer);
		assertEquals(decodingContext.get(fieldName), encodingContext.get(fieldName));
	}

	@Test public void encodeAndDecodeInt8s() {
		String fieldName = "field";
		PacketContext encodingContext = new PacketContext();
		PacketContext decodingContext = new PacketContext();
		PacketFormat format = new PacketFormatBuilder().int8(fieldName).end();
		encodingContext.put(fieldName, (byte) 42);
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		format.encode(encodingContext, buffer);
		assertTrue(encodingContext.get(fieldName) instanceof Byte);
		assertEquals((byte)42, encodingContext.get(fieldName));
		format.decode(decodingContext, buffer);
		assertEquals((Byte)decodingContext.get(fieldName), encodingContext.get(fieldName));
	}

	@Test public void encodeAndDecodeInt64s() {
		String fieldName = "field";
		PacketContext encodingContext = new PacketContext();
		PacketContext decodingContext = new PacketContext();
		PacketFormat format = new PacketFormatBuilder().int64(fieldName).end();
		encodingContext.put(fieldName, 42L);
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		format.encode(encodingContext, buffer);
		assertTrue(encodingContext.get(fieldName) instanceof Long);
		assertEquals(42L, encodingContext.get(fieldName));
		format.decode(decodingContext, buffer);
		assertEquals((Long)decodingContext.get(fieldName), encodingContext.get(fieldName));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void shouldNotEncodeUnsupportedType() {
		String fieldName = "field";
		PacketFormat format = new PacketFormatBuilder().add(new PacketFormat.Field(fieldName, Short.class, 2)).end();
		PacketContext encodingContext = new PacketContext();
		encodingContext.put(fieldName, 42L);
		format.encode(encodingContext, ChannelBuffers.dynamicBuffer());
	}

	@Test(expected=UnsupportedOperationException.class)
	public void shouldNotDecodeUnsupportedType() {
		final String fieldName = "field";
		PacketFormat format = new PacketFormatBuilder().add(new PacketFormat.Field(fieldName, Short.class, 2)).end();
		PacketContext decodingContext = new PacketContext();
		ChannelBuffer buffer = ChannelBuffers.buffer(2);
		buffer.writeShort((short) 42);
		format.decode(decodingContext, buffer);
	}
}
