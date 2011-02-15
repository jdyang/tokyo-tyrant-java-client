package tokyotyrant.transcoder;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Always treat values as character string.
 */
public class StringTranscoder implements Transcoder {
	private final String characterEncoding;
	
	public StringTranscoder() {
		this("UTF-8");
	}
	
	public StringTranscoder(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}
	
	public Object decode(byte[] encoded) {
		try {
			return new String(encoded, characterEncoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unable to decode " + Arrays.toString(encoded), e);
		}
	}

	public byte[] encode(Object decoded) {
		try {
			return decoded.toString().getBytes(characterEncoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unable to encode " + decoded, e);
		}
	}
}
