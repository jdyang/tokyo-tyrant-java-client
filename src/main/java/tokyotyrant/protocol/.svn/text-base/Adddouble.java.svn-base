package tokyotyrant.protocol;

import tokyotyrant.transcoder.Transcoder;

public class Adddouble extends CommandSupport<Double> {
	private static final PacketFormat REQUEST = magic().int32("ksiz").int64("integ").int64("fract").bytes("kbuf", "ksiz").end();
	private static final PacketFormat RESPONSE = code(true).int64("integ").int64("fract").end();
	private final byte[] key;
	private final long numInteg;
	private final long numFract;
	private long sumInteg;
	private long sumFract;
	
	public Adddouble(Transcoder keyTranscoder, Transcoder valueTranscoder, Object key, double num) {
		super((byte) 0x61, REQUEST, RESPONSE, keyTranscoder, valueTranscoder);
		this.key = keyTranscoder.encode(key);
		this.numInteg = _integ(num);
		this.numFract = _fract(num);
	}
	
	public Double getReturnValue() {
		return isSuccess() ? _double(sumInteg, sumFract) : Double.NaN;
	}
	
	private static final long TRILLION = (1000000L * 1000000L);
	
	long _integ(double num) {
		return (long)num;
	}
	
	long _fract(double num) {
		return (long)((num - _integ(num)) * TRILLION);
	}
	
	double _double(long integ, long fract) {
		return integ + (fract / (double)TRILLION);
	}
	
	protected void pack(PacketContext context) {
		context.put("ksiz", key.length);
		context.put("kbuf", key);
		context.put("integ", numInteg);
		context.put("fract", numFract);
	}
	
	protected void unpack(PacketContext context) {
		code = (Byte)context.get("code");
		if (code == 0) {
			sumInteg = (Long) context.get("integ");
			sumFract = (Long) context.get("fract");
		}
	}
}
