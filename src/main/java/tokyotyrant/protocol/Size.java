package tokyotyrant.protocol;

public class Size extends CommandSupport<Long> {
	private static final PacketFormat REQUEST = magic().end();
	private static final PacketFormat RESPONSE = code(false).int64("size").end();
	private long size;
	             
	public Size() {
		super((byte) 0x81, REQUEST, RESPONSE, null, null);
	}
	
	public Long getReturnValue() {
		return size;
	}
	
	protected void pack(PacketContext context) {
	}
	
	protected void unpack(PacketContext context) {
		code = (Byte)context.get("code");
		size = (Long)context.get("size");
	}
}
