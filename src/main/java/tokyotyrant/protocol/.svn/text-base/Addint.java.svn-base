package tokyotyrant.protocol;

import tokyotyrant.transcoder.Transcoder;

public class Addint extends CommandSupport<Integer> {
	private static final PacketFormat REQUEST = magic().int32("ksiz").int32("num").bytes("kbuf", "ksiz").end();
	private static final PacketFormat RESPONSE = code(true).int32("sum").end();
	private final byte[] key;
	private final int num;
	private int sum;
	
	public Addint(Transcoder keyTranscoder, Transcoder valueTranscoder, Object key, int num) {
		super((byte) 0x60, REQUEST, RESPONSE, keyTranscoder, valueTranscoder);
		this.key = keyTranscoder.encode(key);
		this.num = num;
	}
	
	public Integer getReturnValue() {
		return isSuccess() ? sum : Integer.MIN_VALUE;
	}
	
	protected void pack(PacketContext context) {
		context.put("ksiz", key.length);
		context.put("kbuf", key);
		context.put("num", num);
	}
	
	protected void unpack(PacketContext context) {
		code = (Byte)context.get("code");
		if (code == 0) {
			sum = (Integer)context.get("sum");
		}
	}
}
