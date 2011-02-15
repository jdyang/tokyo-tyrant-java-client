package tokyotyrant.networking;

import tokyotyrant.protocol.Command;

public interface Networking {
	void initialize(NodeAddress[] addresses);
	
	void start() throws Exception;
	
	void stop();
	
	void send(Command<?> command);
	
	void send(ServerNode node, Command<?> command);
	
	NodeLocator getNodeLocator();
}
