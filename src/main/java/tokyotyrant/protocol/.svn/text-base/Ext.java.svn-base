package tokyotyrant.protocol;

import tokyotyrant.transcoder.Transcoder;

public class Ext extends CommandSupport<Object> {
	private static final PacketFormat REQUEST = magic().int32("nsiz").int32("opts").int32("ksiz").int32("vsiz").bytes("nbuf", "nsiz").bytes("kbuf", "ksiz").bytes("vbuf", "vsiz").end();
	private static final PacketFormat RESPONSE = code(true).int32("rsiz").bytes("rbuf", "rsiz").end();
	private final byte[] name;
	private final byte[] key;
	private final byte[] value;
	private final int opts;
	private byte[] result;
	
	public Ext(Transcoder keyTranscoder, Transcoder valueTranscoder, String name, Object key, Object value, int opts) {
		super((byte) 0x68, REQUEST, RESPONSE, keyTranscoder, valueTranscoder);
		this.name = name.getBytes();
		this.key = keyTranscoder.encode(key);
		this.value = valueTranscoder.encode(value);
		this.opts = opts;
	}
	
	public Object getReturnValue() {
		return isSuccess() ? valueTranscoder.decode(result) : null;
	}
	
	protected void pack(PacketContext context) {
		context.put("nsiz", name.length);
		context.put("opts", opts);
		context.put("ksiz", key.length);
		context.put("vsiz", value.length);
		context.put("nbuf", name);
		context.put("kbuf", key);
		context.put("vbuf", value);
	}
	
	protected void unpack(PacketContext context) {
		code = (Byte)context.get("code");
		if (code == 0) {
			result = (byte[])context.get("rbuf");
		}
	}
}
