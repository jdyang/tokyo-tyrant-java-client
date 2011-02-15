package tokyotyrant.networking;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class ActiveStandbyNodeLocatorTest extends NodeLocatorTest {
	@Before public void beforeEach() {
		dut = new ActiveStandbyNodeLocator();
		dut.initialize(new ServerNode[] { node0, node1 });
	}
	
	@Test public void getSequence() {
		Iterator<ServerNode> i = dut.getSequence();
		assertEquals(node0, i.next());
		assertEquals(node1, i.next());
		assertFalse(i.hasNext());
	}
}
