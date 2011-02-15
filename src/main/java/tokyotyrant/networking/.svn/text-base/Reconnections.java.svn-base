package tokyotyrant.networking;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Reconnections {
	static final int INITIAL_BACKOFF = 100;
	// maximum amount of time to wait between reconnect attempts
	static final int MAX_BACKOFF = 60 * 1000;
	private final SortedMap<Long, ServerNode> queue = new TreeMap<Long, ServerNode>();

	public void reconnect(ServerNode node) {
		if (queue.containsValue(node)) {
			return;
		}
		node.disconnect();
		node.reconnecting();
		queue.put(findEmptyTimeSlot(now() + backoff(node)), node);
	}
	
	/**
	 * @return In millisecond.
	 */
	public long getTimeToNextAttempt() {
		if (queue.isEmpty()) {
			return 0;
		}
		return Math.max(queue.firstKey() - now(), 1);
	}
	
	public void reconnectDelayed() {
		List<ServerNode> failedNodes = new ArrayList<ServerNode>();
		for (Iterator<ServerNode> i = queue.headMap(now()).values().iterator(); i.hasNext(); ) {
			ServerNode node = i.next();
			i.remove();
			if (!node.connect()) {
				failedNodes.add(node);
			}
		}
		for (ServerNode each : failedNodes) {
			reconnect(each);
		}
	}
	
	public int countDelayed() {
		return queue.size();
	}

	long now() {
		return System.currentTimeMillis();
	}
	
	int backoff(ServerNode node) {
		return backoff(Math.min(node.getReconnectAttempt(), 16));
	}
	
	int backoff(int attempts) {
		return Math.min(INITIAL_BACKOFF * (1 << attempts), MAX_BACKOFF);
	}
	
	long findEmptyTimeSlot(long timeSlot) {
		while (queue.containsKey(timeSlot)) {
			timeSlot++;
		}
		return timeSlot;
	}
}
