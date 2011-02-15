package tokyotyrant.networking;

import java.util.Arrays;
import java.util.Iterator;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import tokyotyrant.protocol.PingCommand;

@RunWith(JMock.class)
public class AbstractNetworkingTest {
	private Mockery mockery = new JUnit4Mockery() {{
		this.setImposteriser(ClassImposteriser.INSTANCE);
	}};
	private AbstractNetworking dut;
	private NodeLocator nodeLocator;
	private NodeSelector nodeSelector;
	private Reconnections reconnections;
	private ServerNode node0;
	private ServerNode node1;

	@Before public void beforeEach() {
		nodeLocator = mockery.mock(NodeLocator.class);
		dut = new AbstractNetworking(nodeLocator, nodeSelector) {
			public void start() {
			}
			public void stop() {
			}
		};
		nodeSelector = mockery.mock(NodeSelector.class);
		dut.nodeSelector = nodeSelector;
		reconnections = mockery.mock(Reconnections.class);
		dut.reconnections = reconnections;
		node0 = mockery.mock(ServerNode.class, "node0");
		node1 = mockery.mock(ServerNode.class, "node1");
	}
	
	@Test public void connectAllNodes() {
		mockery.checking(new Expectations() {{
			one(nodeLocator).getAll(); will(returnValue(Arrays.asList(node0, node1)));
			one(node0).connect(); will(returnValue(true));
			one(node1).connect(); will(returnValue(true));
		}});
		dut.connectAllNodes();
	}

	@Test public void connectAllNodesAndReconnectFailedNodeLater() {
		mockery.checking(new Expectations() {{
			one(nodeLocator).getAll(); will(returnValue(Arrays.asList(node0, node1)));
			one(node0).connect(); will(returnValue(false));
			one(reconnections).reconnect(node0);
			one(node1).connect(); will(returnValue(true));
		}});
		dut.connectAllNodes();
	}

	@Test public void disconnectAllNodes() {
		mockery.checking(new Expectations() {{
			one(nodeLocator).getAll(); will(returnValue(Arrays.asList(node0, node1)));
			one(node0).disconnect();
			one(node1).disconnect();
		}});
		dut.disconnectAllNodes();
	}
	
	@SuppressWarnings("unchecked")
	@Test public void sendCommandToRecommendedNode() {
		final PingCommand command = new PingCommand(1);
		mockery.checking(new Expectations() {{
			one(nodeLocator).getSequence();
			one(nodeSelector).select(with(any(Iterator.class))); will(returnValue(node0));
			one(node0).send(command);
		}});
		dut.send(command);
	}

	@Test public void sendCommandToSpecifiedNode() {
		final PingCommand command = new PingCommand(1);
		mockery.checking(new Expectations() {{
			one(node0).send(command);
		}});
		dut.send(node0, command);
	}
}
