package tokyotyrant.protocol;

import tokyotyrant.transcoder.Transcoder;

public class Putcat extends PutCommandSupport {
	public Putcat(Transcoder keyTranscoder, Transcoder valueTranscoder, Object key, Object value) {
		super((byte) 0x12, keyTranscoder, valueTranscoder, key, value);
	}
}
