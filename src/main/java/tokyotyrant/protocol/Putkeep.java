package tokyotyrant.protocol;

import tokyotyrant.transcoder.Transcoder;

public class Putkeep extends PutCommandSupport {
	public Putkeep(Transcoder keyTranscoder, Transcoder valueTranscoder, Object key, Object value) {
		super((byte) 0x11, keyTranscoder, valueTranscoder, key, value);
	}
}
