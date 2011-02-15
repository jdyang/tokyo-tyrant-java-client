package tokyotyrant.networking;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * First node is the primary node. All other nodes are backup node.
 * When the primary node is down, try backup nodes in specified order.
 */
public class ActiveStandbyNodeLocator implements NodeLocator {
	private List<ServerNode> nodes;

	public void initialize(ServerNode[] nodes) {
		this.nodes = Collections.unmodifiableList(Arrays.asList(nodes));
	}
	
	public Collection<ServerNode> getAll() {
		return nodes;
	}

	public Iterator<ServerNode> getSequence() {
		return nodes.iterator();
	}
}
