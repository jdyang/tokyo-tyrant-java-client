package tokyotyrant.protocol;

public class Setmst extends CommandSupport<Boolean> {
	private static final PacketFormat REQUEST = magic().int32("hsiz").int32("port").int64("ts").int32("opts").bytes("host", "hsiz").end();
	private static final PacketFormat RESPONSE = code(false).end();
	private final byte[] host;
	private final int port;
	private final long timestamp;
	private final int opts;
	
	public Setmst(String host, int port, long timestamp, int opts) {
		super((byte) 0x78, REQUEST, RESPONSE, null, null);
		this.host = host.getBytes();
		this.port = port;
		this.timestamp = timestamp;
		this.opts = opts;
	}

	public Boolean getReturnValue() {
		return isSuccess();
	}
	
	protected void pack(PacketContext context) {
		context.put("hsiz", host.length);
		context.put("host", host);
		context.put("port", port);
		context.put("ts", timestamp);
		context.put("opts", opts);
	}
	
	protected void unpack(PacketContext context) {
		code = (Byte)context.get("code");
	}
}
