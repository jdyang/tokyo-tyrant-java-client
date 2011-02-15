package tokyotyrant.networking.nio;

import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import tokyotyrant.networking.NodeAddress;

@RunWith(JMock.class)
public class NioNodeTest {
	private Mockery mockery = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};
	private NioNode dut;
	private Selector selector;
	private SocketChannel mockChannel;
	private NodeAddress address;
	
	@Before public void beforeEach() {
		selector = mockery.mock(Selector.class);
		mockChannel = mockery.mock(SocketChannel.class);
		dut = new NioNode(selector) {
			@Override
			public void fixupInterests() {}
		};
		address = new NodeAddress("tcp://localhost:1978");
		dut.initialize(address);
	}
	
	@Test public void handleConnect() throws Exception {
		mockery.checking(new Expectations() {{
			one(mockChannel).finishConnect(); will(returnValue(true));
		}});
		dut.channel = mockChannel;
		dut.handleConnect();
	}
	
	@Test public void handleWrite() throws Exception {
		final Outgoing outgoing = mockery.mock(Outgoing.class);
		mockery.checking(new Expectations() {{
			one(outgoing).write();
		}});
		dut.outgoing = outgoing;
		dut.handleWrite();
	}

	@Test public void handleRead() throws Exception {
		final Incoming incoming = mockery.mock(Incoming.class);
		mockery.checking(new Expectations() {{
			one(incoming).read();
		}});
		dut.incoming = incoming;
		dut.handleRead();
	}
}
