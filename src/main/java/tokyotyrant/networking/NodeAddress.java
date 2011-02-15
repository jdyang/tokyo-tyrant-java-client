package tokyotyrant.networking;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeAddress {
	static final int DEFAULT_TIMEOUT = 0;
	static final int DEFAULT_BUFFER_CAPACITY = 8 * 1024;
	
	private final URI uri;
	private final Map<String, String> parameters;
	
	public NodeAddress(String uri) {
		this(URI.create(uri));
	}
	
	public NodeAddress(URI uri) {
		if (!"tcp".equals(uri.getScheme())) {
			throw new IllegalArgumentException("Only support Tokyo Tyrant protocol");
		}
		
		this.uri = uri;
		parameters = new HashMap<String, String>();
		if (uri.getQuery() != null) {
			String qs = uri.getQuery();
			for (String each : qs.split("&")) {
				String[] keyAndValue = each.split("=");
				parameters.put(keyAndValue[0], keyAndValue[1]);
			}
		}
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof NodeAddress)) {
			return false;
		}
		NodeAddress other = (NodeAddress) o;
		return uri.equals(other.uri);
	}
	
	public int hashCode() {
		return uri.hashCode();
	}
	
	public String toString() {
		return uri.toString();
	}
	
	public Map<String, String> parameters() {
		return parameters;
	}
	
	public SocketAddress socketAddress() {
		return new InetSocketAddress(uri.getHost(), uri.getPort());
	}
	
	int parameterAsInt(String name, int defaultValue) {
		String value = parameters.get(name);
		if (null == value) {
			return defaultValue;
		}
		return Integer.parseInt(value);
	}

	boolean parameterAsBoolean(String name, boolean defaultValue) {
		String value = parameters.get(name);
		if (null == value) {
			return defaultValue;
		}
		return Boolean.parseBoolean(value);
	}

	public boolean readOnly() {
		return parameterAsBoolean("readOnly", false);
	}

	public int timeout() {
		return parameterAsInt("timeout", DEFAULT_TIMEOUT);
	}

	public int bufferCapacity() {
		return parameterAsInt("bufferCapacity", DEFAULT_BUFFER_CAPACITY);
	}

	public static NodeAddress[] addresses(String addresses) {
		List<NodeAddress> result = new ArrayList<NodeAddress>();
		for (String each : addresses.split("\\s")) {
			result.add(new NodeAddress(each));
		}
		return result.toArray(new NodeAddress[result.size()]);
	}
}
