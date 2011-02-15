package tokyotyrant.networking;

import tokyotyrant.protocol.Command;

public abstract class AbstractNetworking implements Networking {
	protected NodeLocator nodeLocator;
	protected NodeSelector nodeSelector;
	protected NodeAddress[] addresses;
	protected Reconnections reconnections;
	
	protected AbstractNetworking(NodeLocator nodeLocator, NodeSelector nodeSelector) {
		this.nodeLocator = nodeLocator;
		this.nodeSelector = nodeSelector;
		this.reconnections = new Reconnections();
	}

	public void initialize(NodeAddress[] addresses) {
		this.addresses = addresses;
	}

	public void send(Command<?> command) {
		send(nodeSelector.select(nodeLocator.getSequence()), command);
	}
	
	public void send(ServerNode node, Command<?> command) {
		node.send(command);
	}
	
	public NodeLocator getNodeLocator() {
		return nodeLocator;
	}
	
	protected void connectAllNodes() {
		for (ServerNode each : nodeLocator.getAll()) {
			if (!each.connect()) {
				reconnections.reconnect(each);
			}
		}
	}
	
	protected void disconnectAllNodes() {
		for (ServerNode each : nodeLocator.getAll()) {
			each.disconnect();
		}
	}
}
