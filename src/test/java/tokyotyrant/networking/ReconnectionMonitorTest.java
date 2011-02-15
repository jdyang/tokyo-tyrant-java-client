package tokyotyrant.networking;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class ReconnectionMonitorTest {
	private Mockery mockery = new JUnit4Mockery() {{
		this.setImposteriser(ClassImposteriser.INSTANCE);
	}};
	private ReconnectionMonitor dut;
	private Reconnections reconnections;

	@Before public void beforeEach() {
		reconnections = mockery.mock(Reconnections.class);
		mockery.checking(new Expectations() {{
			allowing(reconnections).getTimeToNextAttempt(); will(returnValue(Long.MAX_VALUE));
			allowing(reconnections).reconnectDelayed();
		}});
		dut = new ReconnectionMonitor(reconnections);
		dut.start();
	}
	
	@After public void afterEach() {
		dut.stop();
	}
	
	@Test public void reconnect() throws InterruptedException {
		final ServerNode node = mockery.mock(ServerNode.class);
		mockery.checking(new Expectations() {{
			one(reconnections).reconnect(node);
		}});
		dut.reconnect(node);
	}
}
