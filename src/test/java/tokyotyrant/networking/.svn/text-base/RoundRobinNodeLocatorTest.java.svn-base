package tokyotyrant.networking;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class RoundRobinNodeLocatorTest extends NodeLocatorTest {
	@Before public void beforeEach() {
		dut = new RoundRobinNodeLocator();
		dut.initialize(new ServerNode[] { node0, node1 });
	}
	
	@Test public void getSequence() {
		Iterator<ServerNode> i1 = dut.getSequence();
		Iterator<ServerNode> i2 = dut.getSequence();
		assertEquals(node0, i1.next());
		assertEquals(node1, i2.next());
		assertEquals(node1, i1.next());
		assertEquals(node0, i2.next());
		assertFalse(i1.hasNext());
		assertFalse(i2.hasNext());
	}

	@Test(expected=UnsupportedOperationException.class)
	public void sequenceIsImmutable() {
		Iterator<ServerNode> i = dut.getSequence();
		i.remove();
	}
}
