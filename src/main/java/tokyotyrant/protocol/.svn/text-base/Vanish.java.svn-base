package tokyotyrant.protocol;

public class Vanish extends CommandSupport<Boolean> {
	private static final PacketFormat REQUEST = magic().end();
	private static final PacketFormat RESPONSE = code(true).end();
	             
	public Vanish() {
		super((byte) 0x72, REQUEST, RESPONSE, null, null);
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
