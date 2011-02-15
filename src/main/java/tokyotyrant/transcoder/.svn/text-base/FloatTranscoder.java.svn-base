package tokyotyrant.transcoder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class FloatTranscoder implements Transcoder {
	private final ByteOrder byteOrder;

	public FloatTranscoder() {
		this(ByteOrder.nativeOrder());
	}
	
	public FloatTranscoder(ByteOrder byteOrder) {
		this.byteOrder = byteOrder;
	}

	public byte[] encode(Object decoded) {
		return ByteBuffer.allocate(Float.SIZE / 8).order(byteOrder).putFloat((Float) decoded).array();
	}

	public Float decode(byte[] encoded) {
		if (encoded.length != Float.SIZE / 8) {
			throw new IllegalArgumentException("Unable to decode " + Arrays.toString(encoded));
		}
		return ByteBuffer.wrap(encoded).order(byteOrder).getFloat();
	}
}
