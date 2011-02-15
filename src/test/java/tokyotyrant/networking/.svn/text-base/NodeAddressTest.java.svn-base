package tokyotyrant.networking;

import static org.junit.Assert.*;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class NodeAddressTest {
	@Test public void singleNode() {
		assertArrayEquals(new NodeAddress[] { new NodeAddress("tcp://localhost:1978") },
				NodeAddress.addresses("tcp://localhost:1978"));
	}

	@Test public void multipleNodes() {
		assertArrayEquals(new NodeAddress[] { new NodeAddress("tcp://localhost:1978"), new NodeAddress("tcp://localhost:1979") },
				NodeAddress.addresses("tcp://localhost:1978 tcp://localhost:1979"));
	}
	
	@Test public void socketAddress() {
		assertEquals(new InetSocketAddress("localhost", 1978), new NodeAddress("tcp://localhost:1978").socketAddress());
	}
	
	@Test public void parameters() {
		Map<String, String> expected = new HashMap<String, String>();
		expected.put("name", "value");
		assertEquals(expected, new NodeAddress("tcp://localhost/?name=value").parameters());
	}

	@Test public void readOnlyOption() {
		assertFalse(new NodeAddress("tcp://localhost:1978").readOnly());
		assertTrue(new NodeAddress("tcp://localhost:1978/?readOnly=true").readOnly());
	}

	@Test public void timeout() {
		assertEquals(NodeAddress.DEFAULT_TIMEOUT, new NodeAddress("tcp://localhost:1978").timeout());
		assertEquals(100, new NodeAddress("tcp://localhost:1978?timeout=100").timeout());
	}

	@Test public void bufferCapacityOption() {
		assertEquals(NodeAddress.DEFAULT_BUFFER_CAPACITY, new NodeAddress("tcp://localhost:1978").bufferCapacity());
		assertEquals(1024, new NodeAddress("tcp://localhost:1978?bufferCapacity=1024").bufferCapacity());
	}
}
