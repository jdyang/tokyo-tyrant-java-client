package tokyotyrant.networking.nio;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
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
public class OutgoingTest {
	private Mockery mockery = new JUnit4Mockery() {{
		this.setImposteriser(ClassImposteriser.INSTANCE);
	}};
	private Outgoing dut;
	private SocketChannel channel;
	private ChannelBuffer buffer;
	private Incoming incoming;
	
	@Before public void beforeEach() {
		channel = mockery.mock(SocketChannel.class);
		buffer = ChannelBuffers.dynamicBuffer();
		incoming = new Incoming(1024);
		dut = new Outgoing(incoming, buffer);
		dut.attach(channel);
	}

	@Test public void write() throws Exception {
		mockery.checking(new Expectations() {{
			one(channel).write(with(any(ByteBuffer.class))); will(returnValue(0));
		}});
		dut.write();
	}

	@Test public void fillOutgoingBufferWithOneCommand() throws Exception {
		PingCommand command = new PingCommand(1);
		dut.put(command);
		dut.fillBuffer();
		assertTrue(command.isReading());
		assertTrue(incoming.hasReadable());
		ChannelBuffer expected = ChannelBuffers.dynamicBuffer();
		command.encode(expected);
		assertEquals(expected, buffer);
	}

	@Test public void forgetBuffered() throws Exception {
		PingCommand command = new PingCommand(1);
		dut.put(command);
		dut.forgetBuffered();
		assertFalse(buffer.readable());
		assertTrue(dut.hasWritable());
	}
}
