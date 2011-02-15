package tokyotyrant.networking.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import tokyotyrant.protocol.Command;

public class Outgoing {
	private final Incoming incoming;
	private final ChannelBuffer buffer;
	private final BlockingQueue<Command<?>> writeQueue;
	private SocketChannel channel;

	public Outgoing(Incoming incoming, int bufferCapacity) {
		this(incoming, ChannelBuffers.dynamicBuffer(bufferCapacity));
	}

	public Outgoing(Incoming incoming, ChannelBuffer buffer) {
		this.incoming = incoming;
		this.buffer = buffer;
		writeQueue = new LinkedBlockingQueue<Command<?>>();
	}

	public void attach(SocketChannel channel) {
		this.channel = channel;
	}
	
	void put(Command<?> command) {
		writeQueue.add(command);
	}	

	public void putAll(BlockingQueue<Command<?>> inputQueue) {
		if (inputQueue.isEmpty()) {
			return;
		}
		Collection<Command<?>> tmp = new ArrayList<Command<?>>();
		inputQueue.drainTo(tmp, writeQueue.remainingCapacity());
		writeQueue.addAll(tmp);
	}

	public boolean hasWritable() {
		return buffer.readable() || !writeQueue.isEmpty();
	}
	
	public void write() throws Exception {
		fillBuffer();
		consumeBuffer();
	}

	void fillBuffer() throws Exception {
		while (!writeQueue.isEmpty()) {
			Command<?> command = writeQueue.peek();
			try {
				command.encode(buffer);
				Command<?> _removed = writeQueue.remove();
				assert _removed == command;
				command.reading();
				if (command.responseRequired()) {
					incoming.put(command);
				} else {
					command.complete();
				}
			} catch (Exception exception) {
				command.error(exception);
				throw new Exception("Error while sending " + command, exception);
			}
		}
	}
	
	void consumeBuffer() throws IOException {
		ByteBuffer chunk = buffer.toByteBuffer();
		int n = channel.write(chunk);
		buffer.skipBytes(n);
		buffer.discardReadBytes();
	}
	
	public void forgetBuffered() {
		buffer.clear();
	}
}
