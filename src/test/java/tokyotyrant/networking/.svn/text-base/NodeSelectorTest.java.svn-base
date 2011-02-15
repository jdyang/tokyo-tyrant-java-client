package tokyotyrant.networking;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class NodeSelectorTest {
	private Mockery mockery = new JUnit4Mockery();
	private NodeSelector dut;
	private ServerNode node0;
	private ServerNode node1;

	@Before public void beforeEach() {
		dut = new NodeSelector();
		node0 = mockery.mock(ServerNode.class, "node0");
		node1 = mockery.mock(ServerNode.class, "node1");
	}

	@Test public void selectPrimaryIfItIsActive() {
		mockery.checking(new Expectations() {{
			one(node0).isActive(); will(returnValue(true));
		}});
		assertEquals(node0, dut.select(Arrays.asList(node0).iterator()));
	}

	@Test public void selectBackupIfThePrimaryIsNotActive() {
		mockery.checking(new Expectations() {{
			one(node0).isActive(); will(returnValue(false));
			one(node1).isActive(); will(returnValue(true));
		}});
		assertEquals(node1, dut.select(Arrays.asList(node0, node1).iterator()));
	}

	@Test public void selectPrimaryIfAllBackupsAreNotActive() {
		mockery.checking(new Expectations() {{
			one(node0).isActive(); will(returnValue(false));
			one(node1).isActive(); will(returnValue(false));
		}});
		assertEquals(node0, dut.select(Arrays.asList(node0, node1).iterator()));
	}
}
