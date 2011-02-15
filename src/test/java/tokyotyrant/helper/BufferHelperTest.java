package tokyotyrant.helper;

import static org.junit.Assert.*;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.Test;

public class BufferHelperTest {
	@Test public void prefixedDataAvailable() {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

		assertFalse(BufferHelper.prefixedDataAvailable(buffer, 4));
		
		buffer.writeInt(1);
		assertFalse(BufferHelper.prefixedDataAvailable(buffer, 4));
		buffer.writeByte((byte) 1);
		assertTrue(BufferHelper.prefixedDataAvailable(buffer, 4));
	}
}
