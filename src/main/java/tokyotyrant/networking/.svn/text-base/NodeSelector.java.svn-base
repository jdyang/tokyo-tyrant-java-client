package tokyotyrant.networking;

import java.util.Iterator;

public class NodeSelector {
	public ServerNode select(Iterator<ServerNode> sequence) {
		ServerNode selected = sequence.next();
		if (selected.isActive()) {
			return selected;
		}
		while (sequence.hasNext()) {
			ServerNode each = sequence.next();
			if (each.isActive()) {
				selected = each;
				break;
			}
		}
		return selected;
	}
}
