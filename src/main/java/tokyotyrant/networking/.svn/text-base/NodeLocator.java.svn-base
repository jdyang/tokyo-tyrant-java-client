package tokyotyrant.networking;

import java.util.Collection;
import java.util.Iterator;

/**
 * All nodes should have same data.
 */
public interface NodeLocator {
	/**
	 * Initialize with given nodes.
	 */
	void initialize(ServerNode[] nodes);	
	
	/**
	 * All nodes
	 */
	Collection<ServerNode> getAll();
	
	/**
	 * Nodes for the key. First node is primary. Rest are backup.
	 */
	Iterator<ServerNode> getSequence();
}
