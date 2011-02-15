package tokyotyrant.helper;

import static org.junit.Assert.*;

import org.junit.Test;

public class ByteArrayUtilsTest {
	@Test public void indexOf() {
		assertEquals(-1, ByteArrayUtils.indexOf(new byte[] { 1, 2, 3 }, 0, 0));
		assertEquals(1, ByteArrayUtils.indexOf(new byte[] { 1, 2, 3 }, 2, 0));
	}
	
	@Test public void split() {
		assertEquals(1, ByteArrayUtils.split(new byte[] {}, 0).size());
		assertEquals(1, ByteArrayUtils.split(new byte[] {1}, 0).size());
		assertEquals(2, ByteArrayUtils.split(new byte[] {1, 0, 2}, 0).size());
		assertEquals(3, ByteArrayUtils.split(new byte[] {1, 0, 0, 2}, 0).size());
		assertEquals(3, ByteArrayUtils.split(new byte[] {1, 0, 2, 0}, 0).size());
	}
}
