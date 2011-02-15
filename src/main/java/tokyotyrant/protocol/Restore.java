package tokyotyrant.protocol;

public class Restore extends CommandSupport<Boolean> {
	private static final PacketFormat REQUEST = magic().int32("psiz").int64("ts").int32("opts").bytes("path", "psiz").end();
	private static final PacketFormat RESPONSE = code(false).end();
	private final byte[] path;
	private final long timestamp;
	private final int opts;
	
	public Restore(String path, long timestamp, int opts) {
		super((byte) 0x74, REQUEST, RESPONSE, null, null);
		this.path = path.getBytes();
		this.timestamp = timestamp;
		this.opts = opts;
	}

	public Boolean getReturnValue() {
		return isSuccess();
	}
	
	protected void pack(PacketContext context) {
		context.put("psiz", path.length);
		context.put("ts", timestamp);
		context.put("opts", opts);
		context.put("path", path);
	}
	
	protected void unpack(PacketContext context) {
		code = (Byte)context.get("code");
	}
}
