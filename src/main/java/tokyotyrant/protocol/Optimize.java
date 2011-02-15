package tokyotyrant.protocol;

public class Optimize extends CommandSupport<Boolean> {
	private static final PacketFormat REQUEST = magic().int32("psiz").bytes("pbuf", "psiz").end();
	private static final PacketFormat RESPONSE = code(false).end();
	private final byte[] parameters;
	
	public Optimize(String parameters) {
		super((byte) 0x71, REQUEST, RESPONSE, null, null);
		this.parameters = parameters.getBytes();
	}

	public Boolean getReturnValue() {
		return isSuccess();
	}
	
	protected void pack(PacketContext context) {
		context.put("psiz", parameters.length);
		context.put("pbuf", parameters);
	}
	
	protected void unpack(PacketContext context) {
		code = (Byte) context.get("code");
	}
}
