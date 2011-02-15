package tokyotyrant.transcoder;

/**
 * Always treat values as byte array.
 */
public class ByteArrayTranscoder implements Transcoder {
	public byte[] encode(Object decoded) {
		if (decoded == null) {
			throw new NullPointerException("Cannot encode null");
		}
		return (byte[]) decoded;
	}

	public Object decode(byte[] encoded) {
		if (encoded == null) {
			throw new NullPointerException("Cannot decode null");
		}
		return encoded;
	}
}
