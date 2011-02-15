package tokyotyrant.protocol;

import tokyotyrant.transcoder.Transcoder;

public class Vsiz extends CommandSupport<Integer> {
	private static final PacketFormat REQUEST = magic().int32("ksiz").bytes("kbuf", "ksiz").end();
	private static final PacketFormat RESPONSE = code(true).int32("vsiz").end();
	private final byte[] key;
	private int vsiz;

	public Vsiz(Transcoder keyTranscoder, Transcoder valueTranscoder, Object key) {
		super((byte) 0x38, REQUEST, RESPONSE, keyTranscoder, valueTranscoder);
		this.key = keyTranscoder.encode(key);
	}
	
	public Integer getReturnValue() {
		return isSuccess() ? vsiz : -1;
	}

	protected void pack(PacketContext context) {
		context.put("ksiz", key.length);
		context.put("kbuf", key);
	}

	protected void unpack(PacketContext context) {
		code = (Byte)context.get("code");
		if (code == 0) {
			vsiz = (Integer)context.get("vsiz");
		}
	}
}
