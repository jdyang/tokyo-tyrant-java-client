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

import tokyotyrant.protocol.Command;
import tokyotyrant.protocol.PingCommand;

@RunWith(JMock.class)
public class IncomingTest {
	private Mockery mockery = new JUnit4Mockery() {{
		this.setImposteriser(ClassImposteriser.INSTANCE);
	}};
	private Incoming dut;
	private SocketChannel channel;
	private ChannelBuffer buffer;
	
	@Before public void beforeEach() {
		channel = mockery.mock(SocketChannel.class);
		buffer = ChannelBuffers.dynamicBuffer();
		dut = new Incoming(1024, buffer);
		dut.attach(channel);
	}
	
	@Test public void read() throws Exception {
		mockery.checking(new Expectations() {{
			one(channel).read(with(any(ByteBuffer.class))); will(returnValue(0));
		}});
		dut.read();
	}

	@Test public void consumeBuffer() throws Exception {
		PingCommand command = new PingCommand(1);
		command.writing(0);
		dut.put(command);
		buffer.writeByte(Command.EUNKNOWN);
		dut.consumeBuffer();
		assertTrue(command.isCompleted());
	}

	@Test public void consumeBufferIncompleted() throws Exception {
		PingCommand command = new PingCommand(1);
		command.writing(0);
		dut.put(command);
		int mark = buffer.readerIndex();
		dut.consumeBuffer();
		assertFalse(command.isCompleted());
		assertEquals(mark, buffer.readerIndex());
	}

	@Test public void cancelAll() throws Exception {
		PingCommand command = new PingCommand(1);
		command.writing(0);
		dut.put(command);
		dut.cancelAll();
		assertFalse(dut.hasReadable());
		assertTrue(command.isCancelled());
	}
}
