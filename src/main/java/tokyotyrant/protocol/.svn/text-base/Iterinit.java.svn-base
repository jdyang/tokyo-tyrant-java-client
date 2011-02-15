package tokyotyrant.protocol;

public class Iterinit extends CommandSupport<Boolean> {
	private static final PacketFormat REQUEST = magic().end();
	private static final PacketFormat RESPONSE = code(false).end();
	             
	public Iterinit() {
		super((byte) 0x50, REQUEST, RESPONSE, null, null);
	}
	
	public Boolean getReturnValue() {
		return isSuccess();
	}
	
	protected void pack(PacketContext context) {
	}
	
	protected void unpack(PacketContext context) {
		code = (Byte)context.get("code");
	}
}
