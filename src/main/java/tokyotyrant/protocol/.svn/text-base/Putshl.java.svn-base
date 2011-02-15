package tokyotyrant.protocol;

import tokyotyrant.transcoder.Transcoder;

public class Putshl extends CommandSupport<Boolean> {
	private static final PacketFormat REQUEST = magic().int32("ksiz").int32("vsiz").int32("width").bytes("kbuf", "ksiz").bytes("vbuf", "vsiz").end();
	private static final PacketFormat RESPONSE = code(false).end();
	private final byte[] key;
	private final byte[] value;
	private final int width;
	
	public Putshl(Transcoder keyTranscoder, Transcoder valueTranscoder, Object key, Object value, int width) {
		super((byte) 0x13, REQUEST, RESPONSE, keyTranscoder, valueTranscoder);
		this.key = keyTranscoder.encode(key);
		this.value = valueTranscoder.encode(value);
		this.width = width;
	}

	public Boolean getReturnValue() {
		return isSuccess();
	}
	
	protected void pack(PacketContext context) {
		context.put("ksiz", key.length);
		context.put("vsiz", value.length);
		context.put("width", width);
		context.put("kbuf", key);
		context.put("vbuf", value);
	}
	
	protected void unpack(PacketContext context) {
		code = (Byte)context.get("code");
	}
}
