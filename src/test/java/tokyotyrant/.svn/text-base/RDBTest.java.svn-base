package tokyotyrant;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Invocation;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.action.CustomAction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import tokyotyrant.protocol.Vanish;
import tokyotyrant.transcoder.ByteArrayTranscoder;
import tokyotyrant.transcoder.StringTranscoder;
import tokyotyrant.transcoder.Transcoder;

@RunWith(JMock.class)
public class RDBTest {
	private Mockery mockery = new JUnit4Mockery();
	private RDB dut;

	@Before public void beforeEach() {
		dut = new RDB();
	}
	
	@Test public void executesCommands() throws IOException {
		final RDB.Connection connection = mockery.mock(RDB.Connection.class);
		dut.connection = connection;
		Vanish command = new Vanish();
		mockery.checking(new Expectations() {{
            one(connection).write(with(any(ChannelBuffer.class)));
            one(connection).read(with(any(ChannelBuffer.class))); will(new CustomAction("Fill the buffer") {
                public Object invoke(Invocation invocation) throws Throwable {
                    ChannelBuffer buffer = (ChannelBuffer) invocation.getParameter(0);
                    buffer.writeBytes(new byte[] { 0 });
                    return null;
                }
            });
		}});
		assertThat(dut.execute(command), is(true));
	}
	
	@Test public void hasKeyTranscoderAndTheDefaultIsStringTranscoder() {
		assertThat(dut.getKeyTranscoder(), is(StringTranscoder.class));
	}

	@Test public void hasKeyTranscoderWhichIsChangeable() {
		Transcoder newTranscoder = new ByteArrayTranscoder();
		dut.setKeyTranscoder(newTranscoder);
		assertThat(dut.getKeyTranscoder(), is(newTranscoder));
	}
	
	@Test public void hasValueTranscoderAndTheDefaultIsStringTranscoder() {
		assertThat(dut.getValueTranscoder(), is(StringTranscoder.class));
	}

	@Test public void hasValueTranscoderWhichIsChangeable() {
		Transcoder newTranscoder = new ByteArrayTranscoder();
		dut.setValueTranscoder(newTranscoder);
		assertThat(dut.getValueTranscoder(), is(newTranscoder));
	}
}
