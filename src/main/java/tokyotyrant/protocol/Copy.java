package tokyotyrant.protocol;

public class Copy extends CommandSupport<Boolean> {
	private static final PacketFormat REQUEST = magic().int32("psiz").bytes("path", "psiz").end();
	private static final PacketFormat RESPONSE = code(false).end();
	private final byte[] path;
	
	public Copy(String path) {
		super((byte) 0x73, REQUEST, RESPONSE, null, null);
		this.path = path.getBytes();
	}

	public Boolean getReturnValue() {
		return isSuccess();
	}
	
	protected void pack(PacketContext context) {
		context.put("psiz", path.length);
		context.put("path", path);
	}
	
	protected void unpack(PacketContext context) {
		code = (Byte)context.get("code");
	}
}
