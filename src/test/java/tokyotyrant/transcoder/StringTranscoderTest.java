package tokyotyrant.transcoder;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

public class StringTranscoderTest extends TranscoderTest {
	private String characterEncoding = "ISO-8859-1";
	
	@Before public void beforeEach() {
		dut = new StringTranscoder(characterEncoding);
	}
	
	@Test public void encodeString() throws UnsupportedEncodingException {
		assertArrayEquals("value".getBytes(characterEncoding), dut.encode("value"));
	}

	@Test public void encodeInteger() throws UnsupportedEncodingException {
		assertArrayEquals("42".getBytes(characterEncoding), dut.encode(42));
	}

	@Test public void decode() throws UnsupportedEncodingException {
		assertEquals("value", (String)dut.decode("value".getBytes(characterEncoding)));
	}
}
