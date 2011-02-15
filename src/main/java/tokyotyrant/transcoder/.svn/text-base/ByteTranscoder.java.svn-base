package tokyotyrant.transcoder;

import java.util.Arrays;

public class ByteTranscoder implements Transcoder {
	public byte[] encode(Object decoded) {
        return new byte[] { (Byte) decoded };
	}

	public Object decode(byte[] encoded) {
		if (encoded.length != Byte.SIZE / 8) {
			throw new IllegalArgumentException("Unable to decode " + Arrays.toString(encoded));
		}
		return encoded[0];
	}
}
