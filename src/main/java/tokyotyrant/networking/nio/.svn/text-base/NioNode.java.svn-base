package tokyotyrant.networking.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tokyotyrant.networking.NodeAddress;
import tokyotyrant.networking.ServerNode;
import tokyotyrant.protocol.Command;

public class NioNode implements ServerNode {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	//Configuration
	private NodeAddress address;

	//Status
	private int reconnecting = 0;

	//Network
	Selector selector;
	SocketChannel channel;
	SelectionKey selectionKey;
	BlockingQueue<Command<?>> inputQueue;
	Outgoing outgoing;
	Incoming incoming;
	
	public NioNode(Selector selector) {
		this.selector = selector;
	}
	
	public void initialize(NodeAddress address) {
		this.address = address;
		inputQueue = new ArrayBlockingQueue<Command<?>>(16 * 1024);
		incoming = new Incoming(address.bufferCapacity());
		outgoing = new Outgoing(incoming, address.bufferCapacity());
	}
	
	public NodeAddress getAddress() {
		return address;
	}
	
	public void send(Command<?> command) {
		inputQueue.add(command);
		selector.wakeup();
	}

	public boolean isActive() {
		return reconnecting == 0 && channel != null && channel.isConnected();
	}
	
	public int getReconnectAttempt() {
		return reconnecting;
	}
	
	public boolean connect() {
		logger.info("Connect " + address);
		try {
			channel = openChannel();
			channel.connect(address.socketAddress());
			outgoing.attach(channel);
			incoming.attach(channel);
			selectionKey = channel.register(selector, SelectionKey.OP_CONNECT, this);
			return true;
		} catch (IOException e) {
			logger.error("Error while opening connection to " + address, e);
			return false;
		}
	}

	SocketChannel openChannel() throws IOException {
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		channel.socket().setTcpNoDelay(true);
		channel.socket().setKeepAlive(true);
		return channel;
	}

	public void disconnect() {
		logger.info("Disconnect " + address);
		try {
			selectionKey.cancel();
			channel.close();
		} catch (IOException e) {
			logger.error("Error while closing connection to " + address, e);
		} finally {
			outgoing.forgetBuffered();
			incoming.cancelAll();
		}
	}

	public void reconnecting() {
		logger.info("Reconnecting " + address);
		reconnecting++;
	}

	public void fillWriteQueue() {
		outgoing.putAll(inputQueue);
	}

	public void fixupInterests() {
		if (selectionKey == null || !selectionKey.isValid()) {
			return;
		}
		
		int ops = 0;
		if (channel.isConnected()) {
			if (incoming.hasReadable()) {
				ops |= SelectionKey.OP_READ;
			}
			if (outgoing.hasWritable()) {
				ops |= SelectionKey.OP_WRITE;
			}
		} else {
			ops = SelectionKey.OP_CONNECT;
		}
		selectionKey.interestOps(ops);
	}
	
	public void handleConnect() throws Exception {
		if (!channel.finishConnect()) {
			throw new IllegalStateException("Connection is not established");
		}
		reconnecting = 0;
	}
	
	public void handleWrite() throws Exception {
		outgoing.write();
	}
	
	public void handleRead() throws Exception {
		incoming.read();
	}
	
	public String toString() {
		return getClass().getName() + "[" + address.toString() + "]";
	}
}
