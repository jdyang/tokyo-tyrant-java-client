package tokyotyrant.networking;

import tokyotyrant.protocol.Command;

public interface ServerNode {
	void initialize(NodeAddress address);
	
	NodeAddress getAddress();
	
	void send(Command<?> command);

	boolean isActive();

	int getReconnectAttempt();

	/**
	 * Open connection.
	 */
	boolean connect();

	/**
	 * Disconnect connection.
	 */
	void disconnect();

	/**
	 * {@link Networking} notice the reconnection to this node.
	 */
	void reconnecting();
}
