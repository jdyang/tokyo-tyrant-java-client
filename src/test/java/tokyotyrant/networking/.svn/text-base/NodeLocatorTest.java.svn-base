package tokyotyrant.networking;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public abstract class NodeLocatorTest {
	protected Mockery mockery = new JUnit4Mockery();
	protected NodeLocator dut;
	protected ServerNode node0;
	protected ServerNode node1;
	
	@Before public void prepareNodes() {
		node0 = mockery.mock(ServerNode.class, "node0");
		node1 = mockery.mock(ServerNode.class, "node1");
	}
	
	@Test public void knowsAllNodes() {
		assertEquals(Arrays.asList(node0, node1), dut.getAll());
	}

	@Test(expected=UnsupportedOperationException.class)
	public void providesImmutableSequenceOfNodes() {
		Iterator<ServerNode> i = dut.getSequence();
		i.remove();
	}
}
