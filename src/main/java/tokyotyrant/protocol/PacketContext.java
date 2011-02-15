package tokyotyrant.protocol;

import java.util.HashMap;
import java.util.Map;

public final class PacketContext {
	private final Map<String, Object> fields;
	
	public PacketContext() {
		fields = new HashMap<String, Object>();		
	}
	
	public void put(String key, Object value) {
		fields.put(key, value);
	}
	
	public Object get(String key) {
		return fields.get(key);
	}
}