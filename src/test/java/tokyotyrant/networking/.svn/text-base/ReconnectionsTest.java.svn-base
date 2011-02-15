package tokyotyrant.networking;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class ReconnectionsTest {
	private Mockery mockery = new JUnit4Mockery();
	private Reconnections dut;
	private long now = System.currentTimeMillis();

	@Before public void beforeEach() {
		dut = new Reconnections() {
			long now() {
				return now;
			}
		};
	}
	
	@Test public void reconnect() {
		final ServerNode node = mockery.mock(ServerNode.class);
		mockery.checking(new Expectations() {{
			one(node).disconnect();
			one(node).reconnecting();
			one(node).getReconnectAttempt(); will(returnValue(1));
		}});
		dut.reconnect(node);
		now += 10;
		assertEquals(dut.backoff(1) - 10, dut.getTimeToNextAttempt());
	}
	
	@Test public void reconnectShouldNotMissNode() throws IOException {
		final ServerNode node1 = mockery.mock(ServerNode.class, "Node1");
		final ServerNode node2 = mockery.mock(ServerNode.class, "Node2");
		mockery.checking(new Expectations() {{
			one(node1).disconnect();
			one(node1).reconnecting();
			one(node1).getReconnectAttempt(); will(returnValue(1));

			one(node2).disconnect();
			one(node2).reconnecting();
			one(node2).getReconnectAttempt(); will(returnValue(1));
		}});
		dut.reconnect(node1);
		dut.reconnect(node2);
		assertEquals(2, dut.countDelayed());
	}

	@Test public void delayIndefinitelyWhenEmpty() {
		assertEquals(0, dut.getTimeToNextAttempt());
	}
	
	@Test public void reconnectDelayedNodes() throws IOException {
		final ServerNode node = mockery.mock(ServerNode.class);
		mockery.checking(new Expectations() {{
			one(node).disconnect();
			one(node).reconnecting();
			one(node).getReconnectAttempt(); will(returnValue(1));
			
			one(node).connect(); will(returnValue(true));
		}});
		dut.reconnect(node);
		now += dut.backoff(1) + 1;
		dut.reconnectDelayed();
		assertEquals(0, dut.countDelayed());
	}

	@Test public void reconnectFailedNodeAgain() throws IOException {
		final ServerNode node1 = mockery.mock(ServerNode.class, "Node1");
		final ServerNode node2 = mockery.mock(ServerNode.class, "Node2");
		mockery.checking(new Expectations() {{
			one(node1).disconnect();
			one(node1).reconnecting();
			one(node1).getReconnectAttempt(); will(returnValue(1));

			one(node2).disconnect();
			one(node2).reconnecting();
			one(node2).getReconnectAttempt(); will(returnValue(2));

			one(node1).connect(); will(returnValue(false));
			one(node2).connect(); will(returnValue(true));
			
			one(node1).disconnect();
			one(node1).reconnecting();
			one(node1).getReconnectAttempt(); will(returnValue(1));
		}});
		dut.reconnect(node1);
		dut.reconnect(node2);
		now += dut.backoff(2) + 1;
		dut.reconnectDelayed();
		assertEquals(1, dut.countDelayed());
	}

	@Test public void shouldBackoffExponentialy() {
		final ServerNode node = mockery.mock(ServerNode.class);
		mockery.checking(new Expectations() {{
			one(node).getReconnectAttempt(); will(returnValue(0));
			one(node).getReconnectAttempt(); will(returnValue(1));
			one(node).getReconnectAttempt(); will(returnValue(3));
		}});
		assertEquals(100, dut.backoff(node));
		assertEquals(200, dut.backoff(node));
		assertEquals(800, dut.backoff(node));
	}

	@Test public void shouldNotBackoffLongerThanMaximum() {
		final ServerNode node = mockery.mock(ServerNode.class);
		mockery.checking(new Expectations() {{
			one(node).getReconnectAttempt(); will(returnValue(31));
		}});
		assertEquals(Reconnections.MAX_BACKOFF, dut.backoff(node));
	}
}
