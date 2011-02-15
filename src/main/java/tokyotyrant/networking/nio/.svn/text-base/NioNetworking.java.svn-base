package tokyotyrant.networking.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tokyotyrant.networking.AbstractNetworking;
import tokyotyrant.networking.NodeLocator;
import tokyotyrant.networking.NodeSelector;
import tokyotyrant.networking.ServerNode;

public class NioNetworking extends AbstractNetworking implements Runnable {
	private final Logger logger = LoggerFactory.getLogger(getClass());  
	private Selector selector;
	private Thread ioThread;
	private boolean running;

	public NioNetworking(NodeLocator nodeLocator, NodeSelector nodeSelector) {
		super(nodeLocator, nodeSelector);
	}

	public void start() throws Exception {
		selector = Selector.open();
		NioNode[] nodes = new NioNode[addresses.length];
		for (int i = 0; i < addresses.length; i++) {
			nodes[i] = new NioNode(selector);
			nodes[i].initialize(addresses[i]);
		}
		nodeLocator.initialize(nodes);
		connectAllNodes();

		//start IO
		running = true;
		ioThread = new Thread(this, this.getClass().getName());
		ioThread.start();
	}
	
	public void stop() {
		disconnectAllNodes();

		running = false;
		try {
			selector.wakeup().close();
		} catch (IOException e) {
			logger.error("Error while closing selector", e);
		}
	}

	public void run() {		
		while (running) {
			try {
				handleIO();
			} catch (Exception e) {
				logger.error("Error while handling IO", e);
			}
		}
	}
	
	void handleIO() throws IOException {
		handleInvocations();
		
		logger.debug("Selecting...");
		int n = selector.select(reconnections.getTimeToNextAttempt());
		logger.debug("{} keys are selected", n);
		if (!running) {
			logger.info("Stopped. So will not handle IO. {} keys will be ignored", n);
			return;
		}
		
		Set<SelectionKey> selectedKeys = selector.selectedKeys();
		Iterator<SelectionKey> i = selectedKeys.iterator();
		while (i.hasNext()) {
			SelectionKey key = i.next();
			i.remove();
			handleChannelIO(key);
		}
		
		reconnections.reconnectDelayed();
	}
	
	void handleInvocations() {
		for (ServerNode each : nodeLocator.getAll()) {
			if (!each.isActive()) {
				continue;
			}
			NioNode node = (NioNode) each;
			node.fillWriteQueue();
			node.fixupInterests();
		}
	}
	
	void handleChannelIO(SelectionKey key) {
		NioNode node = (NioNode) key.attachment();
		try {
			if (key.isConnectable()) {
				logger.debug("Ready to connect {}", node);
				node.handleConnect();
			} else {
				if (key.isReadable()) {
					logger.debug("Ready to read {}", node);
					node.handleRead();
				}
				if (key.isWritable()) {
					logger.debug("Ready to write {}", node);
					node.handleWrite();
				}
			}
		} catch (Exception e) {
			logger.error("Error while handling IO on " + node, e);
			reconnections.reconnect(node);
		}
		node.fixupInterests();
	}
}
