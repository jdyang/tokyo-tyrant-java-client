package tokyotyrant.transcoder;

import java.util.Arrays;

import org.junit.Test;

public abstract class TranscoderTest {
	protected Transcoder dut;
	
	@Test(expected=NullPointerException.class)
	public void shouldNotEncodeNull() {
		System.err.println(Arrays.toString(dut.encode(null)));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldNotDecodeNull() {
		System.err.println(dut.decode(null));
	}
}
