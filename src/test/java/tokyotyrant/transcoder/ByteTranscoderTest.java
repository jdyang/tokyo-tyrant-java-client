package tokyotyrant.transcoder;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ByteTranscoderTest extends TranscoderTest {
	@Before public void beforeEach() {
		dut = new ByteTranscoder();
	}
	
	@Test public void encode() {
		byte value = 0x12;
		assertArrayEquals(new byte[] { value }, dut.encode(value));
	}
	
	@Test public void decode() {
		byte value = 0x12;
		assertEquals(value, dut.decode(new byte[] { value }));
	}

	@Test(expected=IllegalArgumentException.class)
	public void shouldNotDecodeInvalid() {
		dut.decode(new byte[] {0x12, 0x34});
	}
}
