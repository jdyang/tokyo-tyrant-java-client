package tokyotyrant.networking;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RoundRobinNodeLocator implements NodeLocator {
	private List<ServerNode> nodes;
	private int primaryIndex = 0;
	
	public void initialize(ServerNode[] nodes) {
		this.nodes = Collections.unmodifiableList(Arrays.asList(nodes));
	}
	
	public Collection<ServerNode> getAll() {
		return nodes;
	}

	public Iterator<ServerNode> getSequence() {
		return new NodeIterator(primaryIndex++ % nodes.size(), nodes);
	}

	static class NodeIterator implements Iterator<ServerNode> {
		private int primaryIndex;
		private List<ServerNode> nodes;
		private int next;

		public NodeIterator(int primaryIndex, List<ServerNode> nodes) {
			this.nodes = nodes;
			this.primaryIndex = primaryIndex;
			this.next = primaryIndex;
		}
		
		public boolean hasNext() {
			return (next % nodes.size()) != primaryIndex;
		}

		public ServerNode next() {
			return nodes.get(next++ % nodes.size());
		}

		public void remove() {
			throw new UnsupportedOperationException("Can't remove a node");
		}
	}
}
